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
        *  3 - fronta*/

    public void setShowCategory(int c){
        this.showCategory = c;
    }

    public int getShowCategory(){
        return this.showCategory;
    }

    private ListAdapter adapterAll(Context context){ //vypíše všechno z databáze nebo všechno v albu
        String query = "SELECT _id, title, location FROM tab ORDER BY title ASC";
        Cursor cursor = MainActivity.dbHandler.runQuery(query, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
                android.R.layout.simple_list_item_1, cursor, new String[] {"title"}, new int[] {android.R.id.text1});
        return adapter;
    }

    public ListAdapter adapterQueue(Context context, ArrayList titles){
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, titles);
        return adapter;
    }

    public ListAdapter getAdapter(Context context, ArrayList titles){
        ListAdapter a;
        if (showCategory == 0){
            a = adapterAll(context);
        }else if (showCategory == 3){
            a = adapterQueue(context, titles);
        }else a = null;
        return a;
    }
}