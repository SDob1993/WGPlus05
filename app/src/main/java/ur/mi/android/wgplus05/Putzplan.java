package ur.mi.android.wgplus05;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;



public class Putzplan extends ActionBarActivity {

    private ImageButton addUser;

    private ArrayList<PutzplanItem> ArrayListUser;

    private Spinner spinnerFrequenz;

    private String dateString;

    private PutzplanItemAdapter customAdapter;

    private ListView listView;
    FrameLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putzplan_neu);
        setTitle("#EinerMussesMachen");
        mainLayout = (FrameLayout) findViewById(R.id.activty_putzplan);

        initListViews();
        initButtons();
        initOnClickListener();
        setUpListViews();

    }


    private void setUpListViews() {
        ArrayListUser = new ArrayList<>();
        customAdapter = new PutzplanItemAdapter(this, ArrayListUser);
        listView.setAdapter(customAdapter);
        ArrayListUser.add(new PutzplanItem("titel","frequenz","date","tag","name",1));
        customAdapter.notifyDataSetChanged();

    }

    private void initButtons() {
        addUser = (ImageButton) findViewById(R.id.button_add_user);
    }

    private void initOnClickListener() {
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupAdd(v);
            }
        });
    }


    private void initListViews() {
        listView = (ListView) findViewById(R.id.list_putzplan);

    }


    public void showPopupAdd(View anchorView) {

        // get a reference to the already created main layout


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.fragment_putzplan, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);


        spinnerFrequenz = (Spinner) popupView.findViewById(R.id.spinner_frequenz);
        ArrayAdapter<String> adapterFrequenz = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.frequenz));
        spinnerFrequenz.setAdapter(adapterFrequenz);

        final EditText editDay = (EditText) popupView.findViewById(R.id.edit_day);

        final NumberPicker numberPicker = (NumberPicker) popupView.findViewById(R.id.number_picker_aufwand);
        numberPicker.setMaxValue(3);

        final EditText titel = (EditText) popupView.findViewById(R.id.edit_titel);

        final Button buttonSet = (Button) popupView.findViewById(R.id.button_set_date);




        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupDate();

            }
        });


        final Button buttonFertig = (Button) popupView.findViewById(R.id.buttom_fertig);

        buttonFertig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titel.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Musst scho an Titel angeben", Toast.LENGTH_LONG).show();
                }
                if (dateString.equals("")) {
                    Toast.makeText(getApplicationContext(), "Musst scho a Datum angeben", Toast.LENGTH_LONG).show();
                }
                if (!(titel.getText() == null || editDay.getText() == null)) {
                    PutzplanItem putzplanItem = new PutzplanItem(titel.getText().toString(), spinnerFrequenz.getSelectedItem().toString(),
                            dateString, editDay.getText().toString(), "Lukas", numberPicker.getValue());
                    ArrayListUser.add(putzplanItem);
                    customAdapter.notifyDataSetChanged();
                    popupWindow.dismiss();
                    Log.d("values",putzplanItem.toText() );
                }
            }
        });
    }


    private void removeTaskAtPositionKueche(int position) {
        if (ArrayListUser.get(position) != null) {
            ArrayListUser.remove(position);
            customAdapter.notifyDataSetChanged();
        }
    }




    public void showPopupDate() {

        // get a reference to the already created main layout


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.layout_date_picker, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);


        final Button button = (Button) popupView.findViewById(R.id.button_set_date_dialog);
        final DatePicker datePicker = (DatePicker) popupView.findViewById(R.id.date_picker);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateString = ""+datePicker.getDayOfMonth()+"."+datePicker.getMonth()+"."+datePicker.getYear();
                popupWindow.dismiss();
                Log.d("datum", "onClick: "+dateString);


            }
        });

    }

}











