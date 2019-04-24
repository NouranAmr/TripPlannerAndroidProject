package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage.AlarmActivity;


public class AlertReciever extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.i("broadcast","broadcast");
//        NotificationHelper notificationHelper=new NotificationHelper(context);
//        NotificationCompat.Builder nb=notificationHelper.getChannel1Notification("Alert","From Background");
//        notificationHelper.getManager().notify(1,nb.build());
//

        Intent intent1=new Intent(context, AlarmActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("fromLat",intent.getDoubleExtra("fromLat",0));
        intent1.putExtra("fromLng",intent.getDoubleExtra("fromLng",0));
        intent1.putExtra("toLat",intent.getDoubleExtra("toLat",0));
        intent1.putExtra("toLng",intent.getDoubleExtra("toLng",0));
        context.startActivity(intent1);


    }
}
