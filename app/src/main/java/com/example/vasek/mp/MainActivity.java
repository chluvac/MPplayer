package com.example.vasek.mp;

import android.app.Activity;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.media.MediaPlayer;

public class MainActivity extends Activity {

    public void listViewPrep(){ //předá data z tabulky do kurzoru, do adaptéru a do listView

        libSQLiteHelper database = new libSQLiteHelper(this);

        String query = "SELECT id, title, location FROM lib";
        Cursor libCursor = libSQLiteHelper.library.rawQuery(query, null);  //data z tabulky do kurzoru, ZATÍM OK

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.activity_main, libCursor, database.library.lib.title, libCursor.getCount(), 0); //TODO: jak je to s argumenty, jak je to s vytvářením tabulky
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter); //nastavuje adapter pro listView, dá do něj data z kurzoru
        //TODO: array, kde bude uloženo co se má hrát - jenom ID v tabulce
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { //při vytvoření načte xml main
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //vytvoří UI
        listViewPrep();  //načte tabulku do listView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //zatím nic
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //ehm
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void reloadDatabase(){ //Mělo by fungovat
        libSQLiteHelper.dataUpdate(libSQLiteHelper.library);
    }





    public void play() {    //TODO
       if (true /*hraje*/){ /*nehraj*/}
       else {/*hraj*/ }

        Uri myUri = ; //TODO: dosadit z kurzoru
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(getApplicationContext(), myUri);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    public void next() {    //TODO
        //přeskoč na další
    }

    public void prev() {    //TODO
        //přeskoč na předchozí
    }
}
