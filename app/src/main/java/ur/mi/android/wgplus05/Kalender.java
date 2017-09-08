package ur.mi.android.wgplus05;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class Kalender extends AppCompatActivity {

    CalendarView calendar;
    private ArrayList<CalendarItem> tasks;
    private CalendarAdapter tasks_adapter;
    private CalendarDB toDoDB;
    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender);
        initTaskList();
        initDatabase();
        initListView();
        refreshArrayList();
        mainLayout = (LinearLayout) findViewById(R.id.activity_kalender_id);

        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayofmonth){
                popUpAdd(view,year,month,dayofmonth);
            }
        });
    }



    private void refreshArrayList() {
        ArrayList tempList = toDoDB.getAllToDoItems();
        tasks.clear();
        tasks.addAll(tempList);
        tasks_adapter.notifyDataSetChanged();
    }

    private void popUpAdd(View anchorView, int year, int month, int dayofmonth) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_user_popup_task, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);

        final EditText editText1 = (EditText) popupView.findViewById(R.id.edit_task);
        final NumberPicker daypicker = (NumberPicker) popupView.findViewById(R.id.edit_day);
        daypicker.setMaxValue(31);
        daypicker.setMinValue(1);
        daypicker.setValue(dayofmonth);
        final NumberPicker monthpicker = (NumberPicker) popupView.findViewById(R.id.edit_month);
        monthpicker.setMaxValue(12);
        monthpicker.setMinValue(1);
        monthpicker.setValue(month);
        final NumberPicker yearpicker = (NumberPicker) popupView.findViewById(R.id.edit_year);
        yearpicker.setMaxValue(2111);
        yearpicker.setMinValue(2017);
        yearpicker.setValue(year);

        final Button button = (Button) popupView.findViewById(R.id.task_popup_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText1.getText().toString().length() != 0) {
                    String taskname = editText1.getText().toString();
                    CalendarItem newTask = new CalendarItem(taskname,daypicker.getValue() ,monthpicker.getValue()
                            ,yearpicker.getValue());

                    toDoDB.insertToDoItem(newTask);
                    tasks.add(newTask);
                    tasks_adapter.notifyDataSetChanged();
                    refreshArrayList();
                    popupWindow.dismiss();
                }
                else Toast.makeText(getApplicationContext(),"Bitte gültigen Wert eingeben",Toast.LENGTH_LONG).show();
            }
        });

    }


    private void initListView() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                removeTaskAtPosition(position);
                return true;
            }
        });
    }

    private void initDatabase() {
        toDoDB = new CalendarDB(this);
        toDoDB.open();
    }

    private void initTaskList() {
        tasks = new ArrayList<CalendarItem>();
        initListAdapter();
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        tasks_adapter = new CalendarAdapter(this, tasks);
        list.setAdapter(tasks_adapter);
    }

    private void removeTaskAtPosition(int position) {
        if (tasks.get(position) != null) {
            toDoDB.removeToDoItem(tasks.get(position));
            refreshArrayList();
        }
    }
    private void sortList() {
        Collections.sort(tasks);
        tasks_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toDoDB.close();
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


}
