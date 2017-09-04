package ur.mi.android.wgplus05;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Putzplan extends ActionBarActivity {

    private Button addUserLastMonth;
    private Button addUserThisMonth;
    private Button addUserNextMonth;

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

        initListViews();
        initButtons();
        initOnClickListener();
    }

    private void initOnClickListener() {
        addUserLastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
        addUserThisMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        addUserNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    public String showPopup(View anchorView) {

        View popupView = getLayoutInflater().inflate(R.layout.layout_user_popup, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        // Example: If you have a TextView inside `popup_layout.xml`
        EditText editText = (EditText) findViewById(R.id.edit1);
        editText.getText().toString();



        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], location[1] + anchorView.getHeight());

        return editText.getText().toString();

    }

}



