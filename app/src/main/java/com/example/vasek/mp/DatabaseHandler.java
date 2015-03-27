package com.example.vasek.mp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHandler { //přístup k databázi jen odtud

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseHandler(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() { //otevře databázi
        database = dbHelper.getWritableDatabase();
        //dbHelper.onCreate(database);
    }

    public Cursor runQuery(String query, String [] args){ //projede příkaz databází
        Cursor c = database.rawQuery(query, args);
        return c;
    }

    public Uri findFileUri(String ID) { //najde uri pro dané ID
        String query = "SELECT location FROM tab WHERE _id = " + ID;
        Cursor uriCursor = this.database.rawQuery(query, null);
        uriCursor.moveToFirst();
        Uri value = Uri.parse(uriCursor.getString(0));
        return value;
    }

    public String findFileTitle(String ID){ //najde název pro dané ID
        String query = "SELECT title FROM tab WHERE _id = " + ID;
        Cursor uriCursor = this.runQuery(query, null);
        uriCursor.moveToFirst();
        String value = uriCursor.getString(0);
        return value;
    }

    public void getFiles(ArrayList<File> list, File dir){ //dá do arraylistu všechny soubory
        File[] files = {};
        files = dir.listFiles();
        if(files.length == 0){}else{
            for(File file : files){
                if(file.isDirectory()){
                    getFiles(list, file); //nesmí výt prázdná složka
                }else{
                    list.add(file);
                }
            }
        }
    }

    public String findExternalStoragePath(){
        String [] paths = { "/storage/sdcard1/hudba",
                "/storage/extSdCard/hudba",
                "/storage/sdcard/hudba",
                "/storage/sdcard0/hudba",
                "/emmc/hudba",
                "/mnt/sdcard/external_sd/hudba",
                "/mnt/external_sd/hudba",
                "/sdcard/sd/hudba",
                "/mnt/sdcard/bpemmctest/hudba",
                "/mnt/sdcard/_ExternalSD/hudba",
                "/mnt/sdcard-ext/hudba",
                "/mnt/Removable/MicroSD/hudba",
                "/Removable/MicroSD/hudba",
                "/mnt/external1/hudba",
                "/mnt/extSdCard/hudba",
                "/mnt/extsd/hudba"
        };
        for(String p : paths){
            File file = new File(p);
            if(file.isDirectory()){
                return p;
            }
        }
        return null;
    }

    public void dataUpdate(){ // resetuje tabulku, zavolá getFiles a každý soubor ze seznamu, který je mp3, uloží do databáze
        database.execSQL("DROP TABLE IF EXISTS tab");
        dbHelper.onCreate(database);

        ArrayList<File> list= new ArrayList<File>();
        String path = findExternalStoragePath();
        File dir = new File (path);
        getFiles(list, dir);

        for (File f : list) {
            if (f.isFile() && f.getAbsolutePath().endsWith(".mp3")) {
                MediaMetadataRetriever metaRetriever;
                metaRetriever = new MediaMetadataRetriever();
                metaRetriever.setDataSource(f.getAbsolutePath());
                ContentValues cv = new ContentValues();
                cv.put("title", metaRetriever.extractMetadata(7));
                cv.put("interpret", metaRetriever.extractMetadata(2));
                cv.put("album", metaRetriever.extractMetadata(1));
                cv.put("trackNr", metaRetriever.extractMetadata(0));
                cv.put("location", f.getAbsolutePath());
                database.insert("tab", "Neznámé", cv);
            }
        }
    }
}