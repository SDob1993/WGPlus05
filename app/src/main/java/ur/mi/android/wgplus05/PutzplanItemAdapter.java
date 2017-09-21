package ur.mi.android.wgplus05;



import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class PutzplanItemAdapter extends ArrayAdapter<PutzplanItem> {
    private ArrayList<PutzplanItem> taskList;
    private Context context;
    private CalendarDB SEDB;


    public PutzplanItemAdapter(Context context, ArrayList<PutzplanItem> listItems) {
        super(context, R.layout.list_view_layout, listItems);
        this.context = context;
        this.taskList = listItems;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_view_layout, null);

        }


        PutzplanItem putzplanItem = taskList.get(position);

        if (putzplanItem != null) {
            TextView titel = (TextView) v.findViewById(R.id.titel_list_view);
            TextView date = (TextView) v.findViewById(R.id.date_list_view);
            TextView frequenz = (TextView) v.findViewById(R.id.frequenz_list_view);
            TextView aufwand = (TextView) v.findViewById(R.id.aufwand_list_view);
            TextView avatar = (TextView) v.findViewById(R.id.putzplan_avatar);

            if (putzplanItem.getName()!= null) {
               avatar.setText(putzplanItem.getName());
            }
            if (titel != null) {
                titel.setText(putzplanItem.getTitel());
                Log.d("check", "Titel: " + titel.getText());
            }
            if (date != null) {
                date.setText(putzplanItem.getDate());
                Log.d("check", "Date: " + date.getText());
            }
            if (frequenz != null) {
                frequenz.setText(putzplanItem.getFrequenz());
                Log.d("check", "Name: " + frequenz.getText());
            }
            if (aufwand != null) {
                aufwand.setText(""+putzplanItem.getAufwand());
                Log.d("check", "Aufwand: " + aufwand.getText());
            }
        }

        return v;
    }
    @Override
    public int getCount() {
        return taskList.size();
    }



}
