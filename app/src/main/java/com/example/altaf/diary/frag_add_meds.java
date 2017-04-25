package com.example.altaf.diary;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by HAWONG on 22-Aug-16.
 */
public class frag_add_meds extends Fragment {

    View rootView;

    boolean sunday = false, monday = false, tuesday = false, wednesday = false, thursday = false, friday = false, saturday = false;
    CheckedTextView check1, check2, check3, check4, check5, check6, check7;
    Button addTimeslot;
    Button addReminder;
    EditText med_name;


    DatabaseHelper databaseHelper = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState)

        databaseHelper = new DatabaseHelper(getActivity());

        rootView = inflater.inflate(R.layout.fragment_add_meds, container, false);
        med_name = (EditText) rootView.findViewById(R.id.med_name);
        check1 = (CheckedTextView) rootView.findViewById(R.id.check1);
        check2 = (CheckedTextView) rootView.findViewById(R.id.check2);
        check3 = (CheckedTextView) rootView.findViewById(R.id.check3);
        check4 = (CheckedTextView) rootView.findViewById(R.id.check4);
        check5 = (CheckedTextView) rootView.findViewById(R.id.check5);
        check6 = (CheckedTextView) rootView.findViewById(R.id.check6);
        check7 = (CheckedTextView) rootView.findViewById(R.id.check7);

        addTimeslot = (Button) rootView.findViewById(R.id.add_timeslot);
        addReminder = (Button) rootView.findViewById(R.id.add_reminder);

        createTimeslot();

        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check1.toggle();
                if (sunday) {
                    sunday = false;
                } else {
                    sunday = true;
                }
            }
        });
        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check2.toggle();
                if (monday) {
                    monday = false;
                } else {
                    monday = true;
                }
            }
        });
        check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check3.toggle();
                if (tuesday) {
                    tuesday = false;
                } else {
                    tuesday = true;
                }
            }
        });
        check4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check4.toggle();
                if (wednesday) {
                    wednesday = false;
                } else {
                    wednesday = true;
                }
            }
        });
        check5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check5.toggle();
                if (thursday) {
                    thursday = false;
                } else {
                    thursday = true;
                }
            }
        });
        check6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check6.toggle();
                if (friday) {
                    friday = false;
                } else {
                    friday = true;
                }
            }
        });
        check7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check7.toggle();
                if (saturday) {
                    saturday = false;
                } else {
                    saturday = true;
                }
            }
        });


        getRemindersFromDb();

        addTimeslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.time_layout);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                float scale = getResources().getDisplayMetrics().density;
                int dpAsPixels1 = (int) (2 * scale + 0.5f);

                layoutParams.setMargins(0, dpAsPixels1, 0, dpAsPixels1);

                final Button button = new Button(getContext());
                button.setLayoutParams(layoutParams);
                button.setBackgroundResource(R.drawable.timebutton);
                button.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_lock_idle_alarm, 0, 0, 0);


                int dpAsPixels = (int) (10 * scale + 0.5f);

                button.setPadding(dpAsPixels, 0, 0, 0);
               // button.setTextColor(ContextCompat.getColor(getContext(), R.color.colorMaterial));


                button.setTypeface(Typeface.DEFAULT_BOLD);
                button.setText("12:00 PM");

                TypedValue typedValue1 = new TypedValue();
                getActivity().getTheme().resolveAttribute(R.attr.colorMaterial, typedValue1, true);
                int color1 = typedValue1.data;

                button.setTextColor(color1);


                linearLayout.addView(button);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {



                                String aMpM = "AM";

                                String HourString=selectedHour+"";
                                String MinuteString=selectedMinute+"";

                              //  Log.d("hour",selectedHour+"");

                                if (selectedHour > 11) {
                                    aMpM = "PM";
                                }


                                if (selectedHour > 12) {
                                    selectedHour = selectedHour - 12;
                                }
                                ////////////////////////////////////////
                                if(selectedHour==0)
                                {
                                    selectedHour=12;
                                }

                                if(selectedHour<10)
                                {
                                    HourString="0"+selectedHour;
                                }
                                else
                                {
                                    HourString=selectedHour+"";
                                }
                                if(selectedMinute<10)
                                {
                                    MinuteString="0"+selectedMinute;
                                }






                               // Log.d("hour",selectedHour+"");
                  /////////////////////////////////////////////////////////////

                                button.setText(HourString + ":" + MinuteString + " " + aMpM);
                            }
                        }, hour, minute, false);//Yes 24 hour time
                        mTimePicker.setTitle("Select Medicine Intake Time");
                        mTimePicker.show();
                    }
                });


                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        linearLayout.removeView(button);


                        return true;
                    }
                });


            }
        });

        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sunday || monday || tuesday || wednesday || thursday || friday || saturday) {

                    String medicine = "N/A";
                    if (med_name.getText().length() != 0) {
                        medicine = med_name.getText().toString().trim().toUpperCase();
                    }

                  //  String daysCode = getMedDaysCode();
                    String days = getMedDays();
                    String timesCode = "";
                    String times = "";

                    LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.time_layout);

                    for (int i = 0; i < linearLayout.getChildCount(); i++) {

                        Button b = (Button) linearLayout.getChildAt(i);
                        times += b.getText().toString() + " ";
                        timesCode += b.getText().toString() + ";";
                    }

                    //SetReminder(medicine,days,timesCode.substring(0,timesCode.lastIndexOf(";")));
                   // long returnValue = addToDatabase(medicine, days, times.trim());
                    long returnValue = addToDatabase(medicine, days,timesCode.substring(0,timesCode.lastIndexOf(";")));



                    if (returnValue != -1) {
                        //  addMedicineCard(medicine, days, times);
                        SetReminder(medicine,days,timesCode.substring(0,timesCode.lastIndexOf(";")),returnValue);
                        Toast.makeText(getContext(), "Reminder added.", Toast.LENGTH_LONG).show();
                        RefreshUI();
                        getRemindersFromDb();
                    } else {
                        Toast.makeText(getContext(), "Unable to add reminder. Check if reminder for same medicine already present.", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getContext(), "Select at least one day to set reminder.", Toast.LENGTH_LONG).show();
                }

            }


        });


        return rootView;
    }

    public void createTimeslot() {
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.time_layout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels1 = (int) (2 * scale + 0.5f);

        layoutParams.setMargins(0, dpAsPixels1, 0, dpAsPixels1);

        final Button button = new Button(getContext());
        button.setLayoutParams(layoutParams);
        button.setBackgroundResource(R.drawable.timebutton);
        button.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_lock_idle_alarm, 0, 0, 0);


        int dpAsPixels = (int) (10 * scale + 0.5f);

        button.setPadding(dpAsPixels, 0, 0, 0);
       // button.setTextColor(R.attr.colorMaterial);
        //button.setfor
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setText("12:00 PM");


        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorMaterial, typedValue, true);
        int color = typedValue.data;

        button.setTextColor(color);

       // button.setTextColor(R.drawable.timebutton_textcolor);
        // button.setBackgroundResource(R.drawable.timebutton);
        linearLayout.addView(button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String aMpM = "AM";

                        String HourString=selectedHour+"";
                        String MinuteString=selectedMinute+"";

                        //  Log.d("hour",selectedHour+"");

                        if (selectedHour > 11) {
                            aMpM = "PM";
                        }


                        if (selectedHour > 12) {
                            selectedHour = selectedHour - 12;
                        }
                        ////////////////////////////////////////
                        if(selectedHour==0)
                        {
                            selectedHour=12;
                        }

                        if(selectedHour<10)
                        {
                            HourString="0"+selectedHour;
                        }
                        else
                        {
                            HourString=selectedHour+"";
                        }
                        if(selectedMinute<10)
                        {
                            MinuteString="0"+selectedMinute;
                        }



                        button.setText(HourString + ":" + MinuteString + " " + aMpM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Medicine Intake Time");
                mTimePicker.show();
            }
        });

    }

   /* public void addMedicineCard(String medicine_name,String days,String times)
    {
        final LinearLayout linearLayout=(LinearLayout)rootView.findViewById(R.id.card_layout) ;
        final View card=LayoutInflater.from(getContext()).inflate(R.layout.medicine_card,linearLayout,false);

        final TextView med_name=(TextView)card.findViewById(R.id.card_med_name);
        TextView med_days=(TextView)card.findViewById(R.id.card_med_days);
        TextView med_times=(TextView)card.findViewById(R.id.card_med_times);

        // Button deleteB=(Button)card.findViewById(R.id.card_delete);


        med_name.setText(medicine_name);
        med_times.setText(times);
        med_days.setText(days);*/


    /*   med_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){

                    if(motionEvent.getRawX()>=(med_name.getRight()-med_name.getCompoundDrawables()[2].getBounds().width())){
                        long returnValue=databaseHelper.deleteMedicine(med_name.getText().toString());
                        if(returnValue!=-1){
                            linearLayout.removeView(card);
                            Toast.makeText(getContext(),"Remider Deleted",Toast.LENGTH_SHORT).show();
                            /////////Write function to delete alarm
                        }
                        else{
                            Toast.makeText(getContext(),"Unable to delete reminder.",Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                return false;
            }
        });

        linearLayout.addView(card);

    }*/

    public long addToDatabase(String medicine_name, String daysCode, String times) {
        long returnValue = databaseHelper.addMedicine(medicine_name, daysCode, times);

        return returnValue;
    }

   /* public String getMedDaysCode() {
        String result = "";
        if (sunday) {
            result += 1;
        } else {
            result += 0;
        }
        if (monday) {
            result += 1;
        } else {
            result += 0;
        }
        if (tuesday) {
            result += 1;
        } else {
            result += 0;
        }
        if (wednesday) {
            result += 1;
        } else {
            result += 0;
        }
        if (thursday) {
            result += 1;
        } else {
            result += 0;
        }
        if (friday) {
            result += 1;
        } else {
            result += 0;
        }
        if (saturday) {
            result += 1;
        } else {
            result += 0;
        }

        return result;
    }*/

    public String getMedDays() {
        String result = "";
        if (sunday) {
            result += "Sunday ";
        }
        if (monday) {
            result += "Monday ";
        }
        if (tuesday) {
            result += "Tuesday ";
        }
        if (wednesday) {
            result += "Wednesday ";
        }
        if (thursday) {
            result += "Thursday ";
        }
        if (friday) {
            result += "Friday ";
        }
        if (saturday) {
            result += "Saturday ";
        }

        return result;
    }

    public void getRemindersFromDb() {

        ArrayList<Medicine> medicines = databaseHelper.getAllMeds();
        final LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.card_layout);
        linearLayout.removeAllViews();

        for (Medicine med : medicines) {


            final View card = LayoutInflater.from(getContext()).inflate(R.layout.medicine_card, linearLayout, false);

            final TextView med_name = (TextView) card.findViewById(R.id.card_med_name);
            TextView med_days = (TextView) card.findViewById(R.id.card_med_days);
            TextView med_times = (TextView) card.findViewById(R.id.card_med_times);

            // Button deleteB=(Button)card.findViewById(R.id.card_delete);


            med_name.setText(med.getMed_name());
            med_times.setText(med.getMed_times().replace(";"," "));
            med_days.setText(med.getMed_days());


            med_name.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                        if (motionEvent.getRawX() >= (med_name.getRight() - med_name.getCompoundDrawables()[2].getBounds().width())) {


                            new AlertDialog.Builder(getContext())
                                    .setTitle("Delete Reminder")
                                    .setMessage("Do you really want to delete reminder for medicine "+med_name.getText().toString()+"?")
                                    .setIcon(android.R.drawable.ic_delete)
                                    .setNegativeButton("No",null)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            long returnValue = databaseHelper.getRowIdFromName(med_name.getText().toString());
                                            if (returnValue != -1) {
                                                deleteReminder(med_name.getText().toString(),returnValue);
                                                databaseHelper.deleteMedicine(med_name.getText().toString());
                                                linearLayout.removeView(card);
                                                Toast.makeText(getContext(), "Remider Deleted", Toast.LENGTH_SHORT).show();
                                                /////////Write function to delete alarm
                                            } else {
                                                Toast.makeText(getContext(), "Unable to delete reminder.", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    }).show();



                           // long returnValue = databaseHelper.deleteMedicine(med_name.getText().toString());


                        }
                    }

                    return false;
                }
            });

            linearLayout.addView(card);


        }


    }

    /*
    public String getDaysFromCode(String code)
    {
        String result="";
        char[] chars=code.toCharArray();
        for(int i=0;i<chars.length;i++)
        {
            switch (i){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;

            }
        }
        return result;
    }
*/

    public int SetReminder(String medicine,String daysString,String timeString,long _id){
        int returnValue=-1;

        String[] days=daysString.split(" ");

        String[] times=timeString.split(";");






        for(int i=0;i<days.length;i++)//  for(String day:days)
        {
            for(int j=0;j<times.length;j++) //for(String time:times)
            {
                AlarmManager alarmMgr;
                PendingIntent alarmIntent;

                alarmMgr = (AlarmManager)getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getActivity().getApplicationContext(), AlarmReceiver.class);
                intent.putExtra("id",medicine+" "+days[i]+" "+times[j]);            // use same id while deleting
                intent.putExtra("name",medicine);
                intent.putExtra("time",times[j]);
                intent.putExtra("day",days[i]);
                Log.d("id",medicine+" "+days[i]+" "+times[j]);
                //final int _id=(int) System.currentTimeMillis();
                String id=_id+""+i+""+j;
                int uniqueID=Integer.parseInt(id);

                Log.d("$$",uniqueID +" ADDED "+i+" "+j);

                intent.putExtra("unique_id",uniqueID);

// Set the alarm to start at 8:30 a.m.
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.DAY_OF_WEEK,getDayOfWeek(days[i]));
                int[] TIME=getHourAndMinute(times[j]);
                calendar.set(Calendar.HOUR_OF_DAY, TIME[0]);
                calendar.set(Calendar.MINUTE, TIME[1]);
                calendar.set(Calendar.SECOND, 0);

                long alarmTime=calendar.getTimeInMillis();
                if(calendar.getTimeInMillis()<System.currentTimeMillis()){
                    alarmTime=calendar.getTimeInMillis()+alarmMgr.INTERVAL_DAY * 7; //if past time then schedule from next week
                }

                intent.putExtra("timeInMillis",alarmTime);

                alarmIntent = PendingIntent.getBroadcast(getActivity(), uniqueID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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



        return returnValue;
    }

    public void deleteReminder(String medicine,long _id){

        Medicine med=databaseHelper.getMedFromName(medicine);
        if(med!=null){

            String[] days=med.getMed_days().split(" ");

            String[] times=med.getMed_times().split(";");

            for(int i=0;i<days.length;i++)//  for(String day:days)
            {
                for(int j=0;j<times.length;j++) //for(String time:times)
                {
                    AlarmManager alarmMgr;
                    PendingIntent alarmIntent;

                    alarmMgr = (AlarmManager)getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getActivity().getApplicationContext(), AlarmReceiver.class);
                    intent.putExtra("id",med.getMed_name()+" "+days[i]+" "+times[j]);            // use same id while deleting
                    intent.putExtra("name",medicine);
                    intent.putExtra("time",times[j]);
                    Log.d("id",medicine+" "+days[i]+" "+times[j]);
                    String id=_id+""+i+""+j;
                    int uniqueID=Integer.parseInt(id);

                    Log.d("$$",uniqueID +" DELETED");

                    alarmIntent = PendingIntent.getBroadcast(getActivity(), uniqueID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmMgr.cancel(alarmIntent);
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

    public void RefreshUI(){
        med_name.setText("");
        med_name.clearFocus();

        if(sunday){sunday=false;check1.toggle();};
        if(monday){monday=false;check2.toggle();};
        if(tuesday){tuesday=false;check3.toggle();};
        if(wednesday){wednesday=false;check4.toggle();};
        if(thursday){thursday=false;check5.toggle();};
        if(friday){friday=false;check6.toggle();};
        if(saturday){saturday=false;check7.toggle();};


        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.time_layout);
        linearLayout.removeAllViews();
        createTimeslot();



    }

    int getThemeId() {
        try {
            Class<?> wrapper = Context.class;
            Method method = wrapper.getMethod("getThemeResId");
            method.setAccessible(true);
            return (Integer) method.invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
