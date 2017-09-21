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
    //DB Tabellen
    private static final String TERMINE = "termine";
    private static final String EINKAUF = "einkauf";
    private static final String BENUTZER = "benutzer";
    private static final String DIESERBENUTZER = "dieserbenutzer";
    private static final String PUTZPLAN = "putzplan";
    private static final String EKHIST = "ekhist";

    //Keys für Calendar
    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_DATE = "date";
    public static final String KEY_NAME = "name";
    public static final String KEY_KOSTEN = "kosten";

    //Keys für Finanzen
    public static final String KEY_GUTHABEN = "guthaben";
    public static final String KEY_GESAMT = "gesamt";
    //Keys für DBName
    public static final String KEY_NAMEWG = "namewg";
    //Keys für DBUserName
    public static final String KEY_NAMEUSER = "nameuser";
    //Keys für Putzplan
    public static final String KEY_TITEL = "titel";
    public static final String KEY_FREQUENZ = "frequenz";
    public static final String KEY_DATUM = "datum";
    public static final String KEY_TAG = "tag";
    public static final String KEY_NAMEP = "name";
    public static final String KEY_AUFWAND = "aufwand";
    public static final String KEY_BILD = "bild";
    public static final String KEY_PUNKTE = "punkte";


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
        return db.insert(TERMINE, null, itemValues);
    }
    public long insertPItem(PutzplanItem item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_NAMEP, item.getName());
        itemValues.put(KEY_TITEL, item.getTitel());
        itemValues.put(KEY_DATUM, item.getDate());
        itemValues.put(KEY_AUFWAND, item.getAufwand());
        itemValues.put(KEY_FREQUENZ, item.getFrequenz());
        itemValues.put(KEY_TAG, item.getTag());
        return db.insert(PUTZPLAN, null, itemValues);
    }
    //Insert-Methode für Einkauf
    public long insertEinkaufItem(EinkaufsItem item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_NAME, item.getName());
        return db.insert(EINKAUF, null, itemValues);
    }

    //Insert-Methdoe für Finanzen
    public void insertFinanzen(double guthaben, String username) {
        db.execSQL("UPDATE "+BENUTZER+" SET "+KEY_GUTHABEN+" = '"+guthaben+"'" +
                "WHERE name = '"+username+"';");

    }

    public void insertFinanzenGes(double guthaben, String wgname) {
        db.execSQL("UPDATE "+BENUTZER+" SET "+KEY_GESAMT+" = '"+guthaben+"' WHERE "+KEY_NAMEWG+" = '"+wgname+"';");

    }

    public void insertPunkte(int punkte , String name) {
        db.execSQL("UPDATE "+BENUTZER+" SET "+KEY_PUNKTE+" = '"+punkte+"' WHERE "+KEY_NAME+" = '"+name+"';");

    }

    public void insertNewUser(String name, String wgname){
        db.execSQL("INSERT INTO "+BENUTZER+" VALUES ('"+name+"', '0', '0','"+wgname+"','0');");

    }


    //Insert-Methode Name der Wg
    public long insertWgName (String name){
        ContentValues nameValues = new ContentValues();
        nameValues.put(KEY_NAMEWG,name);
        return db.update(DIESERBENUTZER,nameValues,null,null);
    }
    //Insert-Methode Name des User
    public long insertWgUserName (String name){
        ContentValues nameValues = new ContentValues();
        nameValues.put(KEY_NAMEUSER,name);
        return db.update(DIESERBENUTZER,nameValues,null,null);
    }
    public void insertHist (String nameuser,String name, double kosten){
        db.execSQL("INSERT INTO "+EKHIST+" ("+KEY_NAMEUSER+","+KEY_NAME+","+KEY_KOSTEN+") VALUES('"+nameuser+"','"+name+"','"+kosten+"');");
    }
    //Remove-Methode für Calendar
    public void removeToDoItem(CalendarItem item) {

        String toDelete = KEY_TASK + "=?";
        String[] deleteArguments = new String[]{item.getName()};
        db.delete(TERMINE, toDelete, deleteArguments);

    }
    //Remove-Methode für Calendar
    public void removePutzplanItem(PutzplanItem item) {

        String toDelete = KEY_NAMEP + "=?";
        String[] deleteArguments = new String[]{item.getName()};
        db.delete(PUTZPLAN, toDelete, deleteArguments);

    }
    public void removeEinkaufItem(EinkaufsItem item) {

        String toDelete = KEY_NAME + "=?";
        String[] deleteArguments = new String[]{item.getName()};
        db.delete(EINKAUF, toDelete, deleteArguments);

    }

    public void removeMitbewohner(String name) {

        String toDelete = KEY_NAME + "=?";
        String[] deleteArguments = new String[]{name};
        db.delete(BENUTZER, toDelete, deleteArguments);

    }

    //get All CalendarItems
    public ArrayList<CalendarItem> getAllToDoItems() {
        ArrayList<CalendarItem> items = new ArrayList<CalendarItem>();
        Cursor cursor = db.query(TERMINE, new String[] { KEY_ID,
                KEY_TASK, KEY_DATE}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String task = cursor.getString(1);
                String date = cursor.getString(2);

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
        Cursor cursor = db.query(EINKAUF, new String[] {
                KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                items.add(new EinkaufsItem(name));

            } while (cursor.moveToNext());
        }
        return items;
    }

    //get All Mitbewohner
    public ArrayList<SettingsUser> getAllMitbewohner() {
        ArrayList<SettingsUser> items = new ArrayList<SettingsUser>();
        Cursor cursor = db.query(BENUTZER, new String[] {
                KEY_NAME, KEY_PUNKTE}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                int aufwand = cursor.getInt(1);
                String result = ""+name+"     hat   "+aufwand+" Punkte";
                items.add(result);

            } while (cursor.moveToNext());
        }
        return items;
    }

    //gat Anzhal Mitbewohner
    public int getAnzahlMitbewohner() {;
        int anzahl =0;
        Cursor cursor = db.query(BENUTZER, new String[] {
                KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                anzahl++;

            } while (cursor.moveToNext());
        }
        return anzahl;
    }

    //get All Mitbewohner
    public ArrayList<String> getAllEkitems() {
        ArrayList<String> items = new ArrayList<String>();
        Cursor cursor = db.query(EKHIST, new String[] {
                KEY_NAME, KEY_KOSTEN}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String kosten = ""+Math.round(cursor.getDouble(1));
                items.add(""+name+"    koscht    "+kosten+"    €   !");

            } while (cursor.moveToNext());
        }
        return items;
    }
    //get Putzplan
    public ArrayList<PutzplanItem> getAllPutzplanItems() {
        ArrayList<PutzplanItem> items = new ArrayList<PutzplanItem>();
        Cursor cursor = db.query(PUTZPLAN, new String[] {KEY_ID,
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
        Cursor cursor = db.query(DIESERBENUTZER, new String[] {
                KEY_NAMEWG}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(0);

            } while (cursor.moveToNext());
        }
        return name;
    }
    //get WGName
    public String getUserName() {
        String name ="";
        Cursor cursor = db.query(DIESERBENUTZER, new String[] {
                KEY_NAMEUSER}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(0);

            } while (cursor.moveToNext());
        }
        return name;
    }

    //get Guthabane

    public double getMeinGuthaben(String name) {
        double guthaben = 0;
        Cursor c = db.rawQuery("SELECT "+KEY_GUTHABEN+" FROM "+BENUTZER+" WHERE TRIM("+KEY_NAME+") = '"+name.trim()+"'", null);
        if (c.moveToFirst()) {
            do {
                guthaben = c.getDouble(0);

            } while (c.moveToNext());
        }
        return guthaben;
    }


    //get GuthabenGesamt
    public double getGuthabenGes() {
        double guthaben = 0;
        Cursor cursor = db.query(BENUTZER, new String[] {
                KEY_GESAMT}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                guthaben = cursor.getDouble(0);

            } while (cursor.moveToNext());
        }
        return guthaben;
    }

    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String CREATETASK = "create table "
                + TERMINE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TASK
                + " text not null, " + KEY_DATE + " text " + KEY_NAMEWG
                + " text );";


        private static final String CREATEEKITEM = "create table "
                + EINKAUF + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_NAME
                + " text not null, " + KEY_NAMEWG
                + " text );";

        private static final String CREATEUSER = "create table "
                + BENUTZER + " (" + KEY_NAME
                + " text primary key , " + KEY_GESAMT
                + " double, " + KEY_GUTHABEN + " double, " + KEY_NAMEWG
                + " text ,"+KEY_PUNKTE+" integer );";

        private static final String CREATEEKHIST = "create table "
                + EKHIST + " (" + KEY_ID
                + " integer primary key autoincrement , " + KEY_NAMEUSER
                + " text, " + KEY_KOSTEN + " double, " + KEY_NAME
                + " text );";

        private static final String CREATEGRUPPE = "create table "
                + DIESERBENUTZER + " (" + KEY_ID
                + " integer primary key autoincrement, "+ KEY_NAMEWG
                + " text , " + KEY_NAMEUSER
                + " text );";


        private static final String CREATEPUTZPLAN = "create table "
                + PUTZPLAN + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_NAMEP
                + " text not null, "+ KEY_TITEL +" text, "+KEY_DATUM+ " text, "+KEY_AUFWAND+" integer, "+KEY_FREQUENZ+ " text, " +KEY_TAG+ " text, "+ KEY_NAMEWG
                + " text );";

        public ToDoDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATEGRUPPE);
            db.execSQL(CREATEUSER);
            db.execSQL(CREATETASK);
            db.execSQL(CREATEPUTZPLAN);
            db.execSQL(CREATEEKITEM);
            db.execSQL(CREATEEKHIST);
            db.execSQL("INSERT INTO "+DIESERBENUTZER+"("+KEY_NAMEWG+","+KEY_NAMEUSER+") VALUES ('Name der WG', 'Dein Name');");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
