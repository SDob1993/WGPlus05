package ur.mi.android.wgplus05;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Finanzen extends AppCompatActivity {

    private TextView guthaben;
    private TextView guthabenAnzeige;
    private double guthabenDouble;
    private CalendarDB DB;
    private ListView eingekaufteItems;
    private ArrayList<EinkaufsHistorieItem> eingekaufteItemsArraysList;
    private ArrayAdapter<String> aa;


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
        guthaben.setText(""+Double.toString(Math.round(DB.getGuthaben()))+"€");
        eingekaufteItems = (ListView) findViewById(R.id.bought_items);
        eingekaufteItemsArraysList = new ArrayList<>();
        aa = new ArrayAdapter<String>(this,R.layout.listview_einkauf_history);
        eingekaufteItems.setAdapter(aa);
        aa.notifyDataSetChanged();

    }

    public void addItemToList(EinkaufsHistorieItem einkaufsHistorieItem){
        eingekaufteItemsArraysList.add(einkaufsHistorieItem);
        aa.notifyDataSetChanged();
    }




    public double getGuthabenDouble(){
        return guthabenDouble;
    }

    public void addGuthabenDouble(double guthabenPlus){
        guthabenDouble = guthabenDouble + guthabenPlus;
    }

    public void calculateAverageSum(){

    }
}
