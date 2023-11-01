package src;

import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class testtest {
    public static class Lebensmittel extends JFrame{
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
        public void setMHD(int Tag,int Monat,int Jahr){
            MHD = LocalDate.of(Jahr,Monat,Tag);
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
            object[3]=MHD;
            object[4]=timeLeft;
            object[5]=angebrochen;
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
    public static class Essen {
        String EssenName;
        String EssenAblaufdatum;

        public Essen(String Name, String Datum){
            EssenName = Name;
            EssenAblaufdatum = Datum;

        }
        public Object[] toArray(){
            Object[] object = new Object[2];
            object[0]= EssenName;
            object[1]=EssenAblaufdatum;
            return object;
        }
        @Override
        public String toString() {
            return EssenName + ':' + EssenAblaufdatum ;
        }
    }
   /* private TextField inputName;
    private TextField inputDate;
    private TextArea listText;
    private Button btn;// Declare a Button component*/
    private JTextArea listText;
    private JTextField inputName;
    private JTextField inputDay;
    private JTextField inputMonth;
    private JTextField inputYear;
    private JTextField inputAmount;
    private JButton btn;
    private JToggleButton btnAngebr;
    private JToggleButton btnGML;
    public static ArrayList<Essen> list = new ArrayList<>();
    public ArrayList<Lebensmittel> lebensmittelList = new ArrayList<>();
    String[] columnNames = {"Lebensmittel","Menge","Ablaufdatum","Angebrochen?","Abgelaufen?"};
    //String[] columnNames = {"Lebensmittel","Ablaufdatum"};

    // Constructor to setup GUI components and event handlers
    public testtest () {
       /* setLayout(new FlowLayout());
        // "super" Frame, which is a Container, sets its layout to FlowLayout to arrange
        // the components from left-to-right, and flow to next row from top-to-bottom.


        inputName = new TextField(30);
        inputName.setEditable(true);
        add(inputName);
        inputDate=new TextField(30);
        inputDate.setEditable(true);
        add(inputDate);
        btn = new Button("enter");
        add(btn);
        listText = new TextArea();
        listText.setEditable(false);
        add(listText);


        btnListener listener = new btnListener();
        btn.addActionListener(listener);

        setTitle("Kühlschrank");  // "super" Frame sets its title
        setSize(600, 300);        // "super" Frame sets its initial window size

        // For inspecting the Container/Components objects
        setVisible(true);         // "super" Frame shows*/

        /*Object[][] data = new Object[list.size()+100][list.getFirst().toArray().length];
        for(int i =0;i<list.size();i++){
            data[i]= list.get(i).toArray();
        }*/


        JFrame frame = new JFrame("Test Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        JPanel panel = new JPanel();
        inputName = new JTextField(15);

        panel.add(inputName);
        inputDay = new JTextField(10);
        panel.add(inputDay);
        inputMonth = new JTextField(5);
        panel.add(inputMonth);
        inputYear = new JTextField(5);
        panel.add(inputYear);
        inputAmount = new JTextField(8);
        panel.add(inputAmount);
        btnGML = new JToggleButton("Flüssigkeit?");
        panel.add(btnGML);
        btnAngebr=new JToggleButton("Angebrochen?");
        panel.add(btnAngebr);
        btn = new JButton("Enter");
        panel.add(btn);

        JPanel panel1 = new JPanel();
        JTable table = new JTable();
        DefaultTableModel dtm = new DefaultTableModel(0,0);
        dtm.setColumnIdentifiers(columnNames);
        table.setModel(dtm);
        for(int i=0;i<lebensmittelList.size();i++){
            dtm.addRow(lebensmittelList.get(i).toArray());
        }


        listText = new JTextArea();
        listText.setEditable(false);
        panel1.add(table);
        JScrollPane scrollPane = new JScrollPane(panel1,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JTabbedPane tabpane = new JTabbedPane();
        tabpane.add("input",panel);
        tabpane.add("output", scrollPane);


        frame.getContentPane().add(tabpane);
        //frame.getContentPane().add(BorderLayout.SOUTH, panel1);
        frame.setVisible(true);

        btn.addActionListener(e -> {
            listText.setText("");
            Lebensmittel placeholder;
            placeholder = new Lebensmittel(inputName.getText(),LocalDate.of(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText()),Integer.parseInt(inputDay.getText())),btnAngebr.isSelected(),Integer.parseInt(inputAmount.getText()),btnGML.isSelected());
            Essen essenPlaceholder;
            lebensmittelList.add(placeholder);
            essenPlaceholder = new Essen(inputName.getText(), inputDay.getText());
            list.add(essenPlaceholder);
            String textPlaceholder="";
            //listText.setText(list.toString());
            dtm.addRow(lebensmittelList.get(lebensmittelList.size()-1).toArray());
            for(int i=0;i<list.size();i++){
                //list.get(i).toString();
                //listText.setText((list.get(i)).toString());
                listText.append((list.get(i)).toString()+"\n");

            }
        });
    }

    // The entry main() method
    public static void main(String[] args) {
        Essen brot = new Essen("brot", "23.11.23");
        Essen fleisch = new Essen("Fleisch","08.11.23");
        Essen Sauce = new Essen("Sauce", "05.11.23");
        Essen Kartoffeln = new Essen("Kartoffeln", "30.11.23");
        list.add(brot);
        list.add(fleisch);
        list.add(Sauce);
        list.add(Kartoffeln);
        // Invoke the constructor to setup the GUI, by allocating an instance
        testtest app = new testtest();
    }

    /*private class btnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt) {
            listText.setText("");
            Essen placeholder;
            placeholder = new Essen(inputName.getText(),inputDate.getText());
            list.add(placeholder);
            String textPlaceholder="";
            //listText.setText(list.toString());
            for(int i=0;i<list.size();i++){
                //list.get(i).toString();
                //listText.setText((list.get(i)).toString());
                listText.append((list.get(i)).toString()+"\n");
            }
        }
    }*/
}

