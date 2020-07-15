package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.UserDashboard;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Topup extends AppCompatActivity {

    IndicatorSeekBar indicatorSeekBar;
    Button b1,b2,b3,b4;
    EditText value;

    LinearLayout friendsacc,bankacc,cnicacc;
    //h=hide
    LinearLayout hfriendsacc,hbankacc,hcnicacc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);

        init();

        statusbar();

        seekerbar();

        back();


    }

    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = Topup.this.getWindow();

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

    private void back() {
        ImageView back=findViewById(R.id.tback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void init()
    {
        value=findViewById(R.id.tvalue);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        b4=findViewById(R.id.b4);
        friendsacc=findViewById(R.id.tfriends);
        bankacc=findViewById(R.id.tbanlacc);
        cnicacc=findViewById(R.id.cnic);

        hbankacc=findViewById(R.id.hidbank);
        hcnicacc=findViewById(R.id.hidecnic);
        hfriendsacc=findViewById(R.id.hidefriend);

        btn_event_listner(b1);
        btn_event_listner(b2);
        btn_event_listner(b3);
        btn_event_listner(b4);

        layoutsHideAndShow();

    }

    private void layoutsHideAndShow()
    {
        friendsacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hfriendsacc.getVisibility() ==View.GONE)
                {
                    hbankacc.setVisibility(View.GONE);
                    hcnicacc.setVisibility(View.GONE);
                    hfriendsacc.setVisibility(View.VISIBLE);
                }
                else
                {
                    hfriendsacc.setVisibility(View.GONE);
                }
            }
        });
        bankacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hbankacc.getVisibility() ==View.GONE)
                {
                    hfriendsacc.setVisibility(View.GONE);
                    hcnicacc.setVisibility(View.GONE);
                    hbankacc.setVisibility(View.VISIBLE);
                }
                else
                {
                    hbankacc.setVisibility(View.GONE);
                }
            }
        });

        cnicacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hcnicacc.getVisibility() ==View.GONE)
                {
                    hfriendsacc.setVisibility(View.GONE);
                    hbankacc.setVisibility(View.GONE);
                    hcnicacc.setVisibility(View.VISIBLE);
                }
                else
                {
                    hcnicacc.setVisibility(View.GONE);
                }
            }
        });
    }

    private void btn_event_listner(final Button button)
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value.setText(button.getText().toString());
            }
        });
    }

    private void seekerbar() {
        indicatorSeekBar=findViewById(R.id.custom_text);

        indicatorSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                /*states.setText("states: onSeeking");
                progress.setText("progress: " + seekParams.progress);
                progress_float.setText("progress_float: " + seekParams.progressFloat);
                from_user.setText("from_user: " + seekParams.fromUser);
                thumb_position.setText("thumb_position: " + seekParams.thumbPosition);
                tick_text.setText("tick_text: " + seekParams.tickText);*/
              //  BaseClass.toast(Topup.this,seekParams.progress+"");
                value.setText(seekParams.progress+"");
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
                /*states.setText("states: onStart");
                progress.setText("progress: " + seekBar.getProgress());
                progress_float.setText("progress_float: " + seekBar.getProgressFloat());
                from_user.setText("from_user: true");*/
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                /*states.setText("states: onStop");
                progress.setText("progress: " + seekBar.getProgress());
                progress_float.setText("progress_float: " + seekBar.getProgressFloat());
                from_user.setText("from_user: false");*/
            }
        });
    }


}