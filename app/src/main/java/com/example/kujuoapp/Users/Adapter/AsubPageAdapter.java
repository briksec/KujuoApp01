package com.example.kujuoapp.Users.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.kujuoapp.Users.Feautures.AsubFragments.DayAsub;
import com.example.kujuoapp.Users.Feautures.AsubFragments.MonthlyAsub;
import com.example.kujuoapp.Users.Feautures.AsubFragments.WeeklyAsub;
import com.example.kujuoapp.Users.Fragments.Nis_record;
import com.example.kujuoapp.Users.Fragments.Rec_Money;
import com.example.kujuoapp.Users.Fragments.Send_Money;

public class AsubPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public AsubPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                 DayAsub tab1= new DayAsub();
                return tab1;
            case 1:
                WeeklyAsub tab2 = new WeeklyAsub();
                return tab2;
            case 2:
                MonthlyAsub tab3 = new MonthlyAsub();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}