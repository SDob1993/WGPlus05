package ur.mi.android.wgplus05;

/**
 * Created by Gucci on 08.09.2017.
 */

public class PutzplanItem {

        private String titel;
        private String frequenz;
        private String date;
        private String tag;
        private String name;
        private int aufwand;

    public PutzplanItem(String titel, String frequenz, String date, String tag, String name, int aufwand){
        this.titel = titel;
        this.frequenz = frequenz;
        this.date = date;
        this.tag = tag;
        this.name = name;
        this.aufwand = aufwand;
    }

    public String getFrequenz() {
        return frequenz;
    }

    public String getDate() {
        return date;
    }

    public int getAufwand() {
        return aufwand;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getTitel() {
        return titel;
    }

    public String toText(){
        return ""+titel+date+aufwand+name+tag+frequenz;
    }
}
