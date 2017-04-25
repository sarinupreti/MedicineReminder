package com.example.altaf.diary;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {

    private static final String DATA_FILE="MY_DATA";

    TextView textView;

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

        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView=(TextView)findViewById(R.id.textView2);



        textView.setText("Version 1.0");

    }

}
