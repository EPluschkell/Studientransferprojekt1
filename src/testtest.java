package src;

import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces
import java.time.LocalDate;
import java.util.ArrayList;

public class testtest extends Frame {
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

        @Override
        public String toString() {
            return EssenName + ':' + EssenAblaufdatum ;
        }
    }
    private TextField inputName;
    private TextField inputDate;
    private TextArea listText;
    private Button btn;// Declare a Button component
    public ArrayList<Essen> list = new ArrayList<>();
    public ArrayList<Lebensmittel> lebensmittelList = new ArrayList<>();

    // Constructor to setup GUI components and event handlers
    public testtest () {
        setLayout(new FlowLayout());
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
        setVisible(true);         // "super" Frame shows
    }

    // The entry main() method
    public static void main(String[] args) {
        // Invoke the constructor to setup the GUI, by allocating an instance
        testtest app = new testtest();
        // or simply "new AWTCounter();" for an anonymous instance
    }

    private class btnListener implements ActionListener{
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
    }
}

