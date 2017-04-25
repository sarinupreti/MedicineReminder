package com.example.altaf.diary;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by HAWONG on 27-Aug-16.
 */
public class SnoozeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("noti2","there");

        int unique_id=intent.getIntExtra("unique_id",0);
        String medicine=intent.getStringExtra("name");
        String time=intent.getStringExtra("time");



        // Uri soundURI= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        SettingsValues settingsValues=new SettingsValues(context);

        //////////////////////// SNOOZE PENDING INTENT ///////////////////////////////////////

        Intent switchIntent = new Intent(context, SnoozeAction.class);
        switchIntent.putExtra("unique_id",unique_id);// use same id while deleting
        switchIntent.putExtra("name",medicine);
        switchIntent.putExtra("time",time);


        PendingIntent pendingSnoozeIntent = PendingIntent.getBroadcast(context, (-unique_id),
                switchIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        /////////////////////////////////////////////////////////////////////////////////////


        NotificationCompat.Builder builder= new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("Medicine Reminder")
                .setContentText("It is time to take medicine")
                .setSound(settingsValues.getNotificationSound())
                .setVibrate(settingsValues.getNotificationVibrate())
                .setLights(Color.RED,2000,2000)
                .setColor(Color.argb(1,77,166,255))
                .addAction(android.R.drawable.ic_lock_idle_alarm,"Snooze",pendingSnoozeIntent);

        NotificationCompat.InboxStyle inboxStyle=new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Medicine Reminder");
        inboxStyle.addLine("Medicine Name: "+medicine);
        inboxStyle.addLine("Medicine Intake Time: "+time);

        builder.setStyle(inboxStyle);


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     /*  RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.notification_layout);

            remoteViews.setTextViewText(R.id.noti_medicine_text,medicine);
            remoteViews.setTextViewText(R.id.noti_time_text,time);


        builder.setContent(remoteViews);
*/
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        Intent resultIntent= new Intent(context,main.class);
        TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(main.class);
        taskStackBuilder.addNextIntent(resultIntent);

        PendingIntent pendingIntent=taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);




        notificationManager.notify(unique_id,builder.build());




    }
}
