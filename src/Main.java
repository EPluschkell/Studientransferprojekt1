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
            mhdBerechnung();
            //id = 1;
        }
        //default contructor, falls nichts angegeben wird.
        public Lebensmittel(){
            LebensmittelName = "Default";
            MHD = LocalDate.now();
            angebrochen = false;
            menge = 1;
            gml = false;
            mhdBerechnung();
        }

        //ausgabe als object array, für die Ansicht in Tabelle nötig.
        public Object[] toArray(){
            mhdBerechnung();
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
        public void mhdBerechnung(){
            mhdueberschritten= daysBetweenDates(LocalDate.now(), MHD)<0;
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
    private JToggleButton btnIsOpen;
    private JToggleButton btnIsGram;
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

            Refrigerator kuehlschrank = new Refrigerator();
            kuehlschrank.add(Fleisch);
            kuehlschrank.add(joghurt);
            kuehlschrank.add(kaese);
            kuehlschrank.sortByMHD();
            saveToFile(kuehlschrank, "test.txt");
        }
        Refrigerator kuehlschrank = loadFromFile("test.txt");

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
        btnIsGram = new JToggleButton("In g oder ml?");
        panel.add(btnIsGram);
        btnIsOpen =new JToggleButton("Angebrochen?");
        panel.add(btnIsOpen);
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
        tableReset(dtm,kuehlschrank);

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
            /*Lebensmittel placeholder;
            placeholder = new Lebensmittel(inputName.getText(),LocalDate.of(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText()),Integer.parseInt(inputDay.getText())),btnAngebr.isSelected(),Integer.parseInt(inputAmount.getText()),btnGML.isSelected());
            kuehlschrank.lebensmittelListe.add(placeholder);*/
            addToRefrigerator(kuehlschrank);
            tableReset(dtm ,kuehlschrank);
            resetInputs();
        });
        delBtn.addActionListener(e -> {
            int placeholder = Integer.parseInt(delInput.getText());
            kuehlschrank.foodList.remove(placeholder-1);
            tableReset(dtm, kuehlschrank);
            delInput.setText("");
        });
        saveBtn.addActionListener(e -> {
            saveToFile(kuehlschrank, "test.txt");
        });

        inputAmount.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER){
                    addToRefrigerator(kuehlschrank);
                    tableReset(dtm, kuehlschrank);
                    resetInputs();
                }
            }
        });

        dtm.addTableModelListener(new TableModelListener() {

            // 1 bug, wenn man das letzte Lebensmittel durch 0 Menge löscht, bekommt man eine Exception
            @Override
            public void tableChanged(TableModelEvent e) {
                int testint = dtm.getRowCount() - 1;
                for (int i = 0; i <= testint; i++) {
                    //check, ob wir auch die selben Gegenstände ansehen
                    if (dtm.getValueAt(i,0).toString().equals(kuehlschrank.foodList.get(i).foodName)){
                        //ist gar keine Menge mehr da?
                        if (dtm.getValueAt(i,1).toString().isEmpty()){
                            kuehlschrank.foodList.remove(i);
                            tableReset(dtm,kuehlschrank);
                            System.out.println("test 2.1");
                            break;
                        }
                        //ist die Menge=0?
                        else if(Integer.parseInt(dtm.getValueAt(i,1).toString().replaceAll("g|ml","0"))==0) {
                            kuehlschrank.foodList.remove(i);
                            tableReset(dtm, kuehlschrank);
                            System.out.println("test 2.2");
                            break;
                        }
                        //andere Menge, die geändert wurde wird auf die Lebensmittelliste überschrieben.
                        else if (!dtm.getValueAt(i,1).toString().equals(kuehlschrank.foodList.get(i).toArray()[1])){
                            kuehlschrank.foodList.get(i).quantity= Integer.parseInt(dtm.getValueAt(i,1).toString().replaceAll("g|ml",""));
                        }
                        /*if(dtm.getValueAt(i, 1).toString().matches("[1-9]")){
                            kuehlschrank.lebensmittelListe.get(i).menge= Integer.parseInt(dtm.getValueAt(i,1).toString().replaceAll("g|ml",""));
                        }else {
                            kuehlschrank.lebensmittelListe.remove(i);
                            dtm.removeRow(i);
                            //tableReset(dtm,kuehlschrank);
                            System.out.println("test 2.3");
                            break;
                        }*/
                    System.out.println("test 3");
                }
            }
        }
        });

    }
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        /*String name1 = "Rinderhack";
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

    public static long daysBetweenDates(LocalDate date1, LocalDate date2) {
        // datum1  = heute, datum2 = mhd
        long Tage = date2.toEpochDay() - date1.toEpochDay();
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
                if (kuehlschrank.lebensmittelListe.get(i).mhdueberschritten){
                    JOptionPane.showMessageDialog(null, kuehlschrank.lebensmittelListe.get(i).LebensmittelName + " ist abgelaufen!", "InfoBox: Abgelaufen", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }*/
    }
    public void addToRefrigerator(Refrigerator refrigerator){
        Food placeholder;
        placeholder = new Food(inputName.getText(),LocalDate.of(Integer.parseInt(inputYear.getText()),Integer.parseInt(inputMonth.getText()),Integer.parseInt(inputDay.getText())), btnIsOpen.isSelected(),Integer.parseInt(inputAmount.getText()), btnIsGram.isSelected());
        refrigerator.foodList.add(placeholder);
    }

    public void resetInputs(){
        inputName.setText("");
        inputMonth.setText("");
        inputDay.setText("");
        inputYear.setText("");
        inputAmount.setText("");
    }
    public static void saveToFile(Refrigerator refrigerator, String fileLocation) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileLocation))) {
            oos.writeObject(refrigerator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Refrigerator loadFromFile(String fileLocation) {
        Refrigerator refrigerator = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileLocation))) {
            refrigerator = (Refrigerator) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return refrigerator;
    }


}