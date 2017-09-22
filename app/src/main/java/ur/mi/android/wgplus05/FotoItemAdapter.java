package ur.mi.android.wgplus05;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class FotoItemAdapter extends ArrayAdapter<FotoItem> {

    private Context context;
    private CalendarDB SEDB;
    private ArrayList<FotoItem> posts;
    private ArrayList<CommentaryItem> comments;
    private CommentaryAdapter commentaryAdapter;


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

        if (fotoItem == null) {
            Log.d("check", "getView: ist null");
        }

        if (fotoItem != null) {

            Log.d("check", "getView: ist nicht null");
            TextView user = (TextView) v.findViewById(R.id.name_foto_user);
            user.setText(fotoItem.getUser());
            ImageView foto = (ImageView) v.findViewById(R.id.foto_view);
            TextView user_commentary = (TextView) v.findViewById(R.id.foto_user_commentary);
            final ImageButton thumbUp = (ImageButton) v.findViewById(R.id.foto_thumb_up);
         //   final ImageButton commentaryButton = (ImageButton) v.findViewById(R.id.foto_commentary_button);
            final TextView thumbCount = (TextView) v.findViewById(R.id.foto_thumbcount);
            final EditText commentary = (EditText) v.findViewById(R.id.commentary_box);
            final ImageView shareButton = (ImageView) v.findViewById(R.id.foto_share_button);
            final LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.layout_invisbile);
            final Button sendButton = (Button) v.findViewById(R.id.button_edit_add_comment);
            final ListView commentBox = (ListView) v.findViewById(R.id.listview_foto_commentary);
            final TextView avatar  = (TextView) v.findViewById(R.id.foto_avatar);
            if (fotoItem.getUser()!= null) {
                avatar.setText(Character.toString(fotoItem.getUser().charAt(0)));
            }
            comments = new ArrayList<>();
            commentaryAdapter = new CommentaryAdapter(context, comments);
            commentBox.setAdapter(commentaryAdapter);
            Bitmap myBitmap = BitmapFactory.decodeFile(fotoItem.getImagePath());


            foto.setImageBitmap(myBitmap);

            thumbCount.setText(Integer.toString(fotoItem.getThumbcount()));


            user_commentary.setText(fotoItem.getCommentary());
            Log.d("check", "Aufwand: " + fotoItem.getCommentary());

            thumbUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fotoItem.addthumbUp();
                    thumbCount.setText(Integer.toString(fotoItem.getThumbcount()));
                    thumbUp.setAlpha(0.2f);
                    thumbUp.setClickable(false);

                }
            });

            /*commentaryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout.setVisibility(View.VISIBLE);
                    sendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CommentaryItem commentaryItem = new CommentaryItem(getContext(),commentary.getText().toString());
                            comments.add(commentaryItem);
                            commentaryAdapter.notifyDataSetChanged();
                            linearLayout.setVisibility(View.INVISIBLE);
                        }
                    });
                }
        }); */

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(fotoItem.getImagePath());
                    Bitmap icon = myBitmap;
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/jpeg");

                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "title");
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);


                    OutputStream outstream;
                    try {
                        outstream = context.getContentResolver().openOutputStream(uri);
                        icon.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                        outstream.close();
                    } catch (Exception e) {
                        System.err.println(e.toString());
                    }

                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    context.startActivity(Intent.createChooser(share, "Share Image"));
                }
            });

        } return v;
    }

}


