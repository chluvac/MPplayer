package com.example.vasek.mp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper { //vytváří databázi a tabulku

    private final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "library";

    private static final String TABLE_NAME = "tab";
    /*public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_INTERPRET = "interpret";
    public static final String COLUMN_ALBUM = "album";
    public static final String COLUMN_TRACK = "trackNr";
    public static final String COLUMN_LOCATION = "location";*/

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS tab (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, interpret TEXT, album TEXT, trackNr INTEGER, location TEXT)";
            /*+ " ( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TITLE
            + " text, " + COLUMN_INTERPRET
            + " text, " + COLUMN_ALBUM
            + " text, " + COLUMN_TRACK
            + " integer " + COLUMN_LOCATION
            + " text " +
            ");";*/

    public DBHelper(Context context) { //vytvoří databázi
        super(context, "library", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //vytvoří tabulku
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*Log.w(DBHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion + ", which will destroy all old data");*/
        db.execSQL("DROP TABLE IF EXISTS tab");
        onCreate(db);
    }
}