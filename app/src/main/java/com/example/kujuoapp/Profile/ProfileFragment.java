package com.example.kujuoapp.Profile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.agik.AGIKSwipeButton.Controller.OnSwipeCompleteListener;
import com.agik.AGIKSwipeButton.View.Swipe_Button_View;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.QRCodeGenerator;

public class ProfileFragment extends Fragment {


    ImageView change_prof_btn;

    LinearLayout fingerprintline,QRCodeline ;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        Swipe_Button_View swipe_button_view = view.findViewById(R.id.my_start_btn);
        fingerprintline = view.findViewById(R.id.work_fingerprint);
        QRCodeline = view.findViewById(R.id.work_qrcode);
        change_prof_btn = view.findViewById(R.id.chg_btn);
        swipe_button_view.setOnSwipeCompleteListener_forward_reverse(new OnSwipeCompleteListener() {
            @Override
            public void onSwipe_Forward(Swipe_Button_View swipe_button_view) {
                BaseClass.toast(getContext(),"done");
            }

            @Override
            public void onSwipe_Reverse(Swipe_Button_View swipe_button_view) {

            }
        });

        changeProfile();
        validateStuff();
        return view;
    }

    private void changeProfile() {
        change_prof_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(getContext(),ChangeProfileActivity.class));
            }
        });
    }


    private void validateStuff() {
        fingerprintline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Settings.ACTION_FINGERPRINT_ENROLL));
                }catch (Exception ex){
                    BaseClass.toast(getContext(),"Error : Something went wrong, Please use" +
                            " your device settings if it supports Fingerprint service to enable Fingerprint lock.");
                }
            }
        });

        QRCodeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), QRCodeGenerator.class));
            }
        });
    }


}