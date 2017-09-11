package ur.mi.android.wgplus05;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class CustomListViewAdaper extends ArrayAdapter<PutzplanItem> {

    public CustomListViewAdaper(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        Log.d("check", "erster");
    }

    public CustomListViewAdaper(Context context, int resource, List<PutzplanItem> items) {
        super(context, resource, items);
        Log.d("check", "zweiter");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.listview_edit, null);
        }

        PutzplanItem p = getItem(position);

        if(p == null){
            Log.d("check", "getView: ist null");
        }

        if (p != null) {

            Log.d("check", "getView: ist nicht null");
            TextView titel = (TextView) v.findViewById(R.id.titel_list_view);
            TextView date = (TextView) v.findViewById(R.id.date_list_view);
            TextView frequenz = (TextView) v.findViewById(R.id.frequenz_list_view);
            TextView aufwand = (TextView) v.findViewById(R.id.aufwand_list_view);
            ImageView pic = (ImageView) v.findViewById(R.id.img_list_view);

                titel.setText(p.getTitel());
                Log.d("check", "Titel: "+titel.getText());

                date.setText(p.getDate());
                Log.d("check", "Date: "+date.getText());

                frequenz.setText(p.getName());
                Log.d("check", "Name: "+frequenz.getText());

                aufwand.setText(p.getAufwand());
                Log.d("check", "Aufwand: "+aufwand.getText());

        }

        return v;
    }

}

