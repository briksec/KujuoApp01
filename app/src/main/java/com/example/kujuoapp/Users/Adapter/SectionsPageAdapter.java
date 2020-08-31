package com.example.kujuoapp.Users.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.kujuoapp.Users.Fragments.Nis_record;
import com.example.kujuoapp.Users.Fragments.Rec_Money;
import com.example.kujuoapp.Users.Fragments.Send_Money;

public class SectionsPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SectionsPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                 Send_Money tab1= new Send_Money();
                return tab1;
            case 1:
                Rec_Money tab2 = new Rec_Money();
                return tab2;
            case 2:
                Nis_record tab3 = new Nis_record();
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