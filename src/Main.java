import java.time.LocalDate;

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
            gml = true;
            mhdueberschritten = !MHD.isAfter(LocalDate.now());
        }

    }
    public static void main(String[] args) {
        String name1 = "Rinderhack";
        LocalDate datum1 = LocalDate.of(2023,10,30);
        Boolean angebrochen1 = false;
        int menge1 = 500;
        boolean einheit1 = true; //gramm

        Lebensmittel Fleisch = new Lebensmittel(name1,datum1,angebrochen1,menge1,einheit1);
        if(Fleisch.mhdueberschritten){
            System.out.println("Das Lebensmittel " + Fleisch.LebensmittelName + " ist abgelaufen");
        } else{
            System.out.println("Das Lebensmittel " + Fleisch.LebensmittelName + " ist noch " + datum1.until(LocalDate.now()) + " haltbar.");
        }

    }
}