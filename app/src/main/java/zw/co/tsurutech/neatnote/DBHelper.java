package zw.co.tsurutech.neatnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

/**
 * Created by Kudzie on 7/27/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    static String DatabaseName = "notes.db";
    public static final int DatabaseVersion = 1;

    /***************************Table *******************************/
    public static final String table = "tblNotes";
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_TIME = "time";
    public static final String KEY_DATE = "date";


    public DBHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE =  "CREATE TABLE IF NOT EXISTS "+table+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_TITLE+" TEXT, "+KEY_BODY+" TEXT, "+KEY_TIME+" TEXT, "+KEY_DATE+" TEXT)";
        try{
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            sqLiteDatabase.execSQL("DROP IF EXISTS "+DatabaseName);
            onCreate(sqLiteDatabase);
        }catch (Exception e){e.printStackTrace();}
    }

    /******************************CRUD******************************/
    //New record function
    public boolean newNote(String title, String body){
        Date date = new Date();
        SimpleDateFormat dateft = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeft = new SimpleDateFormat("HH:mm");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_BODY, body);
        values.put(KEY_TIME, timeft.format(date));
        values.put(KEY_DATE, dateft.format(date));

        long result = db.insert(table, null, values);

        return result > 0;
    }

    //Edit existing record
    public boolean editNote(int id, String title, String body){
        Date date = new Date();
        SimpleDateFormat dateft = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeft = new SimpleDateFormat("HH:mm");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_BODY, body);
        values.put(KEY_TIME, timeft.format(date));
        values.put(KEY_DATE, dateft.format(date));

        long result = db.update(table, values, KEY_ID+" = "+id, null);

        return result > 0;
    }

    //Deleting a record
    public boolean deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(table, KEY_ID+" = "+id, null);
        return result > 0;
    }

    //Fetch all records
    public Cursor fetchNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+table+" ORDER BY "+KEY_TIME+", "+KEY_DATE+" DESC";
        return db.rawQuery(query, null);
    }

    //fetch just the title
    public String fetchTitle(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+table+" WHERE "+KEY_ID+" = "+id;
        Cursor cursor = db.rawQuery(query, null);
        String result = "nothing";
        if (cursor.moveToFirst()){
            do{
                result = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE));
            }while (cursor.moveToNext());
        }
        return result;
    }

    //fetch just the body
    public String fetchBody(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+table+" WHERE "+KEY_ID+" = "+id;
        Cursor cursor = db.rawQuery(query, null);
        String result = "Nothing";
        if (cursor.moveToFirst()){
            do{
                result = cursor.getString(cursor.getColumnIndexOrThrow(KEY_BODY));
            }while (cursor.moveToNext());
        }
        return result;
    }

    //Delete everything
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "DROP TABLE "+table;
        db.execSQL(qry);

        String CREATE_TABLE =  "CREATE TABLE IF NOT EXISTS "+table+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_TITLE+" TEXT, "+KEY_BODY+" TEXT, "+KEY_TIME+" TEXT, "+KEY_DATE+" TEXT)";
        try{
            db.execSQL(CREATE_TABLE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
