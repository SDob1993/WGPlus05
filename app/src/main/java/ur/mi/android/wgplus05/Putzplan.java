package ur.mi.android.wgplus05;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putzplan);

        initTextViews();
        initListViews();
        initButtons();
        initOnClickListener();
        setUpListViews();


    }

    public static String getStringMonth(int month){
        String[] monthNames = {"Januar", "Februar", "März", "April", "Mai", "Juni", "July", "August", "September", "Oktober", "November", "Dezember"};
        return monthNames[month];
    }

    private void initTextViews(){
        if(Integer.valueOf(Build.VERSION.SDK_INT) > 23) {
            Calendar c = Calendar.getInstance();
            lastMonth = (TextView) findViewById(R.id.lastMonth);
            lastMonth.setText(getStringMonth(c.get(Calendar.MONTH)-1));
            thisMonth = (TextView) findViewById(R.id.thisMonth);
            thisMonth.setText(getStringMonth(c.get(Calendar.MONTH)));
            nextMonth = (TextView) findViewById(R.id.thisMonth);
            nextMonth.setText(getStringMonth(c.get(Calendar.MONTH)+1));

        }
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

        final View popupView = getLayoutInflater().inflate(R.layout.layout_user_popup, null);

        final PopupWindow popupWindow = new PopupWindow(popupView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);


        // Example: If you have a TextView inside `popup_layout.xml`
        final EditText editText1 = (EditText) popupView.findViewById(R.id.edit1);

        final EditText editText2 = (EditText) popupView.findViewById(R.id.edit2);

        final Button button = (Button) popupView.findViewById(R.id.ok_button);





        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);
        popupWindow.update();


        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], location[1] + anchorView.getHeight());

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

}



