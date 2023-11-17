package src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Kuehlschrank implements Serializable {
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
