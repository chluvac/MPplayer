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
    boolean wasPaused;
    Context context;

    MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener(){ //když dohraje resetuje MP
        @Override
        public void onCompletion(MediaPlayer mediaPlayer){ //TODO: má zavolat startPlaying, aby začla hrát další z fronty, ale context nejde
            if (!wasPaused){queue.remove(0);
                queueTitles.remove(0);
                mediaPlayer.reset();
                try {
                    startPlaying(context);
                } catch (IOException e) {}
            }
        }
    };

    public PlaybackManager(Context ctx){ //připraví MP
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(listener);
        context = ctx;
    }

    public ArrayList getQueueTitles(){
        return this.queueTitles;
    }

    public void startPlaying(Context context) throws IOException { //začne hrát jestli nehraje a fronta není prázdná
        if(!mediaPlayer.isPlaying() && !queue.isEmpty()){
            mediaPlayer.setDataSource(context, queue.get(0));
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
        if(queue.isEmpty()){}else{
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                wasPaused = true;
            }else{
                mediaPlayer.start();
                wasPaused = false;
            }
        }
    }

    public void onQueueItemClick(int position){ //smaže položku z fronty
        this.queue.remove(position);
        this.getQueueTitles().remove(position);
        if(position == 0){
            mediaPlayer.reset();
            try {
                startPlaying(context);
            } catch (IOException e) {}
        }
    }
}