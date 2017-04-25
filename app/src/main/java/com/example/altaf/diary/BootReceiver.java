package com.example.altaf.diary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by HAWONG on 26-Aug-16.
 */

public class BootReceiver extends BroadcastReceiver {
    DatabaseHelper databaseHelper=null;
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {

        this.context=context;
        databaseHelper=new DatabaseHelper(context);

        ArrayList<Medicine> medicines=databaseHelper.getAllMeds();
        SetReminders(medicines);

    }

    public void SetReminders(ArrayList<Medicine> medicines){

        for(Medicine medicine:medicines)
        {
            String med_name=medicine.getMed_name();
            String med_days=medicine.getMed_days();
            String med_times=medicine.getMed_times();


            String[] separateDays=med_days.split(" ");
            String[] separateTimes=med_times.split(";");
            long _id=databaseHelper.getRowIdFromName(med_name);

            for(int i=0;i<separateDays.length;i++){
                for(int j=0;j<separateTimes.length;j++){

                    AlarmManager alarmMgr;
                    PendingIntent alarmIntent;

                    alarmMgr = (AlarmManager)context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
                    intent.putExtra("id",medicine+" "+separateDays[i]+" "+separateTimes[j]);            // use same id while deleting
                    intent.putExtra("name",med_name);
                    intent.putExtra("time",separateTimes[j]);
                    intent.putExtra("day",separateDays[i]);
                    Log.d("id",medicine+" "+separateDays[i]+" "+separateTimes[j]);
                    //final int _id=(int) System.currentTimeMillis();
                    String id=_id+""+i+""+j;
                    int uniqueID=Integer.parseInt(id);

                    Log.d("Broadcast",uniqueID +" ADDED "+i+" "+j);
                  //  Toast.makeText(context,uniqueID+" ADDED",Toast.LENGTH_SHORT).show();

                    intent.putExtra("unique_id",uniqueID);

// Set the alarm to start at 8:30 a.m.
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.DAY_OF_WEEK,getDayOfWeek(separateDays[i]));
                    int[] TIME=getHourAndMinute(separateTimes[j]);
                    calendar.set(Calendar.HOUR_OF_DAY, TIME[0]);
                    calendar.set(Calendar.MINUTE, TIME[1]);
                    calendar.set(Calendar.SECOND, 0);

                    long alarmTime=calendar.getTimeInMillis();
                    if(calendar.getTimeInMillis()<System.currentTimeMillis()){
                        alarmTime=calendar.getTimeInMillis()+alarmMgr.INTERVAL_DAY * 7; //if past time then schedule from next week
                    }

                    intent.putExtra("timeInMillis",alarmTime);

                    alarmIntent = PendingIntent.getBroadcast(context, uniqueID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.

                    if(Build.VERSION.SDK_INT<19) {
                        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime,
                                alarmMgr.INTERVAL_DAY * 7, alarmIntent);
                    }
                    else
                    {
                        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent);
                    }

                }

            }
        }

    }

    public int getDayOfWeek(String day)
    {
        if(day.equals("Sunday"))
            return Calendar.SUNDAY;
        else if(day.equals("Monday"))
            return Calendar.MONDAY;
        else if(day.equals("Tuesday"))
            return Calendar.TUESDAY;
        else if(day.equals("Wednesday"))
            return Calendar.WEDNESDAY;
        else if(day.equals("Thursday"))
            return Calendar.THURSDAY;
        else if(day.equals("Friday"))
            return Calendar.FRIDAY;
        else
            return Calendar.SATURDAY;
    }

    public int[] getHourAndMinute(String time)
    {
        int[] hourMinute=new int[2];

        String[]array=time.split(" ");
        String[] clock=array[0].split(":");

        int hour=Integer.parseInt(clock[0]);
        int minute=Integer.parseInt(clock[1]);

        if(array[1].equals("PM"))
        {
            if(hour<12)
                hour+=12;
        }
        else
        {
            if(hour==12)
            {
                hour=0;  //12 midnight
            }
        }

        hourMinute[0]=hour;
        hourMinute[1]=minute;

        Log.d("TimeCheck",time+" "+hour+" "+minute);

        return hourMinute;
    }

}
