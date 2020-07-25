package com.example.kujuoapp.Users.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.Adapter.FeautureAdapter;
import com.example.kujuoapp.Users.Adapter.PromoAdapter;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.DataClass.FeautureData;
import com.example.kujuoapp.Users.DataClass.PromoClass;
import com.example.kujuoapp.Users.UserDashboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class User_menu extends Fragment {


    public User_menu() {
        // Required empty public constructor
    }


    View view;
    TextView user_name,walletid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_user, container, false);

        user_name=view.findViewById(R.id.dusename);
        walletid=view.findViewById(R.id.wallet_id);

        inflateImageSlider();

        feauture();

        promos();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                setname();            }
        }, 100);


        return view;
    }

    private void setname()
    {

        walletid.setText("Wallet ID : "+UserDashboard.wallet_id);

        user_name.setText(UserDashboard.user_name);

    }

    private void inflateImageSlider() {

        // Using Image Slider -----------------------------------------------------------------------
        SliderLayout sliderShow = view.findViewById(R.id.slider);

        //populating Image slider
        ArrayList<String> sliderImages = new ArrayList<>();
        sliderImages.add("https://i.ibb.co/k9K0XMd/download.jpg");
        sliderImages.add("https://i.ibb.co/k9K0XMd/download.jpg");
        sliderImages.add("https://i.ibb.co/MD60t7K/download-1.jpg");
        //  sliderImages.add("https://www.printstop.co.in/images/flashgallary/large/calendar-diaries-home-banner.jpg");
       /* sliderImages.add("https://www.printstop.co.in/images/flashgallary/large/calendar-diaries-banner.jpg");
        sliderImages.add("https://www.printstop.co.in/images/flashgallary/large/free-visiting-cards-home-banner.JPG");
*/
        for (String s : sliderImages) {
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            sliderView.image(s);
            sliderShow.addSlider(sliderView);
        }

        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

    }

    private void feauture() {
        RecyclerView recyclerView=view.findViewById(R.id.userrecycler);

        List<FeautureData> feautureData= new ArrayList<>();
        feautureData.add(new FeautureData("1","Top Up",R.drawable.purplecircle,R.drawable.replus));
        feautureData.add(new FeautureData("2","Transfer",R.drawable.yellowcircle,R.drawable.yellowicon));
        feautureData.add(new FeautureData("3","Wallet",R.drawable.orangecolor,R.drawable.orangeicon));
        feautureData.add(new FeautureData("4","Bill",R.drawable.yellowcircle,R.drawable.billicon));
        feautureData.add(new FeautureData("5","Mobile Prepaid",R.drawable.orangecolor,R.drawable.mblicon));
        feautureData.add(new FeautureData("6","QR Code",R.drawable.purplecircle,R.drawable.my_qr_code));

        GridLayoutManager gridLayout=new GridLayoutManager(getContext(),4);
        recyclerView.setLayoutManager(gridLayout);
        recyclerView.hasFixedSize();
        FeautureAdapter feautureAdapter=new FeautureAdapter(feautureData,getContext());
        recyclerView.setAdapter(feautureAdapter);

     /*   List<DashboardData> dashboardData=new ArrayList<>();

        dashboardData.add(new DashboardData("1","Cars","https://i.ibb.co/MD60t7K/download-1.jpg"));
       dashboardData.add(new DashboardData("1","Cloths","https://i.ibb.co/k9K0XMd/download.jpg"));
        GridLayoutManager gridLayout=new GridLayoutManager(UserDasboard.this,2);
        recyclerView.setLayoutManager(gridLayout);

        CategoryAdapter categoryAdapter= new CategoryAdapter(UserDasboard.this,dashboardData);
        recyclerView.setAdapter(categoryAdapter);*/
    }


    private void promos() {
        RecyclerView recyclerView=view.findViewById(R.id.drpromo);

        List<PromoClass> data=new ArrayList<>();

        data.add(new PromoClass("1","Bonus CashBack","Get 10% Cashback\\nfor all transaction\\nwith Wallie ","https://i.ibb.co/MD60t7K/download-1.jpg"));
        data.add(new PromoClass("1","DailyDiscount CashBack","Get 10% Cashback\\nfor all transaction\\nwith Wallie ",""));
        data.add(new PromoClass("1","Bonus CashBack","Get 10% Cashback\\nfor all transaction\\nwith Wallie ","https://i.ibb.co/k9K0XMd/download.jpg"));
        GridLayoutManager gridLayout=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayout);

        PromoAdapter promoAdapter=new PromoAdapter(data,getActivity());
        recyclerView.setAdapter(promoAdapter);
    }


}