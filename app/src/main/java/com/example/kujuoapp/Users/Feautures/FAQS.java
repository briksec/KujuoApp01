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

import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.Adapter.AllTransHistoryAdapter;
import com.example.kujuoapp.Users.Adapter.FaqAdapter;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.DataClass.Faq;

import java.util.ArrayList;
import java.util.List;

public class FAQS extends AppCompatActivity {

    RecyclerView recyclerView;

    List<Faq> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q_s);

        back();
        statusbar();
        recyclerView();
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
    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = FAQS.this.getWindow();

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

    private void recyclerView()
    {
        recyclerView=findViewById(R.id.ex_rec);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL,true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        data.add(new Faq("1","Where can i download the Kujuo mobile application?","IPhone users can download the Easypaisa applicationfrom the iOs App Store by searching for the application by  the name. Android users can do the same by going to the android Play Store in their mobile phones."));
        data.add(new Faq("1","What is an Kujuo mobile account?","IPhone users can download the Easypaisa applicationfrom the iOs App Store by searching for the application by  the name. Android users can do the same by going to the android Play Store in their mobile phones."));
        data.add(new Faq("1","Who can be an Kujuo App Mobile Account 5subscriber?","IPhone users can download the Easypaisa applicationfrom the iOs App Store by searching for the application by  the name. Android users can do the same by going to the android Play Store in their mobile phones."));
        data.add(new Faq("1","Question","Answes"));
        data.add(new Faq("1","Question","Answes"));

        FaqAdapter faqAdapter=new FaqAdapter(data,getApplicationContext());

        recyclerView.setAdapter(faqAdapter);
       /* if(BaseClass.isNetworkConnected(FAQS.this))
            fetchdata();
        else
            BaseClass.toast(FAQS.this,"Check Your Internet Connection");*/


    }
}