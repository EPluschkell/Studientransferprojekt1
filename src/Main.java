package src;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

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
            if(mhdueberschritten){
                return LebensmittelName + " mit Ablaufdatum "+MHD+" ist abgelaufen. "+menge+einheit+", "+angebrochenString;
            }
        return LebensmittelName + " mit Ablaufdatum "+MHD+", "+timeLeft+" Tage übrig. "+menge+einheit+", "+angebrochenString;
        }

    }
    public static ArrayList<Lebensmittel> kuehlschrank = new ArrayList<>();
    public static void main(String[] args) {
        String name1 = "Rinderhack";
        LocalDate datum1 = LocalDate.of(2023,11,15);
        Boolean angebrochen1 = false;
        int menge1 = 500;
        boolean einheit1 = true; //gramm

        Lebensmittel Fleisch = new Lebensmittel(name1,datum1,angebrochen1,menge1,einheit1);
        if(Fleisch.mhdueberschritten){
            System.out.println("Das Lebensmittel " + Fleisch.LebensmittelName + " ist abgelaufen");
        } else{
            System.out.println("Das Lebensmittel " + Fleisch.LebensmittelName + " ist noch " + datum1.until(LocalDate.now()) + " haltbar.");
        }
        System.out.println(Fleisch);
        kuehlschrank.add(Fleisch);
        Lebensmittel testLebensmittel = new Lebensmittel();
        kuehlschrank.add(testLebensmittel);
        System.out.println(kuehlschrank.toString());
    }

}