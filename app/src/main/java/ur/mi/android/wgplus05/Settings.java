package ur.mi.android.wgplus05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private FrameLayout mainLayout;
    private View textView;
    private ListView listView;
    private View addButton;


    public Settings() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("#Crew");

        mainLayout = (FrameLayout) findViewById(R.id.activity_settings);

        initListeners();
    }

    private void initListeners() {
        textView = findViewById(R.id.TextViewWGName);
        addButton = findViewById(R.id.buttonaddusers);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showPopupAdd(v);}

    });}

    public void showPopupAdd(View anchorView) {

        // get a reference to the already created main layout


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_user_popup_settings, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);

        final EditText editText1 = (EditText) popupView.findViewById(R.id.edit_settings);

        final Button button = (Button) popupView.findViewById(R.id.settings_popup_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText1.getText().toString().length() != 0) {

                    //EKDB.insertEinkaufItem(item);
                    //item_adapter.notifyDataSetChanged();
                    popupWindow.dismiss();
                }
                else Toast.makeText(getApplicationContext(),"Bitte gültigen Wert eingeben",Toast.LENGTH_LONG).show();
            }
        });

    }


}
