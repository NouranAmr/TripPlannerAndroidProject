package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage.AlarmActivity;


public class AlertReciever extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.i("broadcast","broadcast");
//        NotificationHelper notificationHelper=new NotificationHelper(context);
//        NotificationCompat.Builder nb=notificationHelper.getChannel1Notification("Alert","From Background");
//        notificationHelper.getManager().notify(1,nb.build());
//
//        Log.i("broadcast","after  broadcast");

        Intent intent1=new Intent(context, AlarmActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);


    }
}
