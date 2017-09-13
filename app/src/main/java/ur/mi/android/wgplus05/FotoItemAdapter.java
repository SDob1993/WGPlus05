package ur.mi.android.wgplus05;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FotoItemAdapter extends ArrayAdapter<FotoItem>{

    private ArrayList<PutzplanItem> taskList;
    private Context context;
    private CalendarDB SEDB;
    private ArrayList<FotoItem> posts;


    public FotoItemAdapter(Context context, ArrayList<FotoItem> listItems) {
        super(context, R.layout.listelement_foto_item, listItems);
        this.context = context;
        this.posts = listItems;
        SEDB = new CalendarDB(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(R.layout.listelement_foto_item, null);
        }

        final FotoItem fotoItem = getItem(position);

        if(fotoItem == null){
            Log.d("check", "getView: ist null");
        }

        if (fotoItem != null) {

            Log.d("check", "getView: ist nicht null");
            TextView user = (TextView) v.findViewById(R.id.name_foto_user);
            ImageView avatar = (ImageView) v.findViewById(R.id.avatar_foto_user);
            ImageView foto = (ImageView) v.findViewById(R.id.foto_view);
            TextView user_commentary = (TextView) v.findViewById(R.id.foto_user_commentary);
            final ImageButton thumbUp = (ImageButton) v.findViewById(R.id.foto_thumb_up);
            final ImageButton thumbDown = (ImageButton) v.findViewById(R.id.foto_thumb_down);
            final TextView thumbCount = (TextView) v.findViewById(R.id.foto_thumbcount);


            /* user.setText(SEDB.getUserName());
            Log.d("check", "Titel: "+SEDB.getUserName()); */

            //    avatar.setText(p.getDate());
            //    Log.d("check", "Date: "+date.getText());
            user.setText(fotoItem.getUser());

            foto.setImageBitmap(fotoItem.getImage());

            thumbCount.setText(Integer.toString(fotoItem.getThumbcount()));


            user_commentary.setText(fotoItem.getCommentary());
            Log.d("check", "Aufwand: "+fotoItem.getCommentary());

            thumbUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fotoItem.addthumbUp();
                    thumbCount.setText(Integer.toString(fotoItem.getThumbcount()));
                    thumbUp.setAlpha(0.2f);
                    thumbUp.setClickable(false);
                    thumbDown.setAlpha(0.2f);
                    thumbDown.setClickable(false);
                }
            });

            thumbDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fotoItem.addthumbDown();
                    thumbCount.setText(Integer.toString(fotoItem.getThumbcount()));
                    thumbDown.setAlpha(0.2f);
                    thumbDown.setClickable(false);
                    thumbUp.setAlpha(0.2f);
                    thumbUp.setClickable(false);
                }
            });

        }

        return v;
    }

}
