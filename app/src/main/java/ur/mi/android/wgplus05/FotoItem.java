package ur.mi.android.wgplus05;

import android.graphics.Bitmap;


public class FotoItem extends PictureActivity{

    private String commentary;
    private byte[] image;
    private String name;
    private int thumbcount;


    public FotoItem(String commentary, byte[] image, String name, int thumbcount){
        this.commentary = commentary;
        this.image = image;
        this.thumbcount = thumbcount;
        this.name = name;
    }


    public String getCommentary(){
        return commentary;
    }

    public byte[] getImage(){
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
