package com.example.altaf.diary;

import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by HAWONG on 22-Aug-16.
 */
public class frag_settings extends Fragment {

    private static final String DATA_FILE="MY_DATA";


    ListView listView;
    String[] options={"Notification Tone","Theme","Snooze Time","About"};


    static final int TONE_REQUEST=1;
    static final int THEME_REQUEST=2;
    static final int SNOOZE_REQUEST=3;

    SettingsValues settingsValues=null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        settingsValues=new SettingsValues(getContext().getApplicationContext());

        View view=inflater.inflate(R.layout.fragment_settings,container,false);

        listView=(ListView)view.findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,options);

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=null;
                switch(i){
                    case 0:
                        SettingsValues settingsValues=new SettingsValues(getActivity().getApplicationContext());
                        intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION); //USE RingtoneManager.TYPE_ALARM for alarm
                        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, settingsValues.getNotificationSound());
                        startActivityForResult(intent, TONE_REQUEST);
                        break;
                    case 1:
                        intent=new Intent(getContext().getApplicationContext(),Theme.class);
                        startActivityForResult(intent,THEME_REQUEST);
                        break;
                    case 2:
                        intent=new Intent(getContext().getApplicationContext(),SnoozeTime.class);
                        startActivityForResult(intent,SNOOZE_REQUEST);
                        break;
                    case 3:
                        intent=new Intent(getContext().getApplicationContext(),About.class);
                        startActivityForResult(intent,SNOOZE_REQUEST);
                        break;

                }


            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==TONE_REQUEST){
            if(resultCode==getActivity().RESULT_OK){

                String NOTIFICATION_TONE_URI= data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI).toString();
                settingsValues.setNotificationSound(NOTIFICATION_TONE_URI);
                Toast.makeText(getActivity(),"Notification Tone Changed.",Toast.LENGTH_LONG).show();

            }
        }
        else if(requestCode==THEME_REQUEST){
            if(resultCode==getActivity().RESULT_OK){
                int theme_id=data.getIntExtra("theme_id",0);
                //  Toast.makeText(getActivity(),"Theme id"+theme_id,Toast.LENGTH_LONG).show();
                if(theme_id==1){
                    getActivity().setTheme(R.style.MyTheme1);
                }
                else if(theme_id==2){
                    getActivity().setTheme(R.style.MyTheme2);
                }
                else if(theme_id==3){
                    getActivity().setTheme(R.style.MyTheme3);
                }
                else if(theme_id==4){
                    getActivity().setTheme(R.style.MyTheme4);
                }
                else
                {
                    getActivity().setTheme(R.style.AppTheme_NoActionBar);
                }
                // super.setTheme(R.style.MyTheme4);
                // getActivity().setContentView(R.layout.activity_main);
                getActivity().recreate();
            }
        }
        else if(requestCode==SNOOZE_REQUEST){
            if(resultCode==getActivity().RESULT_OK){

                settingsValues.setSnoozeTimeInMillis(data.getIntExtra("snooze_time",5));
                Toast.makeText(getActivity(),"Snooze time set.",Toast.LENGTH_LONG).show();

            }

        }
    }

}
