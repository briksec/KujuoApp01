package com.example.kujuoapp.Users.Feautures;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.Adapter.TransHistoryAdapter;
import com.example.kujuoapp.Users.DataClass.TransHistoryData;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Transfer extends AppCompatActivity {

    ImageView scanQRCode;
    RelativeLayout walTowal, walTonic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        scanQRCode = findViewById(R.id.scanqrcode);
        walTowal=findViewById(R.id.walletToWallet);
        walTonic=findViewById(R.id.walletToCnic);


        walTowal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Transfer.this,WalletToWallet.class));
            }
        });
        walTonic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Transfer.this,WalletToCnic.class));
            }
        });
        statusbar();

        recyclerView();

        back();

        scanQRMethod();
    }


    private void scanQRMethod() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
        scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(Transfer.this);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.setPrompt("");
                intentIntegrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (intentResult != null){

            if (intentResult.getContents() != null){
                Toasty.success(getApplicationContext(),intentResult.getContents().toString(),Toasty.LENGTH_LONG).show();
            }else {
                // Toasty.error(getApplicationContext(),"Error: Something went wrong!",Toasty.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
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