package com.example.altaf.diary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class SnoozeTime extends AppCompatActivity {

    private static final String DATA_FILE="MY_DATA";

    RadioButton min5,min10,min15,min30,min45,min60;
    Button snooze_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences=getSharedPreferences(DATA_FILE,MODE_PRIVATE);
        int theme_id=sharedPreferences.getInt("theme_id",0);
        if(theme_id==1){
            super.setTheme(R.style.MyTheme1);
        }
        else if(theme_id==2){
            super.setTheme(R.style.MyTheme2);
        }
        else if(theme_id==3){
            super.setTheme(R.style.MyTheme3);
        }
        else if(theme_id==4){
            super.setTheme(R.style.MyTheme4);
        }
        else
        {
            super.setTheme(R.style.AppTheme_NoActionBar);
        }
        setContentView(R.layout.activity_snooze_time);


        min5=(RadioButton)findViewById(R.id.snooze_time1);
        min10=(RadioButton)findViewById(R.id.snooze_time2);
        min15=(RadioButton)findViewById(R.id.snooze_time3);
        min30=(RadioButton)findViewById(R.id.snooze_time4);
        min45=(RadioButton)findViewById(R.id.snooze_time5);
        min60=(RadioButton)findViewById(R.id.snooze_time6);

        snooze_button=(Button)findViewById(R.id.snooze_button);


        int snooze_time=sharedPreferences.getInt("snooze_time",5);
        if(snooze_time==5){  min5.setChecked(true);  }
        else if(snooze_time==10){min10.setChecked(true);}
        else if(snooze_time==15){min15.setChecked(true);}
        else if(snooze_time==30){min30.setChecked(true);}
        else if(snooze_time==45){min45.setChecked(true);}
        else if(snooze_time==60){min60.setChecked(true);}

        snooze_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int snooze_time=10;
                if(min5.isChecked()){snooze_time=5;}
                else if(min10.isChecked()){snooze_time=10;}
                else if(min15.isChecked()){snooze_time=15;}
                else if(min30.isChecked()){snooze_time=30;}
                else if(min45.isChecked()){snooze_time=45;}
                else if(min60.isChecked()){snooze_time=60;}


                SharedPreferences sharedPreferences=getSharedPreferences(DATA_FILE,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("snooze_time",snooze_time);
                editor.commit();


                Intent intent= new Intent();
                intent.putExtra("snooze_time",snooze_time);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
