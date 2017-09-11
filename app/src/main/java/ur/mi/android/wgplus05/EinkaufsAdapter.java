package ur.mi.android.wgplus05;

/**
 * Created by simon on 08.09.2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class EinkaufsAdapter extends ArrayAdapter<EinkaufsItem> {
    private ArrayList<EinkaufsItem> einkaufList;
    private Context context;
    private Typeface myTypeFace;

    public EinkaufsAdapter(Context context, ArrayList<EinkaufsItem> einkaufsItems) {
        super(context, R.layout.ekitem_layout, einkaufsItems);
        this.context = context;
        this.einkaufList = einkaufsItems;
        myTypeFace = Typeface.createFromAsset(context.getAssets(), "IndieFlower.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.ekitem_layout, null);

        }

        EinkaufsItem item = einkaufList.get(position);

        if (item != null) {
            TextView itemName = (TextView) v.findViewById(R.id.item_name);
            itemName.setTypeface(myTypeFace);
            itemName.setText(item.getName());

        }

        return v;
    }

}



