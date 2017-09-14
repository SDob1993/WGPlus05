package ur.mi.android.wgplus05;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gucci on 14.09.2017.
 */

public class CommentaryAdapter extends ArrayAdapter<CommentaryItem>{

        private ArrayList<CommentaryItem> commentary;
        private Context context;
        private Typeface myTypeFace;

        public CommentaryAdapter(Context context, ArrayList<CommentaryItem> commentaryItems) {
            super(context, R.layout.list_element_commentary, commentaryItems);
            this.context = context;
            this.commentary = commentaryItems;
            myTypeFace = Typeface.createFromAsset(context.getAssets(), "IndieFlower.ttf");
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_element_commentary, null);

            }

            CommentaryItem item = getItem(position);

            if (item != null) {
                TextView comment = (TextView) v.findViewById(R.id.commentary_edit);
               // itemName.setTypeface(myTypeFace);
                comment.setText(item.getCommentary());

                TextView user = (TextView) v.findViewById(R.id.commentary_user);
                user.setText(item.getUser());

            }

            return v;
        }

    }

