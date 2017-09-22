package ur.mi.android.wgplus05;

import android.graphics.Bitmap;


public class FotoItem extends PictureActivity{

    private String commentary;
    private byte[] image;
    private String name;
    private int thumbcount;
    private String path;


    public FotoItem(String commentary, byte[] image, String name, int thumbcount, String path){
        this.commentary = commentary;
        this.image = image;
        this.thumbcount = thumbcount;
        this.name = name;
        this.path = path;
    }


    public String getCommentary(){
        return commentary;
    }

    public byte[] getImage(){
        return image;
    }

    public String getImagePath(){ return path; }

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
