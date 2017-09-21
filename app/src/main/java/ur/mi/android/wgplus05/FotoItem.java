package ur.mi.android.wgplus05;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;



public class FotoItem extends PictureActivity{

    private String commentary;
    private Bitmap image;
    private String name;
    private int thumbcount;


    public FotoItem(String commentary, Bitmap image, String name){
        this.commentary = commentary;
        this.image = image;
        thumbcount = 0;
        this.name = name;
    }


    public String getCommentary(){
        return commentary;
    }

    public Bitmap getImage(){
        return image;
    }

    public void addthumbUp(){
        thumbcount++;
    }


    public int getThumbcount(){
        return thumbcount;
    }

    public String getUser(){
     return name;

    }

}
