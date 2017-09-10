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

        if (p != null) {
            TextView titel = (TextView) v.findViewById(R.id.titel_list_view);
            TextView date = (TextView) v.findViewById(R.id.date_list_view);
            TextView name = (TextView) v.findViewById(R.id.name_list_view);
            TextView aufwand = (TextView) v.findViewById(R.id.aufwand_list_view);
            ImageView pic = (ImageView) v.findViewById(R.id.img_list_view);

            if (titel != null) {
                titel.setText(p.getTitel());
            }

            if (date != null) {
                date.setText(p.getDate());
            }

            if (name != null) {
                name.setText(p.getName());
            }
            if (aufwand != null) {
                aufwand.setText(p.getAufwand());
            }

           /* if (pic != null) {
                pic.setImageDrawable();
            }

           */
        }

        return v;
    }

}

