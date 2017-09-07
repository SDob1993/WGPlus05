package ur.mi.android.wgplus05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class EinkaufslisteAdapter {

/*
    private ArrayList<EinkaufsProdukt> taskList;
    private Context context;

    public EinkaufslisteAdapter(Context context, ArrayList<EinkaufsProdukt> listItems) {
        super(context, android.R.layout.simple_list_item_1, listItems);
        this.context = context;
        this.taskList = listItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(android.R.layout.simple_list_item_1, null);

        }

        EinkaufsProdukt task = taskList.get(position);

        if (task != null) {
            TextView taskName = (TextView) v.findViewById(R.id.task_name);
            TextView taskDate = (TextView) v.findViewById(R.id.task_date);

            taskName.setText(task.getName());
            taskDate.setText(task.getFormattedDate());
        }

        return v;
    }
*/
}
