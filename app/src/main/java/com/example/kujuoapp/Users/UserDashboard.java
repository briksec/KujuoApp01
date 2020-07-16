package com.example.kujuoapp.Users;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import com.example.kujuoapp.Users.Feautures.QrSetter;
import com.example.kujuoapp.Users.Feautures.Transfer;
import com.example.kujuoapp.Users.Fragments.User_menu;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserDashboard extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_user_dashboard);

        stausbar();

        //size();

        bottomNavigation= findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.bottomicon));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.scanicon));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.personicon));


        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                if(model.getId()==1)
                {
                    setFragment(new User_menu());
                }
                else if (model.getId()==2)
                {
                    scanQRMethod();
                    // startActivity(new Intent(getApplicationContext(),QRCodeGenerator.class));
                     }
                return null;
            }
        });

        bottomNavigation.setCallListenerWhenIsSelected(true);
        bottomNavigation.show(1,true);
        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                if(model.getId()==1)
                {
                    setFragment(new User_menu());
                }
                else if (model.getId()==2)
                {
                    scanQRMethod();
                    // startActivity(new Intent(getApplicationContext(),QRCodeGenerator.class));
                }
                else if (model.getId()==3)
                {
                    scanQRMethod();
                    // startActivity(new Intent(getApplicationContext(),QRCodeGenerator.class));
                }                return null;
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

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.user_container, fragment, "Menu");
        ft.commit();
    }

    private void scanQRMethod() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

                IntentIntegrator intentIntegrator = new IntentIntegrator(UserDashboard.this);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.setPrompt("");
                intentIntegrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        BaseClass.progress(this);
        BaseClass.progressDialog.show();
        BaseClass.progressDialog.setCancelable(true);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (intentResult != null){

            if (intentResult.getContents() != null){
             //   Toasty.success(getApplicationContext(),intentResult.getContents().toString(),Toasty.LENGTH_LONG).show();
                Intent intent=new Intent(UserDashboard.this, QrSetter.class);
                intent.putExtra("rec_data",intentResult.getContents().toString());
                startActivity(intent);
                BaseClass.progressDialog.dismiss();
            }
            else {
                // Toasty.error(getApplicationContext(),"Error: Something went wrong!",Toasty.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}