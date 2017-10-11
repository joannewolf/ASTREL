package com.example.chiao.astrel;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

public class MainActivity extends AppCompatActivity {

//    private String phoneNumber = "8589994785";
    private String phoneNumber = "9517726467";
    protected static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 965;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button vibrate_button = (Button) findViewById(R.id.vibrate_button);
        Button audio_button = (Button) findViewById(R.id.audio_button);
        Button notify_button = (Button) findViewById(R.id.notify_button);
        Button stop_button = (Button) findViewById(R.id.stop_button);
        Button SMS_button = (Button) findViewById(R.id.SMS_button);
        final TextView notification_message = (TextView) findViewById(R.id.notification_message);
//        final MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.bear);
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        vibrate_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibe.vibrate(500);
            }
        });

        audio_button.setOnClickListener(new View.OnClickListener() {
            MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sample1);
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                else
                    mediaPlayer.start();
            }
        });

        notify_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setColor(Color.BLUE)
                        .setContentTitle("Hi")
                        .setContentText("you click the button")
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setVibrate(new long[] {500, 500})
                        .setLights(Color.YELLOW, 1000, 1000);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

                mMediaPlayer.startAudio(getApplicationContext(), R.raw.bear);
//                mediaPlayer.setLooping(true);
//                mediaPlayer.start();
                mVibrator.startVibrate(getApplicationContext(), new long[] {500, 500});
//                vibe.vibrate(new long[] {500, 500}, 0);
//                stop_button.setVisibility(View.VISIBLE);
                notification_message.setText("Button Clicked");
            }
        });

//        stop_button.setVisibility(View.GONE);
        stop_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMediaPlayer.stopAudio();
//                mediaPlayer.pause();
                mVibrator.stopVibrate();
//                vibe.cancel();
//                stop_button.setVisibility(View.GONE);
                notification_message.setText("");
            }
        });

        SMS_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendSMS(phoneNumber, "Mickie has some white pills.");
            }
        });

    }

    private void sendSMS(String phoneNumber, String message) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
        else {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, null, null);
        }
        Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    sendSMS(phoneNumber, "Chiao didn't take the medicine");
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one

        TextView notification_message = (TextView) findViewById(R.id.notification_message);
        notification_message.setText(intent.getStringExtra("notification message"));
    }
}
