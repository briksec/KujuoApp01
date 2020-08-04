package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.Adapter.TransportAdapter;
import com.example.kujuoapp.Users.DataClass.TransportData;

import java.util.ArrayList;
import java.util.List;

public class BusTicket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_ticket);
        statusbar();
        init();
        back();
    }
    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = BusTicket.this.getWindow();

            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);

        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void init()
    {
        RecyclerView recyclerView;
        List<TransportData> transportData;

        TransportAdapter transportAdapter;

        LinearLayoutManager linearLayoutManager;


        recyclerView=findViewById(R.id.travel_book);


        linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        transportData=new ArrayList<>();

        transportData.add(new TransportData("1","http://app.briksec.com/Kujuo_App/api/images/bus1.jpg","Baggy Rocks","Baggy Rocks Nassau Bahamas Transportation","Mon","50"));
        transportData.add(new TransportData("1","http://app.briksec.com/Kujuo_App/api/images/bus2.jpg","Baggy Rocks","Baggy Rocks Nassau Bahamas Transportation","Mon","50"));
        transportData.add(new TransportData("1","http://app.briksec.com/Kujuo_App/api/images/bus1.jpg","Baggy Rocks","Baggy Rocks Nassau Bahamas Transportation","Mon","50"));
        transportData.add(new TransportData("1","http://app.briksec.com/Kujuo_App/api/images/bus2.jpg","Baggy Rocks","Baggy Rocks Nassau Bahamas Transportation","Mon","50"));
        transportData.add(new TransportData("1","http://app.briksec.com/Kujuo_App/api/images/bus1.jpg","Baggy Rocks","Baggy Rocks Nassau Bahamas Transportation","Mon","50"));
        transportData.add(new TransportData("1","http://app.briksec.com/Kujuo_App/api/images/bus2.jpg","Baggy Rocks","Baggy Rocks Nassau Bahamas Transportation","Mon","50"));

        transportAdapter=new TransportAdapter(getApplicationContext(),transportData);
        recyclerView.setAdapter(transportAdapter);



    }
    private void back()
    {
        ImageView imageView=findViewById(R.id.tback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}