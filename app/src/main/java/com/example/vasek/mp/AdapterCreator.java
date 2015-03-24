package com.example.vasek.mp;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class AdapterCreator {

    private int showCategory;
        /* 0 - vše
        *  1 - interpreti
        *  2 - alba
        *  3 - fronta*/

    private String interpret;
    private String album;

    DBHelper database = new DBHelper();
    SQLiteDatabase library = database.getWritableDatabase();
    public void setShowCategory(int c){
        this.showCategory = c;
    }

    ArrayList<String> interprets = new ArrayList<String>();

    public void setInterpret(String i){
        this.interpret = i;
    }

    public void setAlbum(String a){
        this.album = a;
    }

    public int getShowCategory(){
        return this.showCategory;
    }

    private ListAdapter adapterAll(Context context){ //TODO: když chci vše, vynulovat album
        if (album.isEmpty()){
            String query = "SELECT _id, title FROM tab ORDER BY _id ASC";
            Cursor libCursor = library.rawQuery(query, null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
                    R.layout.activity_main, libCursor, new String[] {"title"}, new int[] {libCursor.getCount()});
            return adapter;
        } else {String query = "SELECT _id, title FROM tab WHERE album " + album + " ORDER BY _id ASC";
        Cursor libCursor = library.rawQuery(query, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
               R.layout.activity_main, libCursor, new String[] {"title"}, new int[] {libCursor.getCount()});
        return adapter;
        }
    }

    private ListAdapter adapterInterprets(Context context){
        String query = "SELECT DISTINCT interprets FROM tab ORDER BY interpret";
        Cursor libCursor = library.rawQuery(query, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
                R.layout.activity_main, libCursor, new String[] {"title"}, new int[] {libCursor.getCount()});
        return adapter;
    }

    private ListAdapter adapterAlbums(Context context){
        String query = "SELECT DISTINCT album FROM tab WHERE interpret " + interpret + " ORDER BY album ASC";
        Cursor libCursor = library.rawQuery(query, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
                R.layout.activity_main, libCursor, new String[] {"title"}, new int[] {libCursor.getCount()});
        return adapter;
    }

    public ListAdapter adapterQueue(Context context, ArrayList titles){ //TODO arrayadapter
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.activity_main, titles);
        return adapter;
    }

    public ListAdapter getAdapter(Context context, ArrayList titles){
        ListAdapter a;
        if (showCategory == 0){
            a = adapterAll(context);
        } else if (showCategory == 1){
            a = adapterInterprets(context);
        }else if (showCategory == 2){
            a = adapterAlbums(context);
        }else if (showCategory == 3){
            a = adapterQueue(context, titles);
        }else a = null;
        return a;
    }
}