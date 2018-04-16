package com.example.deannebaldemor.machineproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Deanne Baldemor on 04/04/2018.
 */

public class MusicService extends Service{

    private MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri musicURI = Uri.parse("android.resource://com.example.deannebaldemor.machineproject/" + R.raw.tryagain);
        mediaPlayer= MediaPlayer.create(this, musicURI);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();

    }
}
