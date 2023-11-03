package src;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class testtest {
    public static class Lebensmittel {
        //int id;
        String LebensmittelName;
        LocalDate MHD;
        Boolean angebrochen;
        int menge;
        Boolean gml; // true = g false = ml
        Boolean mhdueberschritten; // true = ist abgelaufen
        public Lebensmittel(String Name, LocalDate Datum, Boolean Angebrochen, int Menge, Boolean Einheit){
            LebensmittelName = Name;
            MHD = Datum;
            angebrochen = Angebrochen;
            menge = Menge;
            gml = Einheit;
            mhdueberschritten = !MHD.isAfter(LocalDate.now());
            //id = 1;
        }
        //default contructor, falls nichts angegeben wird.
        public Lebensmittel(){
            LebensmittelName = "Default";
            MHD = LocalDate.now();
            angebrochen = false;
            menge = 1;
            gml = false;
            mhdueberschritten = !MHD.isAfter(LocalDate.now());
        }

        public Object[] toArray(){
            int timeLeft = MHD.compareTo(LocalDate.now());
            String einheit;
            if (gml){
                einheit = "g";
            }else {
                einheit = "ml";
            }
            Object[] object = new Object[6];
            object[0]= LebensmittelName;
            object[1]=""+menge+einheit;
            object[2]=MHD;
            object[3]=timeLeft;
            object[4]=angebrochen;
            object[5]=mhdueberschritten;
            return object;
        }
        @Override
        public String toString(){
            String angebrochenString;
            String einheit;
            int timeLeft = MHD.compareTo(LocalDate.now());
            if (angebrochen){
                angebrochenString = "Angebrochen";
            }else {
                angebrochenString = "nicht Angebrochen";
            }
            if (gml){
                einheit = "g";
            }else {
                einheit = "ml";
            }
            return LebensmittelName + " mit Ablaufdatum "+MHD+", "+timeLeft+" Tage übrig. "+menge+einheit+", "+angebrochenString;
        }

    }

    private JTextField inputName;
    private JTextField inputDay;
    private JTextField inputMonth;
    private JTextField inputYear;
    private JTextField inputAmount;
    private JButton btn;
    private JToggleButton btnAngebr;
    private JToggleButton btnGML;
    public ArrayList<Lebensmittel> lebensmittelList = new ArrayList<>();
    String[] columnNames = {"Lebensmittel","Menge","Ablaufdatum","Zeit Übrig","Angebrochen?","Abgelaufen?"};

    // Constructor to setup GUI components and event handlers
    public testtest () {

        JFrame frame = new JFrame("Test Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 400);
        JPanel panel = new JPanel();

        JLabel label1 = new JLabel("Name:");
        panel.add(label1);
        inputName = new JTextField(8);
        panel.add(inputName);
        JLabel label2 = new JLabel("Tag:");
        panel.add(label2);
        inputDay = new JTextField(4);
        panel.add(inputDay);
        JLabel label3 = new JLabel("Monat:");
        panel.add(label3);
        inputMonth = new JTextField(4);
        panel.add(inputMonth);
        JLabel label4 = new JLabel("Jahr:");
        panel.add(label4);
        inputYear = new JTextField(5);
        panel.add(inputYear);
        JLabel label5 = new JLabel("Menge:");
        panel.add(label5);
        inputAmount = new JTextField(7);
        panel.add(inputAmount);
        btnGML = new JToggleButton("In g oder ml?");
        panel.add(btnGML);
        btnAngebr=new JToggleButton("Angebrochen?");
        panel.add(btnAngebr);
        btn = new JButton("Enter");
        panel.add(btn);

        JTable table = new JTable();
        DefaultTableModel dtm = new DefaultTableModel(0,0);
        dtm.setColumnIdentifiers(columnNames);
        table.setModel(dtm);
        for(int i=0;i<lebensmittelList.size();i++){
            dtm.addRow(lebensmittelList.get(i).toArray());
            if (lebensmittelList.get(i).mhdueberschritten){
                JOptionPane.showMessageDialog(null, lebensmittelList.get(i).LebensmittelName + " ist abgelaufen!", "InfoBox: Abgelaufen", JOptionPane.INFORMATION_MESSAGE);
            }
        }


        JScrollPane scrollPane = new JScrollPane(table);

        JTabbedPane tabpane = new JTabbedPane();
        tabpane.add("input",panel);
        tabpane.add("output", scrollPane);


        frame.getContentPane().add(tabpane);
        frame.setVisible(true);

        btn.addActionListener(e -> {
            Lebensmittel placeholder;
            placeholder = new Lebensmittel(inputName.getText(),LocalDate.of(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText()),Integer.parseInt(inputDay.getText())),btnAngebr.isSelected(),Integer.parseInt(inputAmount.getText()),btnGML.isSelected());
            lebensmittelList.add(placeholder);
            dtm.addRow(lebensmittelList.get(lebensmittelList.size()-1).toArray());
            JOptionPane.showMessageDialog(null, lebensmittelList.get(lebensmittelList.size()-1).LebensmittelName + " ist abgelaufen!", "InfoBox: Abgelaufen", JOptionPane.INFORMATION_MESSAGE);
            inputName.setText("");
            inputMonth.setText("");
            inputDay.setText("");
            inputYear.setText("");
            inputAmount.setText("");
        });
    }

    // The entry main() method
    public static void main(String[] args) {
        // Invoke the constructor to setup the GUI, by allocating an instance
        testtest app = new testtest();
    }

}

