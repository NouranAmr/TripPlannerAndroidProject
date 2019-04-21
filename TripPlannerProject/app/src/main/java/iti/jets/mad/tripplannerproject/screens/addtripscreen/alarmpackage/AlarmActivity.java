package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import iti.jets.mad.tripplannerproject.R;


public class AlarmActivity extends AppCompatActivity {

    private AlertDialog.Builder alertBuilder;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setFinishOnTouchOutside(false);

        Log.i("ddd","ddd");

        player=MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        player.setLooping(true);
        player.setVolume(100.0f,100.0f);
        player.start();
        final int tripId=getIntent().getIntExtra("tripId",0);
        alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setTitle("tripaya")
                .setMessage("Do yo want to Start Trip ?")
                .setPositiveButton("start",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        player.stop();
                        player.release();

                        //StartTrip
                        finish();
                    }
                }).setNeutralButton("snooze", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player.stop();
                player.release();

                //Presenter   snooze trip
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player.stop();
                player.release();

                //presenter  cancel trip
                finish();
            }
        }).setIcon(getResources().getDrawable(R.drawable.ic_notifications_active_black_24dp)).setCancelable(false).show();

    }
}
