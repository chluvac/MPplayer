package com.example.vasek.mp;

import android.app.Activity;
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
    //TODO setFolder - dialog na nastavení složky s hudbou
    //TODO - elsy v clicklisteneru

    //kdybybylčas TODO při zobrazení fronty dlouhý kliknutí smaže předmět
    //kdybybylčas TODO a krátký to bude posouvat nahoru
    //kdybybylčas TODO nějaký elegantní prostředí
    //kdybybylčas TODO zapouzdřit clickHandlery do podmínky, když ukazuju frontu tak to nedělej

    PlaybackManager playback = new PlaybackManager(this);
    DBHelper database = new DBHelper();
    SQLiteDatabase library = database.getWritableDatabase();
    AdapterCreator adapterCreator = new AdapterCreator();

    public void listViewPrep(){ //dá data do listview - buď frontu nebo podle queryC
        /*if(showQueue){
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_main, playback.getQueueTitles());
            ListView listView = (ListView) findViewById(android.R.id.list);
            listView.setAdapter(adapter);
        } else{
            /*String query = queryC.getQuery(); //předá data z tabulky do kurzoru, do adaptéru a do listView
            Cursor libCursor = library.rawQuery(query, null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.activity_main, libCursor, new String[] {"title"}, new int[] {libCursor.getCount()});*/

            ListView listView = (ListView) findViewById(android.R.id.list);
            listView.setAdapter(adapterCreator.getAdapter(getApplicationContext(), playback.getQueueTitles())); //nastavuje adapter pro listView, dá do něj data z kurzoru
        //}
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewPrep();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //nastaví queryC kategorii
        switch (item.getItemId()) {
            case R.id.showAll:
                adapterCreator.setAlbum(null);
                adapterCreator.setShowCategory(0);
                this.listViewPrep();
                return true;
            case R.id.showQueue:
                adapterCreator.setShowCategory(3);
                this.listViewPrep();
                return true;
            case R.id.showInterprets:
                adapterCreator.setShowCategory(1);
                listViewPrep();
                return true;
            case R.id.reloadDatabase:
                database.dataUpdate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public Uri findFileUri(String ID){
        String query = "SELECT location FROM tab WHERE id ?";
        Cursor uriCursor = library.rawQuery(query, new String[] {ID});
        Uri value = Uri.parse(uriCursor.getString(1));
        return value;
    }

    public String findFileTitle(String ID){
        String query = "SELECT title FROM tab WHERE id ?";
        Cursor uriCursor = library.rawQuery(query, new String[] {ID});
        String value = uriCursor.getString(1);
        return value;
    }

    public void startPlaying(){
        try {
            playback.startPlaying(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AdapterView.OnItemClickListener clickHandler = new AdapterView.OnItemClickListener() { //na kliknutí poslat do fronty
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            if (adapterCreator.getShowCategory() == 0){
                playback.onClick(findFileUri(Long.toString(id)), (Long.toString(id)));
                startPlaying();
            } else if (adapterCreator.getShowCategory() == 1){

            } else if (adapterCreator.getShowCategory() == 1){

            }
            //TODO dodělat elsy - pošle na co to kliklo a musí to poznat autora či album - buď podle position nebo si vedle udělat pole id pro daný interprety
        }
    };

    private AdapterView.OnItemLongClickListener longClickHandler = new AdapterView.OnItemLongClickListener() {//na dlouhý kliknutí přehnrát
        public boolean onItemLongClick(AdapterView parent, View v, int position, long id) {
            if (adapterCreator.getShowCategory() == 0){
                playback.onLongClick(findFileUri(Long.toString(id)), findFileTitle(Long.toString(id)));
                startPlaying();
            }
            return true;
        }
    };

    public void playButton() {
        playback.playButton();
    }
}