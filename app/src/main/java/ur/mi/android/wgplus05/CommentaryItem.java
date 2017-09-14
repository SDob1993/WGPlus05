package ur.mi.android.wgplus05;

import android.content.Context;

/**
 * Created by Gucci on 14.09.2017.
 */

public class CommentaryItem {

    private String commentary;
    private String user;
    private CalendarDB CDB;

    public CommentaryItem(Context context, String commentary){
        this.commentary = commentary;
        CDB = new CalendarDB(context);
        this.user =/* CDB.getUserName(); */ "Hans ohne Schwanz";
    }

    public String getCommentary(){
        return commentary;
    }

    public String getUser(){
        if (user != null){
            return user;
        }
        else return "Hans";
    }

}
