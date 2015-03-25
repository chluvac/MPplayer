package com.example.vasek.mp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class DatabaseHandler { //přístup k databázi jen odtud

    private final String TAG 	 = "DatabaseHandler";
    static final  String NAME    = "name";
    static final  String VALUE   = "value";

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseHandler(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() { //otevře databázi
        database = dbHelper.getWritableDatabase();
        dbHelper.onCreate(database);
    }

    public void close() { //zavře databázi
        dbHelper.close();
    }

    public Cursor runQuery(String query, String [] args){ //projede příkaz databází
        Cursor c = database.rawQuery(query, args);
        return c;
    }

    public Uri findFileUri(String ID) { //najde uri pro dané ID
        String query = "SELECT location FROM tab WHERE id ?";
        Cursor uriCursor = this.runQuery(query, new String[] {ID});
        Uri value = Uri.parse(uriCursor.getString(1));
        return value;
    }

    public String findFileTitle(String ID){ //najde název pro dané ID
        String query = "SELECT title FROM tab WHERE id ?";
        Cursor uriCursor = this.runQuery(query, new String[] {ID});
        String value = uriCursor.getString(1);
        return value;
    }

    public void dataUpdate(){ //resetuje tabulku, projde SD kartu a uloží všechny mp3 do tabulky
        database.execSQL("DROP TABLE IF EXISTS tab");
        //dbHelper.onCreate(database);
        database.rawQuery("INSERT INTO tab (title, interpret, album, trackNr, location) VALUES (title, ja, neni, 5, doma )", null);
        ContentValues cv = new ContentValues();
        cv.put("title", "pokus");
        cv.put("interpret", "ja");
        cv.put("album", "moje");
        cv.put("trackNr", 1);
        cv.put("location", "doma");
        database.insert("tab", null, cv);
        File[] file = Environment.getExternalStorageDirectory().listFiles();
        for (File f : file) {
            if (f.isFile() && f.getAbsolutePath().endsWith(".mp3")) {
                MediaMetadataRetriever metaRetriever;
                metaRetriever = new MediaMetadataRetriever();
                metaRetriever.setDataSource(f.getAbsolutePath());
                String save_to_table = "INSERT INTO tab (title, interpret, album, trackNr, location) " +
                        "values (" + metaRetriever.extractMetadata(7) + ", " +
                        metaRetriever.extractMetadata(2) + ", " +
                        metaRetriever.extractMetadata(1) + ", " +
                        metaRetriever.extractMetadata(0) + ", " +
                        f.getAbsolutePath() + ")";
                database.rawQuery(save_to_table, null);
            }
        }
    }
}
