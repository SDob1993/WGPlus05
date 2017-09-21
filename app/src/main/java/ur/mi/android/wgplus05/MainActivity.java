package ur.mi.android.wgplus05;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private ImageButton finanzenButton;
    private ImageButton einkaufslisteButton;
    private ImageButton kalenderButton;
    private ImageButton einstellungenButton;
    private ImageButton blackboardButton;
    private ImageButton putzplanButton;
    private LinearLayout mainLayout;
    private static String name;
    private static String wgname;
    private CalendarDB DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtons();
        initOnClickListener();
        initLayout();
        initDB();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                checkFirstRun();
            }
        }, 2000);

    }

    private void initDB() {
        DB = new CalendarDB(this);
        DB.open();

    }


    public String getName(){
        if (name != null) {
            return name;
        }
        else return "Name is null";
    }

    private void checkFirstRun() {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun){
            showLoginPopUp(viewGroup);

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        }
    }

    private void initLayout(){
        mainLayout = (LinearLayout) findViewById(R.id.activity_main);
    }


    private void initButtons(){
        finanzenButton = (ImageButton) findViewById(R.id.finanzen_button);
        einkaufslisteButton = (ImageButton) findViewById(R.id.einkaufsliste_button);
        kalenderButton = (ImageButton) findViewById(R.id.kalender_button);
        einstellungenButton = (ImageButton) findViewById(R.id.einstellungen_button);
        blackboardButton = (ImageButton) findViewById(R.id.blackboard_button);
        putzplanButton = (ImageButton) findViewById(R.id.putzplan_button);

    }

    private void initOnClickListener(){
        finanzenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Finanzen.class);
                startActivity(intent);
            }
        });

        einkaufslisteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Einkaufsliste2.class);
                startActivity(intent);
            }
        });

        kalenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Kalender.class);
                startActivity(intent);
            }
        });

        einstellungenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Settings.class);
                startActivity(intent);
            }
        });

        blackboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PictureActivity.class);
                startActivity(intent);
            }
        });
        putzplanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,Putzplan.class);
                startActivity(intent);
            }
        });
    }

    public void showLoginPopUp(View anchorView) {

        // get a reference to the already created main layout


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_username_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);


        final EditText editName = (EditText) popupView.findViewById(R.id.edit_name);
        final EditText editWgname = (EditText) popupView.findViewById(R.id.edit_wgname);

        final Button button = (Button) popupView.findViewById(R.id.edit_ok_button_name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().toString().length() != 0 && editWgname.getText().toString().length() != 0) {
                    name = editName.getText().toString();
                    wgname = editWgname.getText().toString();
                    DB.insertWgUserName(name);
                    DB.insertWgName(wgname);
                    DB.insertNewUser(name,wgname);
                    popupWindow.dismiss();
                    Log.d("name", name);
                } else
                    Toast.makeText(getApplicationContext(), "Bitte geb deinen Namen und den Namen deiner WG ein", Toast.LENGTH_LONG).show();
            }
        });


    }

}
