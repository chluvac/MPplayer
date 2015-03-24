package com.example.vasek.mp;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;

import static android.database.sqlite.SQLiteDatabase.create;
import static android.database.sqlite.SQLiteDatabase.openDatabase;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class DBHelper {

    public static final String TABLE_NAME = "tab";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_INTERPRET = "interpret";
    public static final String COLUMN_ALBUM = "album";
    public static final String COLUMN_TRACK = "trackNr";
    public static final String COLUMN_LOCATION = "location";

    private static final String TABLE_CREATE = "create table if not exists "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TITLE
            + " text, " + COLUMN_INTERPRET
            + " text, " + COLUMN_ALBUM
            + " text, " + COLUMN_TRACK
            + " integer " + COLUMN_LOCATION
            + " text " +
            ");";

    private SQLiteDatabase library;

    public DBHelper(){
        library = openOrCreateDatabase("database", null);
        library.execSQL(TABLE_CREATE);
        dataUpdate(); //TODO: jen když je tabulka prázdná
    };

    public void tableCreate(){
        library.execSQL(TABLE_CREATE);
    }

    public SQLiteDatabase getWritableDatabase(){
        return library;
    }

    public void dataUpdate(){
        int n = 0;
        while (n<2000000000){
            n++;
        }
        library.execSQL("DROP TABLE IF EXISTS tab");  //reset tabulky
        library.execSQL(TABLE_CREATE);

        File[] file = Environment.getExternalStorageDirectory().listFiles(); //do pole file uloží všechny soubory TODO:vybrat složku ze který to chci vybírat - ne celý uložiště
        for (File f : file) {   //pro každou položku v poli vytvoří objekt a projde cyklus
            if (f.isFile() && f.getAbsolutePath().endsWith(".mp3")) { //všechny mp3 projdou sem, zbytek tu vypadne
                MediaMetadataRetriever metaRetriever;
                metaRetriever = new MediaMetadataRetriever();
                metaRetriever.setDataSource(f.getAbsolutePath()); //objekt na nalezení metadat k danýmu objektu souboru
                String save_to_table = "INSERT INTO tab (title, interpret, album, trackNr, location)" +
                        "values(" + metaRetriever.extractMetadata(7) + "," +
                        metaRetriever.extractMetadata(2) + "," +
                        metaRetriever.extractMetadata(1) + "," +
                        metaRetriever.extractMetadata(0) + "," +
                        f.getAbsolutePath() + ")";
                library.rawQuery(save_to_table, null); //sem prošlý mp3 uloží do tabulky včetně metadat
            }
        }
    }
}