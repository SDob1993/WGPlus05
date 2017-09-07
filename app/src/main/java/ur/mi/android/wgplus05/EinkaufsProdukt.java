package ur.mi.android.wgplus05;


import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class EinkaufsProdukt {

    private String name;
    private int position;

    public EinkaufsProdukt(String name, Integer position) {
        this.name = name;
        this.position = position;
    }


    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }


    @Override
    public String toString() {
        return "Name: " + getName();
    }
}

