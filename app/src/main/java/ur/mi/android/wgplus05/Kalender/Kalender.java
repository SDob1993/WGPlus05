package ur.mi.android.wgplus05.Kalender;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import ur.mi.android.wgplus05.CalendarDB;
import ur.mi.android.wgplus05.Einkaufsliste.EinkaufsItem;
import ur.mi.android.wgplus05.R;

public class Kalender extends AppCompatActivity {

    CalendarView calendar;
    private ArrayList<CalendarItem> tasks;
    private CalendarAdapter tasks_adapter;
    private CalendarDB toDoDB;
    private FrameLayout mainLayout;
    private AlarmManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender);
        initTaskList();
        initDatabase();
        initListView();
        refreshArrayList();
        mainLayout = (FrameLayout) findViewById(R.id.activity_kalender_id);
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
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
        final Switch nswitch = (Switch) popupView.findViewById(R.id.notification_switch);

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
                    if (nswitch.isChecked()) { startAlarm(true,false,yearpicker.getValue(),monthpicker.getValue(),daypicker.getValue(), tasks.size(), taskname); }
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
            Intent myIntent = new Intent(this, AlarmNotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, position, myIntent, 0);
            manager.cancel(pendingIntent);
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


    //NotificationStuff

    private void startAlarm(boolean isNotification, boolean isRepeat,int year, int month, int day, int position, String name) {
        Intent myIntent;
        PendingIntent pendingIntent;

        Calendar myAlarmDate = Calendar.getInstance();
        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
        myAlarmDate.set(year, month, day-1, 10, 0, 0);
        Log.d("alarm", Integer.toString(year)+Integer.toString(month)+Integer.toString(day-1));

        if (isNotification) {
            myIntent = new Intent(this, AlarmNotificationReceiver.class);
            myIntent.putExtra("name", name);
            myIntent.putExtra("position", position);
            pendingIntent = PendingIntent.getBroadcast(this, position, myIntent, 0);


            manager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntent);
            Log.d("alarm", Long.toString(myAlarmDate.getTimeInMillis()));
        }
    }

}



