package src;

import java.io.Serializable;
import java.time.LocalDate;

import static src.Main.daysBetweenDates;


public class Lebensmittel implements Serializable {
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

    }

