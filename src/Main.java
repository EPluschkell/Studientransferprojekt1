package src;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


public class Main {
    /*public static class Lebensmittel implements Serializable {
        //int id;
        String foodName;
        LocalDate bestBeforeDate;
        Boolean isOpen;
        int quantity;
        Boolean isGram; // true = g false = ml
        Boolean isExpired; // true = ist abgelaufen
        //Constructor für Lebensmittel Klasse
        public Lebensmittel(String Name, LocalDate Datum, Boolean Angebrochen, int Menge, Boolean Einheit){
            foodName = Name;
            bestBeforeDate = Datum;
            isOpen = Angebrochen;
            quantity = Menge;
            isGram = Einheit;
            mhdBerechnung();
            //id = 1;
        }
        //default contructor, falls nichts angegeben wird.
        public Lebensmittel(){
            foodName = "Default";
            bestBeforeDate = LocalDate.now();
            isOpen = false;
            quantity = 1;
            isGram = false;
            mhdBerechnung();
        }

        //ausgabe als object array, für die Ansicht in Tabelle nötig.
        public Object[] toArray(){
            mhdBerechnung();
            long timeLeft = daysBetweenDates(LocalDate.now(),bestBeforeDate);
            String einheit;
            if (isGram){
                einheit = "g";
            }else {
                einheit = "ml";
            }
            Object[] object = new Object[6];
            object[0]= foodName;
            object[1]=""+quantity+einheit;
            object[2]=bestBeforeDate;
            object[3]=timeLeft;
            object[4]=isOpen;
            object[5]=isExpired;
            return object;
        }
        public void mhdBerechnung(){
            isExpired= daysBetweenDates(LocalDate.now(), bestBeforeDate)<0;
        }
        @Override
        public String toString(){
            String angebrochenString;
            String einheit;
            long timeLeft = daysBetweenDates(LocalDate.now(),bestBeforeDate);
            if (isOpen){
                angebrochenString = "Angebrochen";
            }else {
                angebrochenString = "nicht Angebrochen";
            }
            if (isGram){
                einheit = "g";
            }else {
                einheit = "ml";
            }
            if(timeLeft<0){
                return foodName + " mit Ablaufdatum "+bestBeforeDate+" ist abgelaufen. "+quantity+einheit+", "+angebrochenString;
            } else if (timeLeft == 0) {
                return foodName + " mit Ablaufdatum "+bestBeforeDate+" läuft heute ab. "+quantity+einheit+", "+angebrochenString;
            }
            return foodName + " mit Ablaufdatum "+bestBeforeDate+", "+timeLeft+" Tage übrig. "+quantity+einheit+", "+angebrochenString;
        }

    }*/
    /*public static class Kuehlschrank implements Serializable {
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
                    return lebensmittel1.foodName.compareTo(lebensmittel2.foodName);
                }
            });
        }
        //Sortierung nach Mindesthaltbarkeitsdatum
        public void sortByMHD() {
            Collections.sort(lebensmittelListe, new Comparator<Lebensmittel>() {
                @Override
                public int compare(Lebensmittel lebensmittel1, Lebensmittel lebensmittel2) {
                    return lebensmittel1.bestBeforeDate.compareTo(lebensmittel2.bestBeforeDate);
                }
            });
        }
    }*/

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
    public Main() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //test daten, später mit Import gespeicherter daten auszuwechseln
        if (loadFromFile("test.txt")==null){
            String name1 = "Rinderhack";
            LocalDate datum1 = LocalDate.of(2023,11,15);
            Boolean angebrochen1 = false;
            int menge1 = 500;
            boolean einheit1 = true; //gramm

            Food Fleisch = new Food(name1,datum1,angebrochen1,menge1,einheit1);
            Food joghurt = new Food("Joghurt", LocalDate.of(2023, 10, 31), true, 200, false);
            Food kaese = new Food("Käse", LocalDate.of(2023, 11, 30), false, 300, true);

            Refrigerator refrigerator = new Refrigerator();
            refrigerator.add(Fleisch);
            refrigerator.add(joghurt);
            refrigerator.add(kaese);
            refrigerator.sortByMHD();
            saveToFile(refrigerator, "test.txt");
        }
        Refrigerator refrigerator = loadFromFile("test.txt");

