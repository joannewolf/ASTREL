package com.example.chiao.astrel;

import android.media.MediaPlayer;
import android.content.Context;

/**
 * Created by Chiao on 2017/6/5.
 */

public class mMediaPlayer {
    private static MediaPlayer mediaPlayer;
    private static boolean isPlayingAudio = false;

    public static void startAudio(Context context, int resid){
        if (!isPlayingAudio) {
            mediaPlayer = MediaPlayer.create(context, resid);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            isPlayingAudio = true;
        }
    }

    public static void stopAudio(){
        mediaPlayer.stop();
        mediaPlayer.release();
        isPlayingAudio = false;
    }
}
