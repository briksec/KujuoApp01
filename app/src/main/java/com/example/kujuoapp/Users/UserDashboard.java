package com.example.kujuoapp.Users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.Adapter.FeautureAdapter;
import com.example.kujuoapp.Users.Adapter.PromoAdapter;
import com.example.kujuoapp.Users.DataClass.FeautureData;
import com.example.kujuoapp.Users.DataClass.PromoClass;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_user_dashboard);

        stausbar();
        inflateImageSlider();
        feauture();
        promos();
        size();

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.bottomicon));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.scanicon));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.personicon));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                if(model.getId()==1)
                {
                    BaseClass.toast(getApplicationContext(),"kjfk");
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                return null;
            }
        });
    }

    private void stausbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = UserDashboard.this.getWindow();

            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void size() {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;

        int height = displayMetrics.heightPixels;

        Toast.makeText(getApplicationContext(),height+"/n"+width+"",Toast.LENGTH_LONG).show();
    }

    private void inflateImageSlider() {

        // Using Image Slider -----------------------------------------------------------------------
         SliderLayout sliderShow = findViewById(R.id.slider);

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
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(s);
            sliderShow.addSlider(sliderView);
        }

        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

    }

    private void feauture() {
        RecyclerView recyclerView=findViewById(R.id.userrecycler);

        List<FeautureData> feautureData= new ArrayList<>();
        feautureData.add(new FeautureData("1","Top Up",R.drawable.purplecircle,R.drawable.replus));
        feautureData.add(new FeautureData("2","Transfer",R.drawable.yellowcircle,R.drawable.yellowicon));
        feautureData.add(new FeautureData("3","Wallet",R.drawable.orangecolor,R.drawable.orangeicon));
        feautureData.add(new FeautureData("4","Bill",R.drawable.yellowcircle,R.drawable.billicon));
        feautureData.add(new FeautureData("5","Mobile Prepaid",R.drawable.orangecolor,R.drawable.mblicon));
        GridLayoutManager gridLayout=new GridLayoutManager(UserDashboard.this,4);
        recyclerView.setLayoutManager(gridLayout);
        recyclerView.hasFixedSize();
        FeautureAdapter feautureAdapter=new FeautureAdapter(feautureData,getApplicationContext());
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
        RecyclerView recyclerView=findViewById(R.id.drpromo);

        List<PromoClass> data=new ArrayList<>();

        data.add(new PromoClass("1","Bonus CashBack","Get 10% Cashback\\nfor all transaction\\nwith Wallie ","https://i.ibb.co/MD60t7K/download-1.jpg"));
        data.add(new PromoClass("1","DailyDiscount CashBack","Get 10% Cashback\\nfor all transaction\\nwith Wallie ","https://i.ibb.co/MD60t7K/downd"));
        data.add(new PromoClass("1","Bonus CashBack","Get 10% Cashback\\nfor all transaction\\nwith Wallie ","https://i.ibb.co/k9K0XMd/download.jpg"));
        GridLayoutManager gridLayout=new GridLayoutManager(UserDashboard.this,2);
        recyclerView.setLayoutManager(gridLayout);

        PromoAdapter promoAdapter=new PromoAdapter(data,getApplicationContext());
        recyclerView.setAdapter(promoAdapter);
    }

}