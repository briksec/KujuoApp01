package com.example.kujuoapp.Profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agik.AGIKSwipeButton.Controller.OnSwipeCompleteListener;
import com.agik.AGIKSwipeButton.View.Swipe_Button_View;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.Feautures.BalanceActivity;
import com.example.kujuoapp.Users.Login;
import com.example.kujuoapp.Users.QRCodeGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {


    ImageButton change_prof_btn;
    static Context context;
    static TextView walletamt;
    static TextView pincode;
    static TextView email;
    static TextView topname;
    static TextView topnumber;
    static ImageView prof_pic;
    LinearLayout fingerprintline,QRCodeline,resetpinCode,setEmail,showBalance ;
    public static Fragment profFrag;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        profFrag = this;
        context = getContext();
        Swipe_Button_View swipe_button_view = view.findViewById(R.id.my_start_btn);
        fingerprintline = view.findViewById(R.id.work_fingerprint);
        QRCodeline = view.findViewById(R.id.work_qrcode);
        change_prof_btn = view.findViewById(R.id.chg_btn);
        walletamt = view.findViewById(R.id.u_wallamount);
        pincode = view.findViewById(R.id.u_pincode);
        email = view.findViewById(R.id.u_email);
        resetpinCode = view.findViewById(R.id.pin_code_set);
        topname = view.findViewById(R.id.top_name);
        topnumber = view.findViewById(R.id.top_number);
        showBalance = view.findViewById(R.id.to_balance);
        setEmail = view.findViewById(R.id.email_set);
        prof_pic = view.findViewById(R.id.prof_pic);
        swipe_button_view.setOnSwipeCompleteListener_forward_reverse(new OnSwipeCompleteListener() {
            @Override
            public void onSwipe_Forward(Swipe_Button_View swipe_button_view) {
                BaseClass.toast(getContext(),"done");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                preferences.edit().clear().apply();
                Intent intent = new Intent(getContext(),Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ((Activity)getContext()).finish();
            }

            @Override
            public void onSwipe_Reverse(Swipe_Button_View swipe_button_view) {

            }
        });

        changeProfile();
        validateStuff();
        changepinCode();
        changeEmail();
        showMyBalance();
        fetchUData();
       // topname.setText(UserDashboard.user_name);
      //  topnumber.setText(UserDashboard.user_phoneno);
        return view;
    }

    private void showMyBalance() {
    showBalance.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), BalanceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    });
    }

    private void changeEmail() {
        setEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EnterExistingPin.class);
                intent.putExtra("mode","change_email");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void changepinCode() {
        resetpinCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EnterExistingPin.class);
                intent.putExtra("mode","change_pin_code");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public static void fetchUData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"fetch_profile_data1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //progress_spinner.dismiss();
                       // Toast.makeText(getContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();
                        if(ServerResponse.trim().equals("0")){
                            Toast.makeText(context,"Not Found",Toast.LENGTH_SHORT).show();
                        }


                        try {
                            JSONArray jsonArray = new JSONArray(ServerResponse);
                            if (jsonArray.length() > 0) {
                                for (int j = 0; j < jsonArray.length(); j++) {

                                    JSONObject info = jsonArray.getJSONObject(j);
                                    email.setText(info.getString("user_email"));
                                    String pin = info.getString("user_password");

                                    pincode.setText(pin.substring(0,2)+"****");
                                    walletamt.setText(info.getString("user_wallet"));
                                    Glide.with(ProfileFragment.context)
                                            .load(info.getString("profile_pic"))
                                            .circleCrop()
                                            .placeholder(R.drawable.ic_loading)
                                            .into(prof_pic);
                                    topname.setText(info.getString("user_name"));
                                    topnumber.setText(info.getString("user_phoneno"));
                                    }

                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(context,"Check Your Internet Connection", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(context);

                String userid=preferences1.getString("user_id", "");
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", userid);


                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

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