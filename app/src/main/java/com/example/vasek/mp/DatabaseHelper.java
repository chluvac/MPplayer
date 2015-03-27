package com.example.vasek.mp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper { //vytváří databázi a tabulku

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS tab (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, interpret TEXT, album TEXT, trackNr INTEGER, location TEXT)";

    public DatabaseHelper(Context context) { //vytvoří databázi
        super(context, "library", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //vytvoří tabulku
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tab");
        onCreate(db);
    }
}