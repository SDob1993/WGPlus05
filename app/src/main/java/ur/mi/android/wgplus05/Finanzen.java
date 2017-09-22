package ur.mi.android.wgplus05;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ur.mi.android.wgplus05.CalendarDB;
import ur.mi.android.wgplus05.R;

public class Finanzen extends AppCompatActivity {

    private TextView guthaben;
    private TextView guthabenAnzeige;
    private double guthabenDouble;
    private CalendarDB DB;
    private ListView eingekaufteItems;
    private ArrayList<String> arraylist;
    private ArrayAdapter<String> aa;
    private TextView drueber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finanzen);
        setTitle(R.string.finanzen_title);
        DB = new CalendarDB(this);
        DB.open();
        arraylist=DB.getAllEkitems();
        String name1 = DB.getUserName();
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Jomblo Ngenes.ttf");
        guthaben = (TextView)findViewById(R.id.guthaben);
        guthaben.setTypeface(myTypeface);
        guthabenAnzeige = (TextView)findViewById(R.id.guthabenAnzeige);
        guthabenAnzeige.setTypeface(myTypeface);
        //balance ist Guthaben des Mitbewohners abhängig von den Gesamt-Ausgaben undder Anzahl der Mitbewohner
        double balance = DB.getMeinGuthaben(name1)-DB.getGuthabenGes()/DB.getAnzahlMitbewohner();
        guthaben.setText(""+Double.toString(Math.round(balance))+"€");
        drueber = (TextView) findViewById(R.id.drueber);
        //setzten des Texts auf die Ausgaben des Users
        drueber.setText(Double.toString(Math.round(DB.getMeinGuthaben(name1))));
        eingekaufteItems = (ListView) findViewById(R.id.bought_items);
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);
        eingekaufteItems.setAdapter(aa);
        aa.notifyDataSetChanged();
        refreshArrayList();


    }


    private void refreshArrayList() {
        ArrayList tempList = DB.getAllEkitems();
        arraylist.clear();
        arraylist.addAll(tempList);
        aa.notifyDataSetChanged();
    }



}
