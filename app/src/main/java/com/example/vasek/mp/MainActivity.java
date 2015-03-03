package com.example.vasek.mp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //při vytvoření načte xml main
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    public void next() {    //TODO
        //přeskoč na další
    }

    public void prev() {    //TODO
        //přeskoč na předchozí
    }
}
