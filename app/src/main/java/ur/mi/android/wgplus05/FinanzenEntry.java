package ur.mi.android.wgplus05;

/**
 * Created by simon on 09.09.2017.
 */

public class FinanzenEntry {
    private double money;

    public FinanzenEntry(double Money){
        this.money = Money;
    }

    public double getGuthabenDouble(){
        return money;
    }

    public void addGuthabenDouble(double guthabenPlus){
        money = money + guthabenPlus;
    }
}
