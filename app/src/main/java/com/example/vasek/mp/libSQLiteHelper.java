package com.example.vasek.mp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import android.os.Environment;
            //databáze - library
            //tabulka - lib

public class libSQLiteHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "library";
    public static SQLiteDatabase library;   //jestli tu je nějáká vopičárna tak asi tady

    libSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    } //konstruktor

    @Override
    public void onCreate(SQLiteDatabase db) {   //vytvoří tabulku pro data
        String create_table = "CREATE TABLE lib ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "interpret TEXT, "+
                "album TEXT, "+
                "songNr INTEGER, "+
                "location TEXT )";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void dataUpdate(SQLiteDatabase db){
            db.execSQL("DELETE FROM IF EXISTS lib");        //reset tabulky

            File[] file = Environment.getExternalStorageDirectory().listFiles();
            for (File f : file) {
                if (f.isFile() && f.getAbsolutePath().endsWith(".mp3")) { //všechny mp3 projdou sem
                    String save_to_table = "INSERT INTO lib (title, location)" +
                            "values(" + f.getAbsolutePath() + "," + f.getAbsolutePath() + ")";
                    db.execSQL(save_to_table); //sem prošlý mp3 uloží do tabulky
                }
            }

    }
}
