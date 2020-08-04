package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kujuoapp.R;

public class PromoExploreActivity extends AppCompatActivity {

    ImageView back,promoimage;
    TextView title_text,body_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_explore);
        back = findViewById(R.id.back_promo);
        promoimage = findViewById(R.id.img_promo);
        title_text = findViewById(R.id.title_promo);
        body_text = findViewById(R.id.content_promo);

        setControls();
    }

    private void setControls() {
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("image_p");
        String Title = intent.getStringExtra("title_p");
        String Content = intent.getStringExtra("content_p");

        Glide.with(getApplicationContext()).load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_loading)
                .into(promoimage);
        title_text.setText(Title);
        body_text.setText(Content);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}