package com.example.altaf.diary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class Theme extends AppCompatActivity {

    private static final String DATA_FILE="MY_DATA";

    RelativeLayout relativeLayout1,relativeLayout2,relativeLayout3,relativeLayout4,relativeLayout5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme2);

        relativeLayout1=(RelativeLayout)findViewById(R.id.theme_selector_trans1);
        relativeLayout2=(RelativeLayout)findViewById(R.id.theme_selector_trans2);
        relativeLayout3=(RelativeLayout)findViewById(R.id.theme_selector_trans3);
        relativeLayout4=(RelativeLayout)findViewById(R.id.theme_selector_trans4);
        relativeLayout5=(RelativeLayout)findViewById(R.id.theme_selector_trans5);

        SharedPreferences sharedPreferences=getSharedPreferences(DATA_FILE,MODE_PRIVATE);
        int theme_id=sharedPreferences.getInt("theme_id",0);
        if(theme_id==1){
            relativeLayout2.setAlpha(0.5f);
        }
        else if(theme_id==2){
            relativeLayout3.setAlpha(0.5f);
        }
        else if(theme_id==3){
            relativeLayout4.setAlpha(0.5f);
        }
        else if(theme_id==4){
            relativeLayout5.setAlpha(0.5f);
        }
        else
        {
            relativeLayout1.setAlpha(0.5f);
        }


        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 flushUI();
                 relativeLayout1.setAlpha(0.5f);
                 setThemeId(0);
            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flushUI();
                relativeLayout2.setAlpha(0.5f);
                setThemeId(1);
            }
        });
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flushUI();
                relativeLayout2.setAlpha(0.5f);
                setThemeId(2);

            }
        });
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flushUI();
                relativeLayout4.setAlpha(0.5f);
                setThemeId(3);
            }
        });
        relativeLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flushUI();
                relativeLayout5.setAlpha(0.5f);
                setThemeId(4);
            }
        });



    }

    private void setThemeId(int themeId){
        SharedPreferences sharedPreferences=getSharedPreferences(DATA_FILE,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("theme_id",themeId);
        editor.commit();

        Intent intent=new Intent();
        intent.putExtra("theme_id",themeId);
        setResult(RESULT_OK,intent);
        finish();
    }
    private void flushUI(){
        relativeLayout1.setAlpha(0f);
        relativeLayout2.setAlpha(0f);
        relativeLayout3.setAlpha(0f);
        relativeLayout4.setAlpha(0f);
        relativeLayout5.setAlpha(0f);
    }
}
