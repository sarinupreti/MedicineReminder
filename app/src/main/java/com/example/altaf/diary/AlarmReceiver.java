package com.example.altaf.diary;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by HAWONG on 24-Aug-16.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.d("here","here");
       // Toast.makeText(context,"Alarm",Toast.LENGTH_LONG).show();

        String medicine=intent.getStringExtra("name");
        String time=intent.getStringExtra("time");
        String day=intent.getStringExtra("day");
        int unique_id=intent.getIntExtra("unique_id",0);
        long timeInMillis=intent.getLongExtra("timeInMillis",0);

        Log.d("###",day+" "+time+" "+timeInMillis);

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

        // below api 19 it'll repeat automatically, so api>=19 we have to repeat manually
        if(Build.VERSION.SDK_INT>=19)
        {

            AlarmManager alarmMgr;
            PendingIntent alarmIntent;

            alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent_new = new Intent(context, AlarmReceiver.class);
            intent_new.putExtra("id",medicine+" "+day+" "+time);            // use same id while deleting
            intent_new.putExtra("name",medicine);
            intent_new.putExtra("time",time);
           // Log.d("exact id",medicine+" "+day+" "+time);
            //final int _id=(int) System.currentTimeMillis();
           // String id=_id+""+i+""+j;
          //  int uniqueID=Integer.parseInt(id);

           // Log.d("$$",uniqueID +" ADDED "+i+" "+j);

            intent_new.putExtra("unique_id",unique_id);

// Set the alarm to start at 8:30 a.m.
          /*  Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.DAY_OF_WEEK,getDayOfWeek(days[i]));
            int[] TIME=getHourAndMinute(times[j]);
            calendar.set(Calendar.HOUR_OF_DAY, TIME[0]);
            calendar.set(Calendar.MINUTE, TIME[1]);
            calendar.set(Calendar.SECOND, 0);
            */
            intent_new.putExtra("timeInMillis",timeInMillis+alarmMgr.INTERVAL_DAY*7);//timeInMillis+alarmMgr.INTERVAL_DAY*7
           // Log.d("exact id",timeInMillis+10*1000+"");

            alarmIntent = PendingIntent.getBroadcast(context, unique_id, intent_new, PendingIntent.FLAG_ONE_SHOT);

            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, timeInMillis+alarmMgr.INTERVAL_DAY*7, alarmIntent);  //timeInMillis+alarmMgr.INTERVAL_DAY*7

        }





    }
}
