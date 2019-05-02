package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage.AlarmActivity;

public class BroadCastAlert extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1=new Intent(context, AlarmActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = intent.getBundleExtra("trip");
        intent1.putExtra("trip",bundle);
        context.startActivity(intent1);


    }
}