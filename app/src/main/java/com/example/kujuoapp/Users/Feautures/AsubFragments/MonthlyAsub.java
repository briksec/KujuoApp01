package com.example.kujuoapp.Users.Feautures.AsubFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.Adapter.AsubDayAdapter;
import com.example.kujuoapp.Users.DataClass.DayData;
import com.example.kujuoapp.Users.Feautures.Asub;

import java.util.ArrayList;
import java.util.List;

public class MonthlyAsub extends Fragment {

    View view;
    List<DayData> dayData=new ArrayList<>();
    AsubDayAdapter asubDayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_monthly_asub, container, false);

        recyclerView();
        Asub.asub_serach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!(dayData.isEmpty())){
                    filter(s.toString());
                }
            }
        });

        return view;
    }

    private void recyclerView()
    {
        RecyclerView recyclerView=view.findViewById(R.id.mothly_rec);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String id,asub_name,asub_cover,asub_totalmembers,asub_leftmember,asubStartDate,asubEnddate,asub_description,asub_amount,asun_duration;

        dayData.add(new DayData("1","MY Asub","https://img.dunyanews.tv/news/2019/October/10-28-19/news_big_images/516136_42178052.jpg","50",
                "5","15-Aug-2020","30-Aug-2020",
                "Get 2000$ just pay daily \n 5$ in 15days \n thanks"
                ,"200","15 days"));

        dayData.add(new DayData("1","MY Asub","https://img.dunyanews.tv/news/2020/April/04-09-20/news_big_images/540375_73611990.jpg","50",
                "5","15-Aug-2020","30-Aug-2020",
                "Get 2000$ just pay daily \n 5$ in 15days \n thanks"
                ,"200","15 days"));

        asubDayAdapter=new AsubDayAdapter(getContext(),dayData);

        recyclerView.setAdapter(asubDayAdapter);


    }

    private void filter(String text) {

        if (text != "") {

            ArrayList<DayData> filteredList = new ArrayList<>();

            for (DayData item : dayData) {

                if (item.getAsub_name().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            if(filteredList !=null)
            {
                asubDayAdapter.filterList(filteredList);}
        }
        else{
            Toast.makeText(getContext(),"No Teacher",Toast.LENGTH_SHORT).show();
        }
    }
}