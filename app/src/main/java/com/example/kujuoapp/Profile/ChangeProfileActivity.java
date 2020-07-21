package com.example.kujuoapp.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kujuoapp.R;

public class ChangeProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        getSupportActionBar().hide();
    }
}