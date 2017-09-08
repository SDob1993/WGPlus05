package ur.mi.android.wgplus05;

/**
 * Created by simon on 08.09.2017.
 */

public class EinkaufsItem {
    private String name;

    public EinkaufsItem(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }



    @Override
    public String toString() {
        return "Name: " + getName() ;
    }
}

