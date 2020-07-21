package com.example.kujuoapp.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kujuoapp.R;

public class ChangeProfileActivity extends AppCompatActivity {

    ImageView arrowright,arrowbottom;
    EditText phoneEdit, nameEdit;
    TextView usersetdname,usersetdphone;
    ExpandGetterSetter eGSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        getSupportActionBar().hide();
        arrowright = findViewById(R.id.fname_arrow);
        arrowbottom = findViewById(R.id.phno_arrow);
        usersetdname = findViewById(R.id.my_username);
        usersetdphone = findViewById(R.id.my_userphone);
        phoneEdit = findViewById(R.id.ph_edit);
        nameEdit = findViewById(R.id.fullname_edit);
        eGSetter = new ExpandGetterSetter();
        eGSetter.setCheckname(true);
        eGSetter.setCheckphone(true);
        arrowright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (eGSetter.isCheckname()){
                    arrowright.setBackgroundResource(R.drawable.ic_arrow_down);
                    usersetdname.setVisibility(View.GONE);
                    nameEdit.setVisibility(View.VISIBLE);
                    eGSetter.setCheckname(false);
                }else {
                    arrowright.setBackgroundResource(R.drawable.ic_right_arrow);
                    usersetdname.setVisibility(View.VISIBLE);
                    nameEdit.setVisibility(View.GONE);
                    eGSetter.setCheckname(true);
                }

            }
        });

        arrowbottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eGSetter.isCheckphone()){
                    arrowbottom.setBackgroundResource(R.drawable.ic_arrow_down);
                    usersetdphone.setVisibility(View.GONE);
                    phoneEdit.setVisibility(View.VISIBLE);
                    eGSetter.setCheckphone(false);
                }else {
                    arrowbottom.setBackgroundResource(R.drawable.ic_right_arrow);
                    usersetdphone.setVisibility(View.VISIBLE);
                    phoneEdit.setVisibility(View.GONE);
                    eGSetter.setCheckphone(true);
                }

            }
        });
    }
}