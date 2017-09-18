package ur.mi.android.wgplus05;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Finanzen extends AppCompatActivity {

    private TextView guthaben;
    private TextView guthabenAnzeige;
    private double guthabenDouble;
    private CalendarDB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finanzen);
        setTitle("#MoneyTalk");
        DB = new CalendarDB(this);
        DB.open();

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Jomblo Ngenes.ttf");
        guthaben = (TextView)findViewById(R.id.guthaben);
        guthaben.setTypeface(myTypeface);
        guthabenAnzeige = (TextView)findViewById(R.id.guthabenAnzeige);
        guthabenAnzeige.setTypeface(myTypeface);
        guthaben.setText(""+Double.toString(DB.getGuthaben())+"€");
    }




    public double getGuthabenDouble(){
        return guthabenDouble;
    }

    public void addGuthabenDouble(double guthabenPlus){
        guthabenDouble = guthabenDouble + guthabenPlus;
    }
}
