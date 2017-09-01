package ur.mi.android.wgplus05;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Putzplan extends ActionBarActivity {

    private CalendarView calenderView;
    private TextView textView;
    private HashMap userList;
    private Button addUser;
    private Button changeUser;
    private EditText user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putzplan);

        calenderView = (CalendarView) findViewById(R.id.calendarView);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("gay");
        addUser = (Button) findViewById(R.id.button_add);
        changeUser = (Button) findViewById(R.id.button_change);
        user = (EditText) findViewById(R.id.editText);

        initOnClickListener();
    }

    private void initOnClickListener(){
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserToPutzplan();
            }
        });
        changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private int getWeeklyNumber(){
        return calenderView.getWeekDayTextAppearance();
    }

    private void assignUserForWeek(){
        userList.put(getWeeklyNumber(), user);
    }

    private void addUserToPutzplan() {
        if (user.getText().toString().length() != 0) {
            textView.setText(user.toString());

        } else Toast.makeText(this, "Musst schon an Namen eingeben", Toast.LENGTH_LONG);
    }

}
