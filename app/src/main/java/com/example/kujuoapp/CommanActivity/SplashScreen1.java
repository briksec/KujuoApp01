package com.example.kujuoapp.CommanActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.kujuoapp.R;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashScreen1 extends AppCompatActivity {


    public static final int RequestPermissionCode = 1;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
         handler = new Handler();
        if(checkPermission()) {
            handler();
            continued();
        }
        else
        {
            requestPermission();
        }
    }

    private void continued() {
        Button next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacksAndMessages(null);

                startActivity(new Intent(SplashScreen1.this, SplashScreen2.class));
                finish();
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(SplashScreen1.this, new
                        String[]{WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA ,READ_EXTERNAL_STORAGE,RECORD_AUDIO}
                , RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults ) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean read = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean rec = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission && read && rec) {
                        handler();
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        finish();
                        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplication(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplication(), READ_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getApplication(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
                && result3 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED;
    }

    public void handler()
    {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String check = preferences.getString("stdcheck", "");
                if (check.equals("1")) {
                    startActivity(new Intent(Splash.this, AskAQuestion.class));
                    finish();
                } else if (check.equals("2")) {
                    startActivity(new Intent(Splash.this, WaitingDashboard.class));
                    finish();
                } else {
                    startActivity(new Intent(Splash.this, Login.class));
                    finish();
                }
*/
                startActivity(new Intent(SplashScreen1.this, SplashScreen2.class));
                finish();
            }
        }, 3000);
    }

}