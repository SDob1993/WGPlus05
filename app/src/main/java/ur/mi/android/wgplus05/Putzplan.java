package ur.mi.android.wgplus05;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Putzplan extends ActionBarActivity {

    private Button addUserLastMonth;
    private Button addUserThisMonth;
    private Button addUserNextMonth;

    private TextView lastMonth;
    private TextView thisMonth;
    private TextView nextMonth;

    private ArrayList ArrayListUserLastMonthKueche;
    private ArrayList ArrayListUserLastMonthBad;
    private ArrayList ArrayListUserThisMonthKueche;
    private ArrayList ArrayListUserThisMonthBad;
    private ArrayList ArrayListUserNextMonthKueche;
    private ArrayList ArrayListUserNextMonthBad;

    private ArrayList currentArrayListUserKueche;
    private ArrayList currentArrayListUserBad;

    private ArrayAdapter ArrayAdapterListUserLastMonthKueche;
    private ArrayAdapter ArrayAdapterListUserLastMonthBad;
    private ArrayAdapter ArrayAdapterListUserThisMonthKueche;
    private ArrayAdapter ArrayAdapterListUserThisMonthBad;
    private ArrayAdapter ArrayAdapterListUserNextMonthKueche;
    private ArrayAdapter ArrayAdapterListUserNextMonthBad;

    private ArrayAdapter currentArrayAdapterListUserKueche;
    private ArrayAdapter currentArrayAdapterListUserBad;

    private ListView ListUserLastMonthKueche;
    private ListView ListUserLastMonthBad;
    private ListView ListUserThisMonthKueche;
    private ListView ListUserThisMonthBad;
    private ListView ListUserNextMonthKueche;
    private ListView ListUserNextMonthBad;

    private MyDatabaseAdapter db_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putzplan);
        setTitle("#EinerMussesMachen");

        //initTextViews();
        initListViews();
        initButtons();
        initOnClickListener();
        setUpListViews();
       // initDB();


    }

    public static String getStringMonth(int month){
        String[] monthNames = {"Januar", "Februar", "März", "April", "Mai", "Juni", "July", "August", "September", "Oktober", "November", "Dezember"};
        return monthNames[month];
    }

   /* private void initTextViews(){
        if(Integer.valueOf(Build.VERSION.SDK_INT) > 23) {
            Calendar c = Calendar.getInstance();
            lastMonth = (TextView) findViewById(R.id.lastMonth);
            lastMonth.setText(getStringMonth(c.get(Calendar.MONTH)-1));
            thisMonth = (TextView) findViewById(R.id.thisMonth);
            thisMonth.setText(getStringMonth(c.get(Calendar.MONTH)));
            nextMonth = (TextView) findViewById(R.id.thisMonth);
            nextMonth.setText(getStringMonth(c.get(Calendar.MONTH)+1));

        }
    } */

    private void initDB(){
        db_adapter.open();
        updateList();
    }

    private void updateList(){

    }

    private void setUpListViews(){
        ArrayListUserLastMonthKueche  = new ArrayList<String>();
        ArrayAdapterListUserLastMonthKueche = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ArrayListUserLastMonthKueche);
        ListUserLastMonthKueche.setAdapter(ArrayAdapterListUserLastMonthKueche);

        ArrayListUserLastMonthBad  = new ArrayList<String>();
        ArrayAdapterListUserLastMonthBad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ArrayListUserLastMonthBad);
        ListUserLastMonthBad.setAdapter(ArrayAdapterListUserLastMonthBad);

        ArrayListUserThisMonthKueche  = new ArrayList<String>();
        ArrayAdapterListUserThisMonthKueche = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ArrayListUserThisMonthKueche);
        ListUserThisMonthKueche.setAdapter(ArrayAdapterListUserThisMonthKueche);

        ArrayListUserThisMonthBad  = new ArrayList<String>();
        ArrayAdapterListUserThisMonthBad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ArrayListUserThisMonthBad);
        ListUserThisMonthBad.setAdapter(ArrayAdapterListUserThisMonthBad);

        ArrayListUserNextMonthKueche  = new ArrayList<String>();
        ArrayAdapterListUserNextMonthKueche = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ArrayListUserNextMonthKueche);
        ListUserNextMonthKueche.setAdapter(ArrayAdapterListUserNextMonthKueche);

        ArrayListUserNextMonthBad  = new ArrayList<String>();
        ArrayAdapterListUserNextMonthBad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ArrayListUserNextMonthBad);
        ListUserNextMonthBad.setAdapter(ArrayAdapterListUserNextMonthBad);
    }

    private void initOnClickListener() {
        addUserLastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentArrayAdapterListUserKueche = ArrayAdapterListUserLastMonthKueche;
                currentArrayAdapterListUserBad = ArrayAdapterListUserLastMonthBad;
                currentArrayListUserKueche = ArrayListUserLastMonthKueche;
                currentArrayListUserBad = ArrayListUserLastMonthBad;

                showPopup(view);
            }
        });
        addUserThisMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentArrayAdapterListUserKueche = ArrayAdapterListUserThisMonthKueche;
                currentArrayAdapterListUserBad = ArrayAdapterListUserThisMonthBad;
                currentArrayListUserKueche = ArrayListUserThisMonthKueche;
                currentArrayListUserBad = ArrayListUserThisMonthBad;

                showPopup(view);

            }
        });
        addUserNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentArrayAdapterListUserKueche = ArrayAdapterListUserNextMonthKueche;
                currentArrayAdapterListUserBad = ArrayAdapterListUserNextMonthBad;
                currentArrayListUserKueche = ArrayListUserNextMonthKueche;
                currentArrayListUserBad = ArrayListUserNextMonthBad;

                showPopup(view);

            }
        });

    }

    private void initButtons() {
        addUserLastMonth = (Button) findViewById(R.id.button_add_lastMonth);
        addUserThisMonth = (Button) findViewById(R.id.button_add_thisMonth);
        addUserNextMonth = (Button) findViewById(R.id.button_add_nextMonth);

    }

    private void initListViews(){
        ListUserLastMonthKueche = (ListView) findViewById(R.id.list_lastMonth_kueche);
        ListUserLastMonthBad = (ListView) findViewById(R.id.list_lastMonth_bad);
        ListUserThisMonthKueche = (ListView) findViewById(R.id.list_thistMonth_kueche);
        ListUserThisMonthBad = (ListView) findViewById(R.id.list_thisMonth_bad);
        ListUserNextMonthKueche = (ListView) findViewById(R.id.list_nexttMonth_kueche);
        ListUserNextMonthBad = (ListView) findViewById(R.id.list_nextMonth_bad);
    }

    public void showPopup(View anchorView) {


        // Example: If you have a TextView inside `popup_layout.xml`


        // get a reference to the already created main layout
        FrameLayout mainLayout = (FrameLayout) findViewById(R.id.activity_einkaufsliste_id);

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

        final EditText editText1 = (EditText) popupView.findViewById(R.id.edit1);

        final EditText editText2 = (EditText) popupView.findViewById(R.id.edit2);

        final Button button = (Button) popupView.findViewById(R.id.ok_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText1.getText().toString().length() == 0 && editText2.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(),"Musst scho an Text eingeben",Toast.LENGTH_LONG).show();

                }
                if (editText1.getText().toString().length() != 0) {
                    currentArrayListUserKueche.add(editText1.getText().toString());
                    currentArrayAdapterListUserKueche.notifyDataSetChanged();
                }
                if (editText2.getText().toString().length() != 0) {
                    currentArrayListUserBad.add(editText2.getText().toString());
                    currentArrayAdapterListUserBad.notifyDataSetChanged();
                }
                popupWindow.dismiss();
            }
        });

    }

    private void removeTaskAtPositionKueche(int position) {
        if (currentArrayListUserKueche.get(position) != null) {
            currentArrayListUserKueche.remove(position);
            currentArrayAdapterListUserKueche.notifyDataSetChanged();
        }
    }

    private void removeTaskAtPositionBad(int position) {
        if (currentArrayListUserBad.get(position) != null) {
            currentArrayListUserBad.remove(position);
            currentArrayAdapterListUserBad.notifyDataSetChanged();
        }
    }

}



