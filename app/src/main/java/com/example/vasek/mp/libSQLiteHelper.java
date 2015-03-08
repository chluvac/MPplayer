package com.example.vasek.mp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import android.os.Environment;
import android.media.MediaMetadataRetriever;

            //databáze - library
            //tabulka - lib

public class libSQLiteHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "library";
    public static SQLiteDatabase library;   //jestli tu je nějáká vopičárna tak asi tady

    libSQLiteHelper(Context context){ //konstruktor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(library);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {   //vytvoří tabulku pro data
        String create_table = "CREATE TABLE lib ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "interpret TEXT, "+
                "album TEXT, "+
                "trackNr INTEGER, "+
                "location TEXT )";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //zbytečný, ale povinný
    }

    public static void dataUpdate(SQLiteDatabase db){
            db.execSQL("DELETE FROM IF EXISTS lib");  //reset tabulky

            File[] file = Environment.getExternalStorageDirectory().listFiles(); //do pole file uloží všechny soubory TODO:vybrat složku ze který to chci vybírat - ne celý uložiště
            for (File f : file) {   //pro každou položku v poli vytvoří objekt a projde cyklus
                if (f.isFile() && f.getAbsolutePath().endsWith(".mp3")) { //všechny mp3 projdou sem, zbytek tu vypadne
                    MediaMetadataRetriever metaRetriever;
                    metaRetriever = new MediaMetadataRetriever();
                    metaRetriever.setDataSource(f.getAbsolutePath()); //objekt na nalezení metadat k danýmu objektu souboru


                    String save_to_table = "INSERT INTO lib (title, interpret, album, trackNr, location)" +
                            "values(" + metaRetriever.extractMetadata(7) + "," +
                                        metaRetriever.extractMetadata(2) + "," +
                                        metaRetriever.extractMetadata(1) + "," +
                                        metaRetriever.extractMetadata(0) + "," +
                                        f.getAbsolutePath() + ")";
                    db.execSQL(save_to_table); //sem prošlý mp3 uloží do tabulky včetně metadat
                }
            }

    }


}
