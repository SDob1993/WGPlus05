package ur.mi.android.wgplus05;

/**
 * Created by simon on 07.09.2017.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalendarDB {
    //DB allgemein
    private static final String DATABASE_NAME = "calendar.db";
    private static final int DATABASE_VERSION = 1;
    //DB_Table für Calendar
    private static final String DATABASE_TABLE = "todolistitems";
    //DB_Table für Einkäufe
    private static final String DATABASE_TABLE1 = "einkauefelistitems";
    //Keys für Calendar
    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_DATE = "date";
    //Keys für EInkaufsitem
    public static final String KEY_IDEK = "_id1";
    public static final String KEY_NAME = "name";

    public static final int COLUMN_TASK_INDEX = 1;
    public static final int COLUMN_DATE_INDEX = 2;
    public static final int COLUMN_NAME_INDEX = 1;

    private ToDoDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public CalendarDB(Context context) {
        dbHelper = new ToDoDBOpenHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }
    //Insert-Methode für Calendar
    public long insertToDoItem(CalendarItem item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_TASK, item.getName());
        itemValues.put(KEY_DATE, item.getFormattedDate());
        return db.insert(DATABASE_TABLE, null, itemValues);
    }
    //Insert-Methode für Einkauf
    public long insertEinkaufItem(EinkaufsItem item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_NAME, item.getName());
        return db.insert(DATABASE_TABLE1, null, itemValues);
    }
    //Remove-Methode für Calendar
    public void removeToDoItem(CalendarItem item) {

        String toDelete = KEY_TASK + "=?";
        String[] deleteArguments = new String[]{item.getName()};
        db.delete(DATABASE_TABLE, toDelete, deleteArguments);

    }
    public void removeEinkaufItem(EinkaufsItem item) {

        String toDelete = KEY_NAME + "=?";
        String[] deleteArguments = new String[]{item.getName()};
        db.delete(DATABASE_TABLE1, toDelete, deleteArguments);

    }
    //get All CalendarItems
    public ArrayList<CalendarItem> getAllToDoItems() {
        ArrayList<CalendarItem> items = new ArrayList<CalendarItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
                KEY_TASK, KEY_DATE}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String task = cursor.getString(COLUMN_TASK_INDEX);
                String date = cursor.getString(COLUMN_DATE_INDEX);

                Date formattedDate = null;
                try {
                    formattedDate = new SimpleDateFormat("dd.MM.yyyy",
                            Locale.GERMAN).parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal = Calendar.getInstance(Locale.GERMAN);
                cal.setTime(formattedDate);

                items.add(new CalendarItem(task, cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)));

            } while (cursor.moveToNext());
        }
        return items;
    }

    //get All EinkaufItems
    public ArrayList<EinkaufsItem> getAllEinkaufItems() {
        ArrayList<EinkaufsItem> items = new ArrayList<EinkaufsItem>();
       /* Cursor cursor = db.query(DATABASE_TABLE1, new String[] {KEY_ID,
                KEY_TASK}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(COLUMN_NAME_INDEX);
                items.add(new EinkaufsItem(name));

            } while (cursor.moveToNext());
        }*/
        //items.add(new EinkaufsItem("Hallo"));
        return items;
    }

    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TASK
                + " text not null, " + KEY_DATE + " text," + KEY_NAME + "text);";


         private static final String DATABASE_CREATE1 = "create table "
                + DATABASE_TABLE1 + " (" + KEY_IDEK
                + " integer primary key autoincrement, " + KEY_NAME
                + " text not null);";

        public ToDoDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
