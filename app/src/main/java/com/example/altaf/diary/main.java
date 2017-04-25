package com.example.altaf.diary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class main extends AppCompatActivity {

    private static final String DATA_FILE="MY_DATA";


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

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
       // super.setTheme(R.style.MyTheme4);
        setContentView(R.layout.activity_main);



       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);







    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment=null;
            switch (position)
            {
                case 0:
                    fragment=new frag_add_meds();
                    break;
                case 1:
                    fragment=new frag_settings();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Medicines";
                case 1:
                    return "Settings";
            }
            return null;
        }
    }
}
