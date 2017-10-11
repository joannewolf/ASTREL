package com.example.chiao.astrel;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Chiao on 2017/6/5.
 */

public class mVibrator {
    public static Vibrator vibrator;
    public static boolean isVibrating = false;

    public static void startVibrate(Context context, long[] pattern){
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (!isVibrating) {
            vibrator.vibrate(pattern, 0);
            isVibrating = true;
        }
    }

    public static void stopVibrate(){
        vibrator.cancel();
        isVibrating = false;
    }
}
