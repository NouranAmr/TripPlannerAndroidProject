package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmbroadcast.AlertReciever;


public class AlarmActivity extends AppCompatActivity {

    private AlertDialog.Builder alertBuilder;
    private MediaPlayer player;
    AlarmPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        presenter=new AlarmPresenter(this);
        presenter.setData(getIntent());
        setFinishOnTouchOutside(false);

        player=MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        player.setLooping(true);
        player.setVolume(100.0f,100.0f);
        player.start();
        final int tripId=getIntent().getIntExtra("tripId",0);
        alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setTitle("Reminder")
                .setMessage("Do You Want To Start Your Trip ?")
                .setPositiveButton("Start",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        player.stop();
                        player.release();

                        //Start Trip   ---open google map
                        presenter.startTrip();
                        finish();
                    }
                }).setNeutralButton("Snooze", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player.stop();
                player.release();

                //Presenter   snooze trip

                presenter.snoozeTrip();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player.stop();
                player.release();

                cancelAlarm();
                //presenter  cancel trip

                finish();
            }
        }).setIcon(getResources().getDrawable(R.drawable.ic_notifications_active_black_24dp)).setCancelable(false).show();

    }
    private void cancelAlarm(){
        AlarmManager alarmManager =(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this, AlertReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.cancel(pendingIntent);
        Toast.makeText(AlarmActivity.this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }
}
