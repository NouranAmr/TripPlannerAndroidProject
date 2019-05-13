package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Note;
import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.floatingView;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.notification.NotificationHelper;

public class AlarmPresenter implements AlarmContract.IPresenter {

    private AlertDialog.Builder alertBuilder;
    private MediaPlayer player;
    AlarmActivity alarmActivity;
    Intent intent;
    Trip myTrip;
    int tripId;

    public AlarmPresenter(AlarmActivity alarmActivity) {
        this.alarmActivity=alarmActivity;


    }
    public void setData(Intent intent){
        this.intent=intent;
        tripId = intent.getIntExtra("tripId",0);
    }

    @Override
    public void setTrip(Trip trip) {
        myTrip = trip;
    }

    @Override
    public void startAlarmRing() {

    }

    @Override
    public void startTrip() {
        Bundle bundle = intent.getBundleExtra("trip");
        Trip trip =bundle.getParcelable("tripBundle");
        LatLng from = new LatLng(trip.getStartLocation().getLat(),trip.getStartLocation().getLng());
        LatLng to = new LatLng(trip.getEndLocation().getLat(),trip.getEndLocation().getLng());
        // ArrayList<Note> notes = intent.getParcelableArrayListExtra("Nots");

        // from current location to
        //String uri = String.format(Locale.ENGLISH,  "google.navigation:q=%f,%f",from.latitude, from.longitude);

        //from loc1 to loc2
        String uri = "http://maps.google.com/maps?saddr=" + from.latitude + "," + from.longitude + "&daddr=" + to.latitude + "," + to.longitude;

        Intent intentFloating=new Intent(alarmActivity, floatingView.class);
        intentFloating.putExtra("URI",uri);
        intentFloating.putExtra("trip",bundle);
        alarmActivity.startActivity(intentFloating);
        //showMap(Uri.parse(uri));
        //Toast.makeText((Context) alarmActivity, "Started", Toast.LENGTH_LONG).show();
    }
   /* public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(alarmActivity.getPackageManager()) != null) {
            alarmActivity.startActivity(intent);
        }
    }*/

    @Override
    public void snoozeTrip() {

        Trip trip = intent.getBundleExtra("trip").getParcelable("tripBundle");
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(alarmActivity.getApplicationContext(),tripId,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationHelper notificationHelper = new NotificationHelper(alarmActivity.getApplicationContext());
        NotificationCompat.Builder builder = notificationHelper.getChannel1Notification(trip.getTripName()+" was Snoozed","Click here to restart.",pendingIntent,alarmSound);
        NotificationManager notificationManager = (NotificationManager)alarmActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(tripId,builder.build());

    }



}
