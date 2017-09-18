package ur.mi.android.wgplus05;


import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class Einkaufsliste2 extends AppCompatActivity {

    private ImageButton addButton;
    private ArrayList<EinkaufsItem> items;
    private EinkaufsAdapter item_adapter;
    private CalendarDB EKDB;
    private FrameLayout mainLayout;
    private TextView wasbrauchenwirnoch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einkaufsliste);
        setTitle("#NudelnUndWasNoch?");
        setupButton();
        setUpTextView();
        initTaskList();
        initDatabase();
        initListView();
        refreshArrayList();
        mainLayout = (FrameLayout) findViewById(R.id.activity_einkaufsliste_id);

    }

    private void setUpTextView(){
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "IndieFlower.ttf");
        wasbrauchenwirnoch = (TextView)findViewById(R.id.wasbrauchenwirnoch);
        wasbrauchenwirnoch.setTypeface(myTypeface);

    }

    private void setupButton(){
        addButton = (ImageButton) findViewById(R.id.einkaufsliste_plus);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupAdd(v);
            }
        });}

    public void showPopupAdd(View anchorView) {

        // get a reference to the already created main layout


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_user_popup_einkaufsliste, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);

        final EditText editText1 = (EditText) popupView.findViewById(R.id.edit_einkaufsliste);

        final Button button = (Button) popupView.findViewById(R.id.einkaufsliste_popup_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText1.getText().toString().length() != 0) {
                    EinkaufsItem item = new EinkaufsItem(editText1.getText().toString());
                    items.add(item);
                    EKDB.insertEinkaufItem(item);
                    item_adapter.notifyDataSetChanged();
                    refreshArrayList();
                    popupWindow.dismiss();
                }
                else Toast.makeText(getApplicationContext(),"Dann gibts halt wieder Nudeln",Toast.LENGTH_LONG).show();
            }
        });

    }


    public void showPopupRemove() {

        // get a reference to the already created main layout


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_user_popup_einkaufsliste_remove, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);

        final NumberPicker preis_euro = (NumberPicker) popupView.findViewById(R.id.edit_einkaufsliste_preis_euro);
        preis_euro.setMaxValue(20);
        preis_euro.setMinValue(0);
        final NumberPicker preis_cent = (NumberPicker) popupView.findViewById(R.id.edit_einkaufsliste_preis_cent);
        preis_cent.setMaxValue(99);
        final Button button = (Button) popupView.findViewById(R.id.einkaufsliste_popup_button_remove);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String preis = ""+preis_euro.getValue()+"."+preis_cent.getValue();
                if(!preis.equals("0.0")) {
                    double preisneu =Double.parseDouble(preis)+EKDB.getGuthaben();
                    String username = EKDB.getUserName();
                    EKDB.insertFinanzen(preisneu,username);
                    popupWindow.dismiss();
                }
                else Toast.makeText(getApplicationContext(),"Dann gibts halt wieder Nudeln",Toast.LENGTH_LONG).show();
                Log.d("preis",preis);

            }
        });

    }



    private void refreshArrayList() {
        ArrayList tempList = EKDB.getAllEinkaufItems();
        items.clear();
        items.addAll(tempList);
        item_adapter.notifyDataSetChanged();
    }



    private void initListView() {
        ListView list = (ListView) findViewById(R.id.einkaufsliste);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                removeTaskAtPosition(position);
                showPopupRemove();
                return true;
            }
        });
    }

    private void initDatabase() {
        EKDB = new CalendarDB(this);
        EKDB.open();
    }

    private void initTaskList() {
        items = new ArrayList<EinkaufsItem>();
        initListAdapter();
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.einkaufsliste);
        item_adapter = new EinkaufsAdapter(this, items);
        list.setAdapter(item_adapter);
    }

    private void removeTaskAtPosition(int position) {
        if (items.get(position) != null) {
            EKDB.removeEinkaufItem(items.get(position));
            refreshArrayList();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EKDB.close();
    }

}
