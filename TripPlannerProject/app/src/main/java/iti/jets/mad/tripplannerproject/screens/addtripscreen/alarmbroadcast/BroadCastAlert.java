package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage.AlarmActivity;

public class BroadCastAlert extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1=new Intent(context, AlarmActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);


    }
}