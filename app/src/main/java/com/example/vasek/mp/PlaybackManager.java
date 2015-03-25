package com.example.vasek.mp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;


public class PlaybackManager {

    public MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<Uri> queue = new ArrayList<Uri>();
    private ArrayList<String> queueTitles = new ArrayList<String>();


    MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener(){ //když dohraje resetuje MP
        @Override
        public void onCompletion(MediaPlayer mediaPlayer){ //TODO: má zavolat startPlaying, aby začla hrát další z fronty, ale context nejde
            mediaPlayer.reset();
            queue.remove(1);
            //MainActivity.startPlaying();
        }
    };

    public PlaybackManager(Context context){ //připraví MP
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(listener);
        try {
            startPlaying(context);
        } catch (IOException e) {
        }
    }

    public ArrayList getQueue(){
        return this.queue;
    }

    public ArrayList getQueueTitles(){
        return this.queueTitles;
    }

    public int getQueueSize(){
        return this.queue.size();
    }

    public void startPlaying(Context context) throws IOException { //začne hrát jestli nehraje a fronta není prázdná
        if(!mediaPlayer.isPlaying() && !queue.isEmpty()){
            mediaPlayer.setDataSource(context, queue.get(1));
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
    }

    public void onClick(Uri uri, String title){ //přidá položku do fronty
        this.queue.add(uri);
        this.queueTitles.add(title);
    }

    public void onLongClick(Uri uri, String title){ //resetuje frontu a přidá položku
        this.queue.clear();
        this.queueTitles.clear();
        this.queue.add(uri);
        this.queueTitles.add(title);
        mediaPlayer.reset();
    }

    public void playButton(){ //tlačítko spustit/pauznout
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }
}