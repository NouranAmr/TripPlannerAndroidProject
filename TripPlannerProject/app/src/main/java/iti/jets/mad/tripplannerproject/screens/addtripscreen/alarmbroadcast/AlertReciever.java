package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
        Bundle bundle = intent.getBundleExtra("trip");
        //Trip trip =bundle.getParcelable("tripBundle");
        intent1.putExtra("trip",bundle);
        context.startActivity(intent1);


    }
}
