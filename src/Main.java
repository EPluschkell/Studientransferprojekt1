package src;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main {
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
            long timeLeft = daysBetweenDates(LocalDate.now(),MHD);
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
            if(timeLeft<0){
                return LebensmittelName + " mit Ablaufdatum "+MHD+" ist abgelaufen. "+menge+einheit+", "+angebrochenString;
            } else if (timeLeft == 0) {
                return LebensmittelName + " mit Ablaufdatum "+MHD+" läuft heute ab. "+menge+einheit+", "+angebrochenString;
            }
            return LebensmittelName + " mit Ablaufdatum "+MHD+", "+timeLeft+" Tage übrig. "+menge+einheit+", "+angebrochenString;
        }

    }
    public static class Kuehlschrank {
        ArrayList<Lebensmittel> lebensmittelListe;

        public Kuehlschrank() {
            lebensmittelListe = new ArrayList<Lebensmittel>();
        }

        public void add(Lebensmittel lebensmittel){
            lebensmittelListe.add(lebensmittel);
        }

        // Sortierung nach Lebensmittelname
        public void sortByName() {
            Collections.sort(lebensmittelListe, new Comparator<Lebensmittel>() {
                @Override
                public int compare(Lebensmittel lebensmittel1, Lebensmittel lebensmittel2) {
                    return lebensmittel1.LebensmittelName.compareTo(lebensmittel2.LebensmittelName);
                }
            });
        }
        //Sortierung nach Mindesthaltbarkeitsdatum
        public void sortByMHD() {
            Collections.sort(lebensmittelListe, new Comparator<Lebensmittel>() {
                @Override
                public int compare(Lebensmittel lebensmittel1, Lebensmittel lebensmittel2) {
                    return lebensmittel1.MHD.compareTo(lebensmittel2.MHD);
                }
            });
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
    //public ArrayList<testtest.Lebensmittel> lebensmittelList = new ArrayList<>();
    String[] columnNames = {"Lebensmittel","Menge","Ablaufdatum","Zeit Übrig","Angebrochen?","Abgelaufen?"};
    public Main(){

        String name1 = "Rinderhack";
        LocalDate datum1 = LocalDate.of(2023,11,15);
        Boolean angebrochen1 = false;
        int menge1 = 500;
        boolean einheit1 = true; //gramm

        Lebensmittel Fleisch = new Lebensmittel(name1,datum1,angebrochen1,menge1,einheit1);
        Lebensmittel joghurt = new Lebensmittel("Joghurt", LocalDate.of(2023, 10, 31), true, 200, false);
        Lebensmittel kaese = new Lebensmittel("Käse", LocalDate.of(2023, 11, 30), false, 300, true);

        Kuehlschrank kuehlschrank = new Kuehlschrank();
        kuehlschrank.add(Fleisch);
        kuehlschrank.add(joghurt);
        kuehlschrank.add(kaese);
        kuehlschrank.sortByMHD();



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
        for(int i=0;i<kuehlschrank.lebensmittelListe.size();i++){
            dtm.addRow(kuehlschrank.lebensmittelListe.get(i).toArray());
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
            kuehlschrank.lebensmittelListe.add(placeholder);
            dtm.addRow(kuehlschrank.lebensmittelListe.get(kuehlschrank.lebensmittelListe.size()-1).toArray());
            inputName.setText("");
            inputMonth.setText("");
            inputDay.setText("");
            inputYear.setText("");
            inputAmount.setText("");
        });
    }
    public static void main(String[] args) {
        String name1 = "Rinderhack";
        LocalDate datum1 = LocalDate.of(2023,11,15);
        Boolean angebrochen1 = false;
        int menge1 = 500;
        boolean einheit1 = true; //gramm

        Lebensmittel Fleisch = new Lebensmittel(name1,datum1,angebrochen1,menge1,einheit1);
        Lebensmittel joghurt = new Lebensmittel("Joghurt", LocalDate.of(2023, 10, 31), true, 200, false);
        Lebensmittel kaese = new Lebensmittel("Käse", LocalDate.of(2023, 11, 30), false, 300, true);

        Kuehlschrank kuehlschrank = new Kuehlschrank();
        kuehlschrank.add(Fleisch);
        kuehlschrank.add(joghurt);
        kuehlschrank.add(kaese);
        kuehlschrank.sortByMHD();

        // Ausgabe
        for (Lebensmittel lebensmittel : kuehlschrank.lebensmittelListe) {
            System.out.println(lebensmittel);
        }

        Main app = new Main();
    }

    public static long daysBetweenDates(LocalDate datum1, LocalDate datum2) {
        // datum1  = heute, datum2 = mhd
        long Tage = datum2.toEpochDay() - datum1.toEpochDay();
        return Tage;
    }


}