package com.example.altaf.diary;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by HAWONG on 27-Aug-16.
 */
public class SnoozeAction extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("noti1","here");

        int unique_id=intent.getIntExtra("unique_id",0);
        String medicine=intent.getStringExtra("name");
        String time=intent.getStringExtra("time");

        NotificationManager manager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(unique_id);



        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent_new = new Intent(context.getApplicationContext(), SnoozeReceiver.class);
        intent_new.putExtra("name",medicine);
        intent_new.putExtra("time",time);
        intent_new.putExtra("unique_id",unique_id);

// Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        SettingsValues settingsValues=new SettingsValues(context);

        long alarmTime=calendar.getTimeInMillis()+settingsValues.getSnoozeTimeInMillis();

        alarmIntent = PendingIntent.getBroadcast(context, (-unique_id), intent_new, PendingIntent.FLAG_UPDATE_CURRENT);
// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.


        alarmMgr.set(AlarmManager.RTC_WAKEUP,alarmTime,alarmIntent);








    }
}