        char enter = 13;

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
        panel.add(saveBtn);
        //zweiter reiter, einfach nur die table:
        JTable table = new JTable();
        table.setAutoCreateRowSorter(true);
        DefaultTableModel dtm = new DefaultTableModel(0,0);
        dtm.setColumnIdentifiers(columnNames);
        table.setModel(dtm);
        tableReset(dtm, refrigerator);

        //zweite pane und tabs aufsetzen
        JScrollPane scrollPane = new JScrollPane(table);
        JTabbedPane tabpane = new JTabbedPane();
        tabpane.add("input",panel);
        tabpane.add("output", scrollPane);

        //die tabpane auf die frame setzen
        frame.getContentPane().add(tabpane);
        frame.setVisible(true);/*
        dtm.addTableModelListener(new TableModelListener() {

            // 1 bug, wenn man das letzte Lebensmittel durch 0 Menge löscht, bekommt man eine Exception
            @Override
            public void tableChanged(TableModelEvent e) {
                int testint = dtm.getRowCount()-1;
                for (int i = testint; i >= 0; i--) {
                    if (dtm.getValueAt(i,0).toString().equals(kuehlschrank.lebensmittelListe.get(i).foodName)){
                        //dtm.getValueAt(i,1).toString().matches("^g|^ml");
                        if (dtm.getValueAt(i,1).toString().isEmpty()){
                            kuehlschrank.lebensmittelListe.remove(i);
                            //dtm.removeRow(i);
                            tableReset(dtm,kuehlschrank);
                            //i=0;
                            System.out.println("test 2.1");
                            break;
                        }else if(Integer.parseInt(dtm.getValueAt(i,1).toString().replaceAll("g|ml","0"))==0){
                            kuehlschrank.lebensmittelListe.remove(i);
                            //dtm.removeRow(i);
                            tableReset(dtm,kuehlschrank);
                            //i=0;
                            System.out.println("test 2.2");
                            break;
                        }/*else if(Integer.parseInt(dtm.getValueAt(i,1).toString().replaceAll("g|ml","0"))==0){
                            kuehlschrank.lebensmittelListe.remove(i);
                            //dtm.removeRow(i);
                            tableReset(dtm,kuehlschrank);
                            i=0;
                            System.out.println("test 1");
                        }*/ /*else if (!dtm.getValueAt(i,1).toString().equals(kuehlschrank.lebensmittelListe.get(i).toArray()[1])){
                            kuehlschrank.lebensmittelListe.get(i).quantity= Integer.parseInt(dtm.getValueAt(i,1).toString().replaceAll("g|ml",""));
                        }
                        System.out.println("test 3");
                    }
                }
                //kuehlschrank.sortByMHD();
                //tableReset(dtm,kuehlschrank);
            }
        });*/
        //aktionen, die passieren sollen, wenn der enter button gedrückt wird
        btn.addActionListener(e -> {
            /*Lebensmittel placeholder;
            placeholder = new Lebensmittel(inputName.getText(),LocalDate.of(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText()),Integer.parseInt(inputDay.getText())),btnAngebr.isSelected(),Integer.parseInt(inputAmount.getText()),btnGML.isSelected());
            kuehlschrank.lebensmittelListe.add(placeholder);*/
            addToKuehlschrank(refrigerator);
            tableReset(dtm , refrigerator);
            resetInputs();
        });
        delBtn.addActionListener(e -> {
            int placeholder = Integer.parseInt(delInput.getText());
            refrigerator.foodList.remove(placeholder-1);
            tableReset(dtm, refrigerator);
            delInput.setText("");
        });
        saveBtn.addActionListener(e -> {
            saveToFile(refrigerator, "test.txt");
        });

