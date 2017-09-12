package ur.mi.android.wgplus05;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FotoItem> posts;
    private Point displaySize;
    private CalendarDB SEDB;

    public ImageAdapter(Context context, Point displaySize) {
        this.context = context;
        this.displaySize = displaySize;
        posts = new ArrayList<>();


    }

    public void addFotoItem(FotoItem fotoItem) {
        posts.add(fotoItem);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public FotoItem getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
            ImageButton thumbUp = (ImageButton) v.findViewById(R.id.foto_thumb_up);
            ImageButton thumbDown = (ImageButton) v.findViewById(R.id.foto_thumb_down);
            final TextView thumbCount = (TextView) v.findViewById(R.id.foto_thumbcount);


            user.setText(SEDB.getUserName());
            Log.d("check", "Titel: "+SEDB.getUserName());

        //    avatar.setText(p.getDate());
        //    Log.d("check", "Date: "+date.getText());

            foto.setImageBitmap(fotoItem.getImage());

            thumbCount.setText(fotoItem.getThumbcount());


            user_commentary.setText(fotoItem.getCommentary());
            Log.d("check", "Aufwand: "+fotoItem.getCommentary());

            thumbUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fotoItem.addthumbUp();
                    thumbCount.setText(fotoItem.getThumbcount());
                }
            });

            thumbDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fotoItem.addthumbDown();
                    thumbCount.setText(fotoItem.getThumbcount());
                }
            });

        }

        return v;
    }

}
