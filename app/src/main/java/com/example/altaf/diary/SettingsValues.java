package com.example.altaf.diary;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by HAWONG on 25-Aug-16.
 */
public class SettingsValues {

    private static final String DATA_FILE="MY_DATA";

    Context context;

     private Uri NOTIFICATION_SOUND;//= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
     private long[] NOTIFICATION_VIBRATE;//=new long[]{Notification.DEFAULT_VIBRATE,Notification.DEFAULT_VIBRATE,Notification.DEFAULT_VIBRATE,Notification.DEFAULT_VIBRATE};
     private long SNOOZE_TIME_IN_MILLIS;//=10*1000;  //

   SettingsValues(Context context){
       this.context=context;
   }


    public  void setNotificationSound(String notificationSound) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(DATA_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("notification_tone",notificationSound);
        editor.commit();
    }

    public void setNotificationVibrate(long[] notificationVibrate) {
        NOTIFICATION_VIBRATE = notificationVibrate;
    }
    public void setSnoozeTimeInMillis(int snoozeTimeInMillis) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(DATA_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("snooze_time",snoozeTimeInMillis);
        editor.commit();

    }

    public Uri getNotificationSound() {
        //Default tone
        Uri uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        SharedPreferences sharedPreferences=context.getSharedPreferences(DATA_FILE,Context.MODE_PRIVATE);
        NOTIFICATION_SOUND=Uri.parse(sharedPreferences.getString("notification_tone",uri.toString()));
        return NOTIFICATION_SOUND;
    }

    public  long[] getNotificationVibrate() {
        NOTIFICATION_VIBRATE=new long[]{0,500,1000,1500};
        return NOTIFICATION_VIBRATE;
    }

    public  long getSnoozeTimeInMillis() {
        SharedPreferences sharedPreferences=context.getSharedPreferences(DATA_FILE,Context.MODE_PRIVATE);
        SNOOZE_TIME_IN_MILLIS=sharedPreferences.getInt("snooze_time",5);
        return SNOOZE_TIME_IN_MILLIS*60*1000;
    }


}
