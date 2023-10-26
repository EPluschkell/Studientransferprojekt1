import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static class Essen {
        String EssenName;
        String EssenAblaufdatum;
        int EssenID;
        public Essen(String Name, String Datum ){
            EssenName = Name;
            EssenAblaufdatum = Datum;
            EssenID = 1;
        }
    }
    public static void main(String[] args) {
        System.out.println(LocalDate.now());
        System.out.println("Test!");
        Essen Fleisch = new Essen("Rinderhack", "2023-10-30");

        System.out.println(Fleisch.EssenAblaufdatum+" "+Fleisch.EssenName);
    }
}
