package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kujuoapp.R;

public class Full_trans_history extends AppCompatActivity
{

    ImageView sent_image,rec_image;
    LinearLayout sent_layout,rec_layout;
    TextView sent_txt,rec_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_trans_history);


    }


}