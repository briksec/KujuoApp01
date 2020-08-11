package com.example.kujuoapp.Users;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.kujuoapp.Profile.ProfileFragment;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.Adapter.FeautureAdapter;
import com.example.kujuoapp.Users.Adapter.PromoAdapter;
import com.example.kujuoapp.Users.DataClass.FeautureData;
import com.example.kujuoapp.Users.DataClass.PromoClass;
import com.example.kujuoapp.Users.Feautures.BalanceActivity;
import com.example.kujuoapp.Users.Feautures.QrSetter;
import com.example.kujuoapp.Users.Feautures.Transfer;
import com.example.kujuoapp.Users.Feautures.ViewAll;
import com.example.kujuoapp.Users.Fragments.UserMenuExtend;
import com.example.kujuoapp.Users.Fragments.User_menu;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserDashboard extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;

    DrawerLayout drawerLayout;
    ImageView menu;
    NavigationView navigationView;
    TextView nav_name,nav_walletid;
    ImageView nav_profile;
    View headerView;
    public static String reg_datetime,user_name,user_email,user_password,user_wallet,user_phoneno,wallet_id,profile_pic;

    boolean opendrawer=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_user_dashboard);



        //size();

        if(BaseClass.isNetworkConnected(UserDashboard.this)==true)
        {
            fetchdata();
        }
        else
            {
                BaseClass.toast(this,"Check Your Internet Connection");
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                user_name=preferences1.getString("user_name", "");
                user_email=preferences1.getString("user_email", "");
                user_password=preferences1.getString("user_password", "");
                user_wallet=preferences1.getString("user_wallet", "");
                reg_datetime=preferences1.getString("reg_datetime", "");
                user_phoneno=preferences1.getString("user_phoneno", "");
                wallet_id=preferences1.getString("wallet_id", "");
                profile_pic=preferences1.getString("profile_pic", "");
                nav_name.setText("User: "+user_name);
                nav_walletid.setText("Wallet ID :"+wallet_id);

                Glide.with(getApplicationContext()).load(profile_pic).circleCrop().into(nav_profile);
            }
        stausbar();
        init();
        bottomNavigations();
        onclick();
    }


    private void onclick()
    {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(opendrawer==false)
                {
                    drawerLayout.openDrawer(GravityCompat.START);
                    opendrawer=true;
                }
                else
                    {
                        drawerLayout.closeDrawers();
                        opendrawer=false;
                    }

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
                if (id==R.id.transhistory)
                {
                    startActivity(new Intent(UserDashboard.this, ViewAll.class));

                }
                else if(id==R.id.profile)
                {
                    setFragment(new ProfileFragment());
                    bottomNavigation.setCallListenerWhenIsSelected(true);
                    bottomNavigation.show(3,true);
                }
                else if(id==R.id.wallet)
                {
                    startActivity(new Intent(UserDashboard.this, BalanceActivity.class));

                }
                else if(id==R.id.logout)
                {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    preferences.edit().clear().apply();
                    Intent intent = new Intent(UserDashboard.this,Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

                //This is for closing the drawer after acting on it
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void init()
    {
        bottomNavigation= findViewById(R.id.bottomNavigation);
        drawerLayout=findViewById(R.id.drawer);
        menu=findViewById(R.id.menu);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        headerView = LayoutInflater.from(this).inflate(R.layout.nav_header, navigationView, false);
        navigationView.addHeaderView(headerView);
         nav_name = (TextView) headerView.findViewById(R.id.name);
         nav_walletid = (TextView) headerView.findViewById(R.id.wallet_id);
         nav_profile =  headerView.findViewById(R.id.profile);

    }

    private void bottomNavigations()
    {
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_apps_24));
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
                else if (model.getId()==3)
                {
                    setFragment(new ProfileFragment());
                    //scanQRMethod();
                    // startActivity(new Intent(getApplicationContext(),QRCodeGenerator.class));
                }
                return null;
            }
        });
        bottomNavigation.setCallListenerWhenIsSelected(true);
        bottomNavigation.show(1,true);
        setFragment(new User_menu());

    /*   bottomNavigation.setCallListenerWhenIsSelected(true);
       bottomNavigation.show(1,true);
       // setFragment(new User_menu());
        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                if(model.getId()==1)
                {
                    setFragment(new User_menu());
                    BaseClass.toast(getApplicationContext(),"kdjk");
                }
                else if (model.getId()==2)
                {
                    scanQRMethod();
                    // startActivity(new Intent(getApplicationContext(),QRCodeGenerator.class));
                }
                else if (model.getId()==3)
                {
                    setFragment(new ProfileFragment());
                    //scanQRMethod();
                    // startActivity(new Intent(getApplicationContext(),QRCodeGenerator.class));
                }                return null;
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void stausbar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        ft.addToBackStack(null);
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
                Toasty.success(getApplicationContext(),intentResult.getContents().toString(),Toasty.LENGTH_LONG).show();
                Intent intent=new Intent(UserDashboard.this, QrSetter.class);
                intent.putExtra("rec_data",intentResult.getContents().toString());
                startActivity(intent);
                BaseClass.progressDialog.dismiss();
            }
            else {
                BaseClass.progressDialog.dismiss();
                // Toasty.error(getApplicationContext(),"Error: Something went wrong!",Toasty.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void fetchdata() {
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"fetch_user_info.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //progress_spinner.dismiss();
              Toast.makeText(getApplicationContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();
                        if(ServerResponse.trim().equals("0")){
                            Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_SHORT).show();
                        }


                        try {
                            JSONArray jsonArray = new JSONArray(ServerResponse);
                            if (jsonArray.length() > 0) {
                                for (int j = 0; j < jsonArray.length(); j++) {

                                    JSONObject info = jsonArray.getJSONObject(j);
                                    user_name=info.getString("user_name");
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("user_name",info.getString("user_name"));
                                    editor.putString("user_email",info.getString("user_email"));
                                    editor.putString("user_password",info.getString("user_password"));
                                    editor.putString("user_wallet",info.getString("user_wallet"));
                                    editor.putString("reg_datetime",info.getString("reg_datetime"));
                                    editor.putString("user_phoneno",info.getString("user_phoneno"));
                                    editor.putString("wallet_id",info.getString("wallet_id"));
                                    editor.putString("profile_pic",info.getString("profile_pic"));

                                    // Toast.makeText(getApplicationContext(),user_name,Toast.LENGTH_SHORT).show();
                                    editor.apply();
                                    if (user_name != null)
                                    { SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


                                        user_name=preferences1.getString("user_name", "");
                                        user_email=preferences1.getString("user_email", "");
                                        user_password=preferences1.getString("user_password", "");
                                        user_wallet=preferences1.getString("user_wallet", "");
                                        reg_datetime=preferences1.getString("reg_datetime", "");
                                        user_phoneno=preferences1.getString("user_phoneno", "");
                                        wallet_id=preferences1.getString("wallet_id", "");
                                        profile_pic=preferences1.getString("profile_pic","");

                                        nav_name.setText("User: "+user_name);
                                        nav_walletid.setText("Wallet ID :"+wallet_id);

                                        Glide.with(getApplicationContext()).load(profile_pic).circleCrop().into(nav_profile);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();

                                    }


                                }// levelAdapter.notifyDataSetChanged();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(getApplicationContext(),"Check Your Internet Connection", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String userid=preferences1.getString("user_id", "");
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", userid);


                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }



    @Override
    public void onBackPressed() {
        Fragment home2 = getSupportFragmentManager().findFragmentByTag("Menu2");

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(home2 instanceof UserMenuExtend && home2.isVisible())
        {
            setFragment(new User_menu());
        }
        else {
            super.onBackPressed();
        }

    }


}