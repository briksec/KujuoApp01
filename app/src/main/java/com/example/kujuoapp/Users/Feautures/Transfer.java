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
import com.example.kujuoapp.Users.Adapter.TransHistoryAdapter;
import com.example.kujuoapp.Users.DataClass.TransHistoryData;

import java.util.ArrayList;
import java.util.List;

public class Transfer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        statusbar();

        recyclerView();

        back();
    }

    private void recyclerView()
    {
        RecyclerView recyclerView=findViewById(R.id.transhistory);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<TransHistoryData> data=new ArrayList<>();
        data.add(new TransHistoryData("1","","Ali","****5454854","-332","fsd","-233"));
        data.add(new TransHistoryData("1","","Ali","****5454854","-332","fsd","-233"));
        data.add(new TransHistoryData("1","","Ali","****5454854","-332","fsd","-233"));

        TransHistoryAdapter adapter=new TransHistoryAdapter(this,data);

        recyclerView.setAdapter(adapter);


    }
    private void back() {
        ImageView back=findViewById(R.id.tback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = Transfer.this.getWindow();

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
}