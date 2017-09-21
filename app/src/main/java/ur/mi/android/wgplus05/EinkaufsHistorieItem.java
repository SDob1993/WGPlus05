package ur.mi.android.wgplus05;

import java.util.Date;

/**
 * Created by Gucci on 20.09.2017.
 */

public class EinkaufsHistorieItem{
    private String name;
    //private Date date;
    private String preis;

    public EinkaufsHistorieItem(String name, Date date, String preis) {
        this.name = name;
        //this.date = date;
        this.preis = preis;
    }


    public String getName() {
        return name;
    }

    //public Date getDate(){
      //  return date;
    //}



    @Override
    public String toString() {
        return getName()+" "+preis;
    }
}
