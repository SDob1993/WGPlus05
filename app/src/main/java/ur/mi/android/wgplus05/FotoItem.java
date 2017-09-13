package ur.mi.android.wgplus05;

import android.graphics.Bitmap;
import android.media.Image;



public class FotoItem extends PictureActivity{

    private String commentary;
    private Bitmap image;
    private String user;
    private Image avatar;
    private int thumbcount;
    private CalendarDB SEDB;

    public FotoItem(String commentary, Bitmap image){
        this.commentary = commentary;
        this.image = image;
        user = "Lukas"; // SEDB.getUser();
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

    public void addthumbDown(){
        thumbcount--;
    }

    public int getThumbcount(){
        return thumbcount;
    }

    public String getUser(){
     /*   if(SEDB.getUserName() != null){
            return SEDB.getUserName();
        } else return "Hans";
        */
     return "Hans";
    }

}
