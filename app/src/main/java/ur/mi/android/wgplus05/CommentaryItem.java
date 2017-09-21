package ur.mi.android.wgplus05;

import android.content.Context;

import ur.mi.android.wgplus05.CalendarDB;

/**
 * Created by Gucci on 14.09.2017.
 */

public class CommentaryItem {

    private String commentary;
    private String user;
    private CalendarDB CDB;

    public CommentaryItem(Context context, String commentary){
        CDB = new CalendarDB(context);
        CDB.open();
        this.commentary = commentary;
        CDB = new CalendarDB(context);
        this.user = CDB.getUserName(); // "Hans";
    }

    public String getCommentary(){
        return commentary;
    }

    public String getUser(){
        return user;
    }

}
