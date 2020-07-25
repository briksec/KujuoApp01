package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;

public class ChangePinCodeActivity extends AppCompatActivity {

    ImageView back;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin_code);
        getSupportActionBar().hide();
        back = findViewById(R.id.cp_backzcp);
        save = findViewById(R.id.btn_save_changepincode);
        initUI();
    }

    private void initUI() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseClass.toast(getApplicationContext(),"Working!");
            }
        });
    }
}