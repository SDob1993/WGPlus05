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
    private static final String DATABASE_TABLE1 = "ekitems";
    //DB_Table für FInanzen
    private static final String DATABASE_TABLE2 = "finanzen";
    //DB_Table für WGName
    private static final String DATABASE_TABLE3 = "wgname";
    //DB-Table für User
    private static final String DATABASE_TABLE4 = "username";
    //DB-Table für Putzplan
    private static final String DATABASE_TABLE5 = "putzplan";
    //Keys für Calendar
    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_DATE = "date";
    //Keys für EInkaufsitem
    public static final String KEY_NAME = "name";
    //Keys für Finanzen
    public static final String KEY_GUTHABEN = "guthaben";
    public static final String KEY_GESAMT = "gesamt";
    //Keys für DBName
    public static final String KEY_NAMEWG = "name";
    //Keys für DBUserName
    public static final String KEY_NAMEUSER = "name";
    //Keys für Putzplan
    public static final String KEY_TITEL = "titel";
    public static final String KEY_FREQUENZ = "frequenz";
    public static final String KEY_DATUM = "datum";
    public static final String KEY_TAG = "tag";
    public static final String KEY_NAMEP = "name";
    public static final String KEY_AUFWAND = "aufwand";
    public static final String KEY_BILD = "bild";

    public static final int COLUMN_TASK_INDEX = 1;
    public static final int COLUMN_DATE_INDEX = 2;
    public static final int COLUMN_NAME_INDEX = 1;
    public static final int COLUMN_GUTHABEN_INDEX = 1;
    public static final int COLUMN_GESAMT_INDEX = 2;

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
    public long insertPItem(PutzplanItem item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_NAMEP, item.getName());
        itemValues.put(KEY_TITEL, item.getTitel());
        itemValues.put(KEY_DATUM, item.getDate());
        itemValues.put(KEY_AUFWAND, item.getAufwand());
        itemValues.put(KEY_FREQUENZ, item.getFrequenz());
        itemValues.put(KEY_TAG, item.getTag());
        return db.insert(DATABASE_TABLE5, null, itemValues);
    }
    //Insert-Methode für Einkauf
    public long insertEinkaufItem(EinkaufsItem item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_NAME, item.getName());
        return db.insert(DATABASE_TABLE1, null, itemValues);
    }
    //Insert-Methdoe für Finanzen
    public long insertFinanzenGes(FinanzenEntry item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_GESAMT, item.getGuthabenDouble());
        return db.insert(DATABASE_TABLE2, null, itemValues);

    }
    public void insertFinanzenSpez(FinanzenEntry item,String User) {
        double value = item.getGuthabenDouble();
        db.execSQL("INSERT INTO"+DATABASE_TABLE2+"("+KEY_GUTHABEN+") VALUES("+ value + ") WHERE"+KEY_ID+"=="+User);

    }

    //Insert-Methode Name der Wg
    public long insertWgName (String name){
        ContentValues nameValues = new ContentValues();
        nameValues.put(KEY_NAME,name);
        return db.update(DATABASE_TABLE3,nameValues,null,null);
    }
    //Insert-Methode Name der Wg
    public long insertWgUserName (String name){
        ContentValues nameValues = new ContentValues();
        nameValues.put(KEY_NAMEUSER,name);
        return db.update(DATABASE_TABLE4,nameValues,null,null);
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
        Cursor cursor = db.query(DATABASE_TABLE1, new String[] {KEY_ID,
                KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(COLUMN_NAME_INDEX);
                items.add(new EinkaufsItem(name));

            } while (cursor.moveToNext());
        }
        return items;
    }
    //get Putzplan
    public ArrayList<PutzplanItem> getAllPutzplanItems() {
        ArrayList<PutzplanItem> items = new ArrayList<PutzplanItem>();
        Cursor cursor = db.query(DATABASE_TABLE5, new String[] {KEY_ID,
                KEY_NAMEP, KEY_TITEL, KEY_DATUM, KEY_AUFWAND, KEY_FREQUENZ,KEY_TAG}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String titel = cursor.getString(2);
                String datum = cursor.getString(3);
                int aufwand = cursor.getInt(4);
                String frequenz = cursor.getString(5);
                String tag = cursor.getString(6);
                items.add(new PutzplanItem(name,titel,datum,tag,frequenz,aufwand));

            } while (cursor.moveToNext());
        }
        return items;
    }
    //get WGName
    public String getWGName() {
        String name ="";
        Cursor cursor = db.query(DATABASE_TABLE3, new String[] {KEY_ID,
                KEY_NAMEWG}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(1);

            } while (cursor.moveToNext());
        }
        return name;
    }
    //get WGName
    public String getUserName() {
        String name ="";
        Cursor cursor = db.query(DATABASE_TABLE4, new String[] {KEY_ID,
                KEY_NAMEUSER}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(1);

            } while (cursor.moveToNext());
        }
        return name;
    }

    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TASK
                + " text not null, " + KEY_DATE + " text);";


        private static final String DATABASE_CREATE1 = "create table "
                + DATABASE_TABLE1 + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_NAME
                + " text not null);";

        private static final String DATABASE_CREATE2 = "create table "
                + DATABASE_TABLE2 + " (" + KEY_ID
                + " text primary key , " + KEY_GESAMT
                + " text not null, " + KEY_GUTHABEN + " text);";

        private static final String DATABASE_CREATE3 = "create table "
                + DATABASE_TABLE3 + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_NAMEWG
                + " text not null);";

        private static final String DATABASE_CREATE4 = "create table "
                + DATABASE_TABLE4 + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_NAMEUSER
                + " text not null);";

        private static final String DATABASE_CREATE5 = "create table "
                + DATABASE_TABLE5 + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_NAMEP
                + " text not null, "+ KEY_TITEL +" text, "+KEY_DATUM+ " text, "+KEY_AUFWAND+" integer, "+KEY_FREQUENZ+ " text, " +KEY_TAG+ " text );";

        public ToDoDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE1);
            db.execSQL(DATABASE_CREATE2);
            db.execSQL(DATABASE_CREATE3);
            db.execSQL(DATABASE_CREATE4);
            db.execSQL(DATABASE_CREATE5);
            db.execSQL("INSERT INTO "+DATABASE_TABLE3+"("+KEY_NAMEWG+") VALUES ('Name der WG')");
            db.execSQL("INSERT INTO "+DATABASE_TABLE4+"("+KEY_NAMEUSER+") VALUES ('Dein Name')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
