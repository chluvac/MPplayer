package com.example.vasek.mp;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.widget.ListView;
import android.os.Bundle;
import java.util.ArrayList;

public class ListViewBrowse extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_browse);

        ListView listview = (ListView) findViewById(R.id.listView);
        String[] values = new String[] {}; //sem postupně uložit všechno z tabulky
        long size = DatabaseUtils.queryNumEntries(libSQLiteHelper.library, "lib");
        for (int n = 0; n < size; n++){
            Cursor SQLReturn = libSQLiteHelper.library.rawQuery("SELECT title FROM lib WHERE id = ?", new  String[2]);
        };

        final ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
    }
}