package ur.mi.android.wgplus05;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static ur.mi.android.wgplus05.R.id.action_bar_root;
import static ur.mi.android.wgplus05.R.id.activity_einkaufsliste_id;
import static ur.mi.android.wgplus05.R.id.parent;

public class Einkaufsliste extends AppCompatActivity {

    private ImageButton addButton;
    private ListView einkaufsliste;
    private ArrayList einkaufslisteArrayList;
    private ArrayAdapter aa;
    private FrameLayout mainLayout;
    private TextView wasbrauchenwirnoch;
    private TextView listViewElement;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einkaufsliste);
        setTitle("#NudelnUndWasNoch?");

        setupButton();
        setUpListView();
        setUpListView();
        setUpTextView();
        setUpListViewAdapter();
        setUpLayout();




    }

    private void setupButton(){
        addButton = (ImageButton) findViewById(R.id.einkaufsliste_plus);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupAdd(v);
            }
        });

    }

    private void setUpLayout(){
        mainLayout = (FrameLayout) findViewById(R.id.activity_einkaufsliste_id);
    }

    private void setUpListView(){
        einkaufsliste = (ListView) findViewById(R.id.einkaufsliste);
        einkaufsliste.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                showPopupRemove();
                removeTaskAtPosition(position);
                return true;
            }
        });

    }

    private void setUpTextView(){
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "IndieFlower.ttf");
        wasbrauchenwirnoch = (TextView)findViewById(R.id.wasbrauchenwirnoch);
        wasbrauchenwirnoch.setTypeface(myTypeface);
        /*listViewElement = (TextView)findViewById(R.id.listViewEdit);
        listViewElement.setTypeface(myTypeface);*/
    }

    private void setUpListViewAdapter(){
        einkaufslisteArrayList = new ArrayList<String>();
        aa = new ArrayAdapter<String>(this, R.layout.listview_edit,einkaufslisteArrayList);
        einkaufsliste.setAdapter(aa);

    }

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

        final Spinner spinnerFrequenz = (Spinner) popupView.findViewById(R.id.spinner_frequenz);
        ArrayAdapter<CharSequence> adapterFrequenz = ArrayAdapter.createFromResource(this,
                R.array.frequenz, android.R.layout.simple_spinner_item);
        spinnerFrequenz.setAdapter(adapterFrequenz);

        final EditText editText1 = (EditText) popupView.findViewById(R.id.edit_einkaufsliste);

        final Button button = (Button) popupView.findViewById(R.id.einkaufsliste_popup_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText1.getText().toString().length() != 0) {
                    einkaufslisteArrayList.add(editText1.getText().toString());
                    aa.notifyDataSetChanged();
                    popupWindow.dismiss();
                }
                else Toast.makeText(getApplicationContext(),"Dann gibts halt wieder Nudeln",Toast.LENGTH_LONG).show();
            }
        });

    }public void showPopupRemove() {

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
                    Finanzen finanzen = new Finanzen();
                    finanzen.addGuthabenDouble(Double.parseDouble(preis));
                    popupWindow.dismiss();
                    Toast.makeText(getApplicationContext(),"Dank dir fürs Einkaufen!",Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getApplicationContext(),"Dann gibts halt wieder Nudeln",Toast.LENGTH_LONG).show();
                Log.d("preis",preis);

            }
        });

    }

    private void removeTaskAtPosition(int position) {
        if (einkaufslisteArrayList.get(position) != null) {
            einkaufslisteArrayList.remove(position);
            aa.notifyDataSetChanged();
        }
    }
}
