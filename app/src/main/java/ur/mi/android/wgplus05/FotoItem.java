package ur.mi.android.wgplus05;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;



public class FotoItem extends PictureActivity{

    private String commentary;
    private Bitmap image;
    private String user;
    private Image avatar;
    private int thumbcount;
    private CalendarDB CDB;

    public FotoItem(String commentary, Bitmap image){
        this.commentary = commentary;
        this.image = image;
        //CDB = new CalendarDB(this);
        //CDB.open();
        user = "Lukas";
        //avatar = SEDB.getAvatar();
        thumbcount = 0;
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
     return "Lukas";//CDB.getUserName();

    }

}
