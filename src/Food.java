package src;

import java.io.Serializable;
import java.time.LocalDate;

import static src.Main.daysBetweenDates;


public class Food implements Serializable {
        String foodName;
        LocalDate bestBeforeDate;
        Boolean isOpen;
        int quantity;
        Boolean isGram; // true = g false = ml
        Boolean isExpired; // true = ist abgelaufen
        //Constructor für Lebensmittel Klasse
        public Food(String foodName, LocalDate bestBeforeDate, Boolean isOpen, int quantity, Boolean isGram){
            this.foodName = foodName;
            this.bestBeforeDate = bestBeforeDate;
            this.isOpen = isOpen;
            this.quantity = quantity;
            this.isGram = isGram;
            calculateExpiredStatus();
        }
        //Default contructor, falls nichts angegeben wird.
        public Food(){
            foodName = "Default";
            bestBeforeDate = LocalDate.now();
            isOpen = false;
            quantity = 1;
            isGram = false;
            calculateExpiredStatus();
        }

        //Ausgabe als object array, für die Ansicht in Tabelle nötig.
        public Object[] toArray(){
            calculateExpiredStatus();
            long timeLeft = daysBetweenDates(LocalDate.now(), bestBeforeDate);
            String unit = isGram ? "g" : "ml";
            Object[] object = new Object[6];
            object[0]= foodName;
            object[1]="" + quantity + unit;
            object[2]= bestBeforeDate;
            object[3]= timeLeft;
            object[4]= isOpen;
            object[5]= isExpired;
            return object;
        }

        //Berechnung, ob das Lebensmittel abgelaufen ist oder nicht.
        public void calculateExpiredStatus(){
            isExpired = daysBetweenDates(LocalDate.now(), bestBeforeDate)<0;
        }
        @Override
        public String toString(){
            String isOpenString = isOpen ? "Angebrochen" : "nicht Angebrochen";
            String unit = isGram ? "g" : "ml";
            long timeLeft = daysBetweenDates(LocalDate.now(), bestBeforeDate);

            if(timeLeft<0){
                return foodName + " mit Ablaufdatum "+ bestBeforeDate +" ist abgelaufen. "+ quantity +unit+", "+isOpenString;
            } else if (timeLeft == 0) {
                return foodName + " mit Ablaufdatum "+ bestBeforeDate +" läuft heute ab. "+ quantity +unit+", "+isOpenString;
            }
            return foodName + " mit Ablaufdatum "+ bestBeforeDate +", "+timeLeft+" Tage übrig. "+ quantity +unit+", "+isOpenString;
        }

    }

