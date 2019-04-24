package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;


import iti.jets.mad.tripplannerproject.screens.addtripscreen.notification.NotificationHelper;

public class AlarmPresenter implements AlarmContract.IPresenter {

    private AlertDialog.Builder alertBuilder;
    private MediaPlayer player;
    AlarmActivity alarmActivity;

    public AlarmPresenter(AlarmActivity alarmActivity) {
        this.alarmActivity=alarmActivity;

    }

    @Override
    public void startAlarmRing() {

    }

    @Override
    public void startTrip() {

    }

    @Override
    public void snoozeTrip() {

        NotificationHelper notificationHelper=new NotificationHelper(alarmActivity.getApplicationContext());
        NotificationCompat.Builder nb=notificationHelper.getChannel1Notification("Your Trip Snoozed","Press To Restart The Trip..");
        notificationHelper.getManager().notify(1,nb.build());

    }



}
