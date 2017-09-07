package ur.mi.android.wgplus05;

/**
 * Created by simon on 07.09.2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class CalendarAdapter extends ArrayAdapter<CalendarItem> {
    private ArrayList<CalendarItem> taskList;
    private Context context;

    public CalendarAdapter(Context context, ArrayList<CalendarItem> listItems) {
        super(context, R.layout.listitem_calendar, listItems);
        this.context = context;
        this.taskList = listItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listitem_calendar, null);

        }

        CalendarItem task = taskList.get(position);

        if (task != null) {
            TextView taskName = (TextView) v.findViewById(R.id.task_name);
            TextView taskDate = (TextView) v.findViewById(R.id.task_date);

            taskName.setText(task.getName());
            taskDate.setText(task.getFormattedDate());
        }

        return v;
    }

}


