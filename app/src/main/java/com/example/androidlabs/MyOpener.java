package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyOpener extends SQLiteOpenHelper {
    public static final String ACTIVITY_NAME = "MY_OPENER_ACTIVITY";
    protected final static String DATABASE_NAME = "CHAT_ACTIVITY_DB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "CHAT";
    public final static String COL_MESSAGE = "MESSAGE";
    public final static String COL_SIDE = "SIDE";
    public final static String COL_ID = "_id";

    public MyOpener(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MESSAGE + " TEXT," +
                COL_SIDE + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public int count()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME;
        int messageCount = db.rawQuery(sql, null).getCount();
        db.close();

        return messageCount;

    }

    public long insertMessage(Message message)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MESSAGE, message.getMessage());

        if(message.isSide()) values.put(COL_SIDE, 1);
        else values.put(COL_SIDE, 0);

        long n = db.insert(TABLE_NAME, null, values);
        return n;
    }

    public List<Message> read()
    {
        List <Message> records = new ArrayList<Message>();
        // String sql = "SELECT * FROM Message ORDER BY id ASC";
        String sql = "SELECT * FROM "+TABLE_NAME;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(COL_ID)));
                String messageEntered = cursor.getString(cursor.getColumnIndex(COL_MESSAGE));
                int side = cursor.getInt(cursor.getColumnIndex(COL_SIDE));

                boolean boolside = false;
                if(side==1) {
                    boolside =true;
                }
                else if(side==0){
                    boolside=false;
                }

                Message message = new Message(messageEntered, boolside, id);
                records.add(message);

            } while (cursor.moveToNext());
        }
        printCursor(cursor);
        cursor.close();
        db.close();

        return records;
    }

    private void printCursor(Cursor cursor)
    {

        Log.i(ACTIVITY_NAME,  "Database Version: "+VERSION_NUM);

        Log.i(ACTIVITY_NAME,  "column count: "+cursor.getColumnCount());

        for(int i = 0; i < cursor.getColumnCount() ; i++){
            Log.i(ACTIVITY_NAME,  "column "+i+": " +cursor.getColumnName(i));
        }

        Log.i(ACTIVITY_NAME,  "Row Count: "+count());

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(ACTIVITY_NAME,"Id: " + Long.parseLong(cursor.getString(cursor.getColumnIndex(COL_ID))));
                Log.i(ACTIVITY_NAME,"Message: " + cursor.getString(cursor.getColumnIndex(COL_MESSAGE)));
                int side = cursor.getInt(cursor.getColumnIndex(COL_SIDE));

                boolean boolside = false;
                if(side==1) {
                    boolside =true;
                }
                else if(side==0){
                    boolside=false;
                }
                cursor.moveToNext();
            }
        }


        Log.i(ACTIVITY_NAME,  String.valueOf(cursor.getColumnCount()));
    }

    public int delete(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME, COL_ID+"='"+id+"'", null);
        db.close();
        return i;
    }

//    public void insertMessage(Message message)
//    {
//       // SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COL_MESSAGE, message.getMessage());
//        if(message.isSide())
//            cv.put(COL_SIDE, 1);
//        else cv.put(COL_SIDE, 0);
//    }
}