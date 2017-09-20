package ur.mi.android.wgplus05;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class EinkaufsHistorieAdapter extends ArrayAdapter<EinkaufsHistorieItem> {
        private Context context;
        private ArrayList<EinkaufsHistorieItem> einkaufList;


        public EinkaufsHistorieAdapter(Context context, ArrayList<EinkaufsHistorieItem> einkaufsItems) {
            super(context, R.layout.listview_einkauf_history);
            this.context = context;
            this.einkaufList = einkaufsItems;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.ekitem_layout, null);

            }

            EinkaufsHistorieItem item = einkaufList.get(position);

            if (item != null) {
                TextView itemName = (TextView) v.findViewById(R.id.item_name);
                itemName.setText(einkaufList.toString());

            }

            return v;
        }

    }

