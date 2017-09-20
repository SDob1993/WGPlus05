package ur.mi.android.wgplus05;

import java.util.Date;

/**
 * Created by simon on 08.09.2017.
 */

public class EinkaufsItem {
    private String name;
    private Date date;

    public EinkaufsItem(String name) {
        this.name = name;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public Date getDate(){
        return date;
    }



    @Override
    public String toString() {
        return "Name: " + getName() ;
    }
}

