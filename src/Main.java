import java.time.LocalDate;

public class Main {
    public class Essen {
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
    }
}