        inputAmount.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER){
                    addToKuehlschrank(refrigerator);
                    tableReset(dtm, refrigerator);
                    resetInputs();
                }
            }
        });

        dtm.addTableModelListener(new TableModelListener() {

            // 1 bug, wenn man das letzte Lebensmittel durch 0 Menge löscht, bekommt man eine Exception
            @Override
            public void tableChanged(TableModelEvent e) {
                int testint = dtm.getRowCount()-1;
                for (int i = testint; i >= 0; i--) {
                    //check, ob wir auch die selben Gegenstände ansehen
                    if (dtm.getValueAt(i,0).toString().equals(refrigerator.foodList.get(i).foodName)){
                        //ist gar keine Menge mehr da?
                        if (dtm.getValueAt(i,1).toString().isEmpty()){
                            refrigerator.foodList.remove(i);
                            tableReset(dtm, refrigerator);
                            System.out.println("test 2.1");
                            break;
                        }
                        //ist die Menge=0?
                        else if(Integer.parseInt(dtm.getValueAt(i,1).toString().replaceAll("g|ml","0"))==0) {
                            refrigerator.foodList.remove(i);
                            tableReset(dtm, refrigerator);
                            System.out.println("test 2.2");
                            break;
                        }
                        //andere Menge, die geändert wurde wird auf die Lebensmittelliste überschrieben.
                        else if (!dtm.getValueAt(i,1).toString().equals(refrigerator.foodList.get(i).toArray()[1])){
                            refrigerator.foodList.get(i).quantity = Integer.parseInt(dtm.getValueAt(i,1).toString().replaceAll("g|ml",""));
                        }
                        System.out.println("test 3");
                    }
                }
            }
        });

    }
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
    public void tableReset(DefaultTableModel table, Refrigerator refrigerator){
        refrigerator.sortByMHD();
        for (int i = table.getRowCount()-1; i >= 0; i--) {
            table.removeRow(i);
        }
        for (int i = 0; i < refrigerator.foodList.size(); i++) {
            table.addRow(refrigerator.foodList.get(i).toArray());
            if (refrigerator.foodList.get(i).isExpired) {
                JOptionPane.showMessageDialog(null, refrigerator.foodList.get(i).foodName + " ist abgelaufen!", "InfoBox: Abgelaufen", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        /*if (table.getRowCount()!=0){
            table.removeRow(0);
            tableReset(table, kuehlschrank);
        }else {
            for (int i = 0; i < kuehlschrank.lebensmittelListe.size(); i++) {
                table.addRow(kuehlschrank.lebensmittelListe.get(i).toArray());
                if (kuehlschrank.lebensmittelListe.get(i).isExpired){
                    JOptionPane.showMessageDialog(null, kuehlschrank.lebensmittelListe.get(i).foodName + " ist abgelaufen!", "InfoBox: Abgelaufen", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }*/
    }
    public void addToKuehlschrank(Refrigerator refrigerator){
        Food placeholder;
        placeholder = new Food(inputName.getText(),LocalDate.of(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText()),Integer.parseInt(inputDay.getText())),btnAngebr.isSelected(),Integer.parseInt(inputAmount.getText()),btnGML.isSelected());
        refrigerator.foodList.add(placeholder);
    }

    public void resetInputs(){
        inputName.setText("");
        inputMonth.setText("");
        inputDay.setText("");
        inputYear.setText("");
        inputAmount.setText("");
    }
    public static void saveToFile(Refrigerator refrigerator, String dateipfad) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dateipfad))) {
            oos.writeObject(refrigerator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Refrigerator loadFromFile(String dateipfad) {
        Refrigerator refrigerator = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dateipfad))) {
            refrigerator = (Refrigerator) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return refrigerator;
    }


}