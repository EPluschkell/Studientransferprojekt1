package src;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.applet.*;

public class Main {
    public static class Lebensmittel {
        //int id;
        String LebensmittelName;
        LocalDate MHD;
        Boolean angebrochen;
        int menge;
        Boolean gml; // true = g false = ml
        Boolean mhdueberschritten; // true = ist abgelaufen
        //Constructor für Lebensmittel Klasse
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

        //ausgabe als object array, für die Ansicht in Tabelle nötig.
        public Object[] toArray(){
            long timeLeft = daysBetweenDates(LocalDate.now(),MHD);
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

    //deklarationen für die GUI
    private JTextField inputName;
    private JTextField inputDay;
    private JTextField inputMonth;
    private JTextField inputYear;
    private JTextField inputAmount;
    private JTextField delInput;
    private JButton btn;
    private JButton delBtn;
    private JButton saveBtn;
    private JToggleButton btnAngebr;
    private JToggleButton btnGML;
    String[] columnNames = {"Lebensmittel","Menge","Ablaufdatum","Zeit Übrig","Angebrochen?","Abgelaufen?"};
    public Main() {
        //test daten, später mit Import gespeicherter daten auszuwechseln
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


        //frame und panel aufgesetzt
        JFrame frame = new JFrame("Kühlschrank");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 400);
        JPanel panel = new JPanel();
        //labels und inputs aufsetzen
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
        JLabel label6 = new JLabel("Nummer zum Löschen:");
        panel.add(label6);
        delInput = new JTextField(3);
        panel.add(delInput);
        delBtn = new JButton("Löschen");
        panel.add(delBtn);
        saveBtn = new JButton("Speichern");
        //zweiter reiter, einfach nur die table:
        JTable table = new JTable();
        table.setAutoCreateRowSorter(true);
        DefaultTableModel dtm = new DefaultTableModel(0,0);
        dtm.setColumnIdentifiers(columnNames);
        table.setModel(dtm);
        tableReset(dtm,kuehlschrank);
        /*for(int i=0;i<kuehlschrank.lebensmittelListe.size();i++){
            dtm.addRow(kuehlschrank.lebensmittelListe.get(i).toArray());
            if (kuehlschrank.lebensmittelListe.get(i).mhdueberschritten){
                JOptionPane.showMessageDialog(null, kuehlschrank.lebensmittelListe.get(i).LebensmittelName + " ist abgelaufen!", "InfoBox: Abgelaufen", JOptionPane.INFORMATION_MESSAGE);
            }
        }*/

        //zweite pane und tabs aufsetzen
        JScrollPane scrollPane = new JScrollPane(table);
        JTabbedPane tabpane = new JTabbedPane();
        tabpane.add("input",panel);
        tabpane.add("output", scrollPane);

        //die tabpane auf die frame setzen
        frame.getContentPane().add(tabpane);
        frame.setVisible(true);
        //aktionen, die passieren sollen, wenn der enter button gedrückt wird
        btn.addActionListener(e -> {
            Lebensmittel placeholder;
            placeholder = new Lebensmittel(inputName.getText(),LocalDate.of(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText()),Integer.parseInt(inputDay.getText())),btnAngebr.isSelected(),Integer.parseInt(inputAmount.getText()),btnGML.isSelected());
            kuehlschrank.lebensmittelListe.add(placeholder);
            tableReset(dtm ,kuehlschrank);
            /*dtm.addRow(kuehlschrank.lebensmittelListe.get(kuehlschrank.lebensmittelListe.size()-1).toArray());
            JOptionPane.showMessageDialog(null, kuehlschrank.lebensmittelListe.get(kuehlschrank.lebensmittelListe.size()-1).LebensmittelName + " ist abgelaufen!", "InfoBox: Abgelaufen", JOptionPane.INFORMATION_MESSAGE);*/
            inputName.setText("");
            inputMonth.setText("");
            inputDay.setText("");
            inputYear.setText("");
            inputAmount.setText("");
        });
        delBtn.addActionListener(e -> {
            int placeholder = Integer.parseInt(delInput.getText());
            kuehlschrank.lebensmittelListe.remove(placeholder-1);
            tableReset(dtm, kuehlschrank);
        });
        saveBtn.addActionListener(e -> {

        });

    }
    public static void main(String[] args) {
        /*
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
        }*/

        Main app = new Main();
    }

    public static long daysBetweenDates(LocalDate datum1, LocalDate datum2) {
        // datum1  = heute, datum2 = mhd
        long Tage = datum2.toEpochDay() - datum1.toEpochDay();
        return Tage;
    }

    //Methode, um die table neu aufzubauen, nach mhd sortiert und mit allen Daten.
    public void tableReset(DefaultTableModel table, Kuehlschrank kuehlschrank){
        kuehlschrank.sortByMHD();
        if (table.getRowCount()!=0){
            table.removeRow(0);
            tableReset(table, kuehlschrank);
        }else {
            for (int i = 0; i < kuehlschrank.lebensmittelListe.size(); i++) {
                table.addRow(kuehlschrank.lebensmittelListe.get(i).toArray());
                if (kuehlschrank.lebensmittelListe.get(i).mhdueberschritten){
                    JOptionPane.showMessageDialog(null, kuehlschrank.lebensmittelListe.get(i).LebensmittelName + " ist abgelaufen!", "InfoBox: Abgelaufen", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }


        }