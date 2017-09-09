package ur.mi.android.wgplus05;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

    private ArrayList ArrayListUser;

    private Spinner spinnerFrequenz;
    private Spinner spinnerTag;
    private Spinner spinnerAufwand;
    private TextView datePutzplan;

    private ArrayAdapter ArrayAdapterListUser;

    private ListView ListUser;
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


    private void setUpListViews(){
        ArrayListUser  = new ArrayList<PutzplanItem>();
        ArrayAdapterListUser = new ArrayAdapter<PutzplanItem>(this, android.R.layout.simple_list_item_1,ArrayListUser);
        ListUser.setAdapter(ArrayAdapterListUser);

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


    private void initListViews(){
        ListUser = (ListView) findViewById(R.id.list_putzplan);

    }


    public void showPopupAdd(View anchorView) {

        // get a reference to the already created main layout


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.fragment_putzplan, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);


        spinnerFrequenz = (Spinner) popupView.findViewById(R.id.spinner_frequenz);
        ArrayAdapter<String> adapterFrequenz = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.frequenz));
        spinnerFrequenz.setAdapter(adapterFrequenz);

       /* spinnerTag = (Spinner) popupView.findViewById(R.id.spinner_tag);
        ArrayAdapter<String> adapterTag = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.tag));
        spinnerTag.setAdapter(adapterTag);


        spinnerAufwand = (Spinner) popupView.findViewById(R.id.spinner_aufwand);
        ArrayAdapter<String> adapterAufwand = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.aufwand));
        spinnerAufwand.setAdapter(adapterAufwand); */

        final EditText titel = (EditText) popupView.findViewById(R.id.edit_titel);

        datePutzplan = (TextView) popupView.findViewById(R.id.date_putzplan);
        datePutzplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });


        final Button buttonFertig = (Button) popupView.findViewById(R.id.buttom_fertig);

        /*buttonFertig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titel.toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Musst scho an Titel angeben",Toast.LENGTH_LONG).show();
                }
                if (datePutzplan.toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Musst scho a Datum angeben",Toast.LENGTH_LONG).show();
                }
                if (!(titel.toString().equals("") && datePutzplan.toString().equals(""))){
                    PutzplanItem putzplanItem = new PutzplanItem(titel.getText().toString(),spinnerFrequenz.getSelectedItem().toString(),
                            datePutzplan.getText().toString(),spinnerTag.getSelectedItem().toString(),"Lukas",Integer.getInteger(spinnerAufwand.getSelectedItem().toString()));
                    ArrayListUser.add(putzplanItem);
                    ArrayAdapterListUser.notifyDataSetChanged();
                    popupWindow.dismiss();
                }
            }
        }); */
    }


    private void removeTaskAtPositionKueche(int position) {
        if (ArrayListUser.get(position) != null) {
            ArrayListUser.remove(position);
            ArrayAdapterListUser.notifyDataSetChanged();
        }
    }

    public void changeDatePutzplanText(String dateString){
        datePutzplan.setText(dateString);
    }


    public void showDatePickerDialog() {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");

    }

    private Date getDateFromString(String dateString) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            // return current date as fallback
            return new Date();
        }
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        private static String dateString;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OKasdas", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            });
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            GregorianCalendar date = new GregorianCalendar(year, month, day);
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                    Locale.GERMANY);
            dateString = df.format(date.getTime());

        }

        public static String getDateString(){
            return dateString;
        }
    }

}



