package com.example.vasek.mp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;

public class MainActivity extends Activity {

    private PlaybackManager playback = new PlaybackManager(this);
    private AdapterCreator adapterCreator = new AdapterCreator();
    public static DatabaseHandler dbHandler;
    ListView listView;
    ListAdapter a;

    public void listViewPrep(){ //dá data z adapterCreatoru do listview
        a = adapterCreator.getAdapter(getApplicationContext(), playback.getQueueTitles());
        listView.setAdapter(a); //nastavuje adapter pro listView, dá do něj data z kurzoru
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DatabaseHandler(this);
        dbHandler.open();
        listView = (ListView) findViewById(android.R.id.list);
        this.listViewPrep();
        listView.setOnItemClickListener(clickHandler);
        listView.setOnItemLongClickListener(longClickHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //vybaluje menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //příkazy pro menu
        switch (item.getItemId()) {
            case R.id.playButton:
                playback.playButton();
                return true;
            case R.id.showAll:
                adapterCreator.setShowCategory(0);
                this.listViewPrep();
                return true;
            case R.id.showQueue:
                adapterCreator.setShowCategory(3);
                this.listViewPrep();
                return true;
            case R.id.reloadDatabase:
                dbHandler.dataUpdate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startPlaying(){ //spustí přehrávání
        try {
            playback.startPlaying(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onQueueClick(int position){
        playback.onQueueItemClick(position);
        listViewPrep();
    }

    private AdapterView.OnItemClickListener clickHandler = new AdapterView.OnItemClickListener() { //na kliknutí pošle do fronty
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            if (adapterCreator.getShowCategory() == 0){
                playback.onClick(dbHandler.findFileUri(Long.toString(id)), dbHandler.findFileTitle(Long.toString(id)));
                startPlaying();
            }
        }
    };

    private AdapterView.OnItemLongClickListener longClickHandler = new AdapterView.OnItemLongClickListener() {//na dlouhý kliknutí přehraje
        public boolean onItemLongClick(AdapterView parent, View v, int position, long id) {
            if (adapterCreator.getShowCategory() == 0){
                playback.onLongClick(dbHandler.findFileUri(Long.toString(id)), dbHandler.findFileTitle(Long.toString(id)));
                startPlaying();
            } else if (adapterCreator.getShowCategory() == 3){
                onQueueClick(position);
            }
            return true;
        }
    };
}