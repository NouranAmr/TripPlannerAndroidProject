package iti.jets.mad.tripplannerproject.screens.addtripscreen.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage.AlarmActivity;

public class NotificationHelper extends ContextWrapper {
    public static final String chanel1ID="Channel 1";
    public static final String chanel1Name="Channel 1";
    private Context context;

    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        this.context=base;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannels();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel channel=new NotificationChannel(chanel1ID,chanel1Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);



    }
    public NotificationManager getManager(){
        if(mManager == null){
            mManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }


    public NotificationCompat.Builder getChannel1Notification (String title , String body){
        Intent intent=new Intent(context, AlarmActivity.class);
        long [] pattern={500 , 500};
        return new NotificationCompat.Builder(getApplicationContext(),chanel1ID)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(false)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setLights(Color.BLUE,500,500)
                .setVibrate(pattern)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentIntent(PendingIntent.getActivity(context,1,intent,PendingIntent.FLAG_CANCEL_CURRENT));
    }

}
