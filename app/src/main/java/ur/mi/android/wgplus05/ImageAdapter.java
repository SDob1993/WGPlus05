package ur.mi.android.wgplus05;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Bitmap> images;
    private Point displaySize;

    public ImageAdapter(Context context, Point displaySize) {
        this.context = context;
        this.displaySize = displaySize;
        images = new ArrayList<Bitmap>();
    }

    public void addImage(Bitmap image) {
        images.add(image);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(displaySize.x / 2, displaySize.x / 2));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap img = images.get(position);
        imageView.setImageBitmap(img);
        return imageView;
    }

}
