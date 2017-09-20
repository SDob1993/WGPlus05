package ur.mi.android.wgplus05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    private FrameLayout mainLayout;
    private TextView textView;
    private TextView textView2;
    private ListView listView;
    private View addButton;
    private boolean nameSetted = false;
    private boolean usernameSetted = false;
    private CalendarDB SEDB;
    private String Name;
    private String UserName;
    private ArrayAdapter<String> itemadapter;
    private ArrayList<String> arraylist;


    public Settings() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("#Crew");

        initDatabase();

        mainLayout = (FrameLayout) findViewById(R.id.activity_settings);

        initListeners();
        setupListView();

    }

    private void setupListView() {
        arraylist = SEDB.getAllMitbewohner();
        ListView itemListView = (ListView) findViewById(R.id.list_mitbewohner);
        itemadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);
        //set adapter
        itemListView.setAdapter(itemadapter);
        //setOnItemLongClickListener
        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                //call to remove
                itemLongClicked(position);
                return false;
            }
        });
    }

    private void itemLongClicked(int position) {
        //remove clicked item
        SEDB.removeMitbewohner(itemadapter.getItem(position));
        arraylist.remove(position);
        refreshArrayList();
    }

    private void initListeners() {
        textView = (TextView) findViewById(R.id.TextViewWGName);
        textView2 = (TextView) findViewById(R.id.TextViewSettings2);
        addButton = findViewById(R.id.buttonaddusers);
        textView.setText(SEDB.getWGName());
        textView2.setText(SEDB.getUserName());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showPopupAdd(v);
            }

        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usernameSetted)
                    showPopupName(v);
                    usernameSetted = true;
            }

        });
    }

    public void showPopupAdd(View anchorView) {

        // get a reference to the already created main layout


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_add_user, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);




        popupWindow.showAtLocation(mainLayout,Gravity.CENTER,0,0);

    final EditText editText1 = (EditText) popupView.findViewById(R.id.add_user);

    final Button button = (Button) popupView.findViewById(R.id.add_popup_button);

        button.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        if (editText1.getText().toString().length() != 0) {
            Name = SEDB.getWGName();
            SEDB.insertNewUser(editText1.getText().toString(),Name);
            System.out.println(SEDB.getWGName());
            refreshArrayList();
            popupWindow.dismiss();
        } else
            Toast.makeText(getApplicationContext(), "Bitte gültigen Wert eingeben", Toast.LENGTH_LONG).show();
    }
    });
}



    //Pup-Up to set UserName
    public void showPopupName(View anchorView) {

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

                    SEDB.insertWgUserName(editText1.getText().toString());
                    UserName = SEDB.getUserName();
                    textView2.setText(UserName);
                    popupWindow.dismiss();
                }
                else Toast.makeText(getApplicationContext(),"Bitte gültigen Wert eingeben",Toast.LENGTH_LONG).show();
            }
        });

    }
    private void refreshArrayList() {
        ArrayList tempList = SEDB.getAllMitbewohner();
        arraylist.clear();
        arraylist.addAll(tempList);
        itemadapter.notifyDataSetChanged();
    }

    private void initDatabase() {
        SEDB = new CalendarDB(this);
        SEDB.open();
    }


}
