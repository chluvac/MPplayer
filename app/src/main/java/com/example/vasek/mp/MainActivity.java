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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

public class MainActivity extends Activity {

    //TODO dodělat loop na playbacku
    //TODO - elsy v clicklisteneru


    private PlaybackManager playback = new PlaybackManager(this);
    private AdapterCreator adapterCreator = new AdapterCreator(this);
    public static DatabaseHandler dbHandler;

    public void listViewPrep(){ //dá data z adapterCreatoru do listview
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapterCreator.getAdapter(getApplicationContext(), playback.getQueueTitles())); //nastavuje adapter pro listView, dá do něj data z kurzoru
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DatabaseHandler(this);
        dbHandler.open();
        listViewPrep();
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
                adapterCreator.setAlbum(null);
                adapterCreator.setInterpret(null);
                adapterCreator.setShowCategory(0);
                this.listViewPrep();
                return true;
            case R.id.showQueue:
                adapterCreator.setShowCategory(3);
                this.listViewPrep();
                return true;
            case R.id.showInterprets:
                adapterCreator.setShowCategory(1);
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

    public AdapterView.OnItemClickListener clickHandler = new AdapterView.OnItemClickListener() { //na kliknutí pošle do fronty
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            if (adapterCreator.getShowCategory() == 0){
                playback.onClick(dbHandler.findFileUri(Long.toString(id)), (Long.toString(id)));
                startPlaying();
            } else if (adapterCreator.getShowCategory() == 1){

            } else if (adapterCreator.getShowCategory() == 1){

            }
            //TODO dodělat elsy - pošle na co to kliklo a musí to poznat autora či album - buď podle position nebo si vedle udělat pole id pro daný interprety
        }
    };

    private AdapterView.OnItemLongClickListener longClickHandler = new AdapterView.OnItemLongClickListener() {//na dlouhý kliknutí přehraje
        public boolean onItemLongClick(AdapterView parent, View v, int position, long id) {
            if (adapterCreator.getShowCategory() == 0){
                playback.onLongClick(dbHandler.findFileUri(Long.toString(id)), dbHandler.findFileTitle(Long.toString(id)));
                startPlaying();
            }
            return true;
        }
    };
}