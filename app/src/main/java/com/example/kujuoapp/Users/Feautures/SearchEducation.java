package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.Adapter.PopUpAdapter;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.DataClass.PopupData;
import com.example.kujuoapp.Users.UserDashboard;

import java.util.ArrayList;
import java.util.List;

public class SearchEducation extends AppCompatActivity {

    Spinner instName;
    EditText rollNo,name,amount;
    Button pay;

    List<PopupData> popupData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_education);
        init();
        back();
        statusbar();
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
            Window window = SearchEducation.this.getWindow();

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

    private void init()
    {
        instName=findViewById(R.id.institute);
        rollNo=findViewById(R.id.rollno);
        name=findViewById(R.id.name);
        amount=findViewById(R.id.fees);
        pay=findViewById(R.id.voucher_pay);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                popupData=new ArrayList<>();

                if(instName.getSelectedItem()!=null && !rollNo.getText().toString().isEmpty()
                        && !name.getText().toString().isEmpty()
                        && !amount.getText().toString().isEmpty() )
                {
                    pwdpopup();

                }
                else
                {
                    BaseClass.toast(getApplicationContext(),"Fill All First");
                }

            }
        });

    }

    private void pwdpopup()
    {
        final Dialog dialog = new Dialog(SearchEducation.this);
        dialog.setContentView(R.layout.pwd_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        final EditText p1=dialog.findViewById(R.id.pin1);
        final EditText p2=dialog.findViewById(R.id.pin2);
        final EditText p3=dialog.findViewById(R.id.pin3);
        final EditText p4=dialog.findViewById(R.id.pin4);
        final EditText p5=dialog.findViewById(R.id.pin5);
        final EditText p6=dialog.findViewById(R.id.pin6);

        final Button proceed=dialog.findViewById(R.id.p_proceed);
        final Button cancel=dialog.findViewById(R.id.p_cancel);


        nextmove(p1,p2);
        nextmove(p2,p3);
        nextmove(p3,p4);
        nextmove(p4,p5);
        nextmove(p5,p6);


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String grt_pin=p1.getText().toString()+p2.getText().toString()+p3.getText().toString()+
                        p4.getText().toString()+p5.getText().toString()+p6.getText().toString();

                ///      BaseClass.toast(WalletToWallet.this,UserDashboard.user_password);
                if(grt_pin.equals(UserDashboard.user_password))
                {

                    SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    String userid=preferences1.getString("user_id", "");

                    if(BaseClass.isNetworkConnected(SearchEducation.this))
                    {
                        dialog.dismiss();

                        confirmationPopup();
                    }
                    else
                    {

                        dialog.dismiss();
                        BaseClass.toast(SearchEducation.this,"Check Your Internet Connection");
                    }


                }
                else
                {
                    Vibrator vibrator = (Vibrator) getSystemService(WalletToWallet.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(500);
                    }
                    YoYo.with(Techniques.Bounce)
                            .duration(1000)
                            .playOn(dialog.findViewById(R.id.layout));

                    p1.setText("");p2.setText("");p3.setText("");p4.setText("");p5.setText("");p6.setText("");

                    p1.requestFocus();

                    BaseClass.toast(SearchEducation.this,"Wrong Pin");
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void nextmove(final EditText e1,final EditText e2)
    {
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // specify length of your editext here to move on next edittext

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(e1.getText().toString().trim().length()==1){
                    e2.requestFocus();
                    e1.hashCode();
                }
            }
        });

    }

    private void confirmationPopup()
    {

        popupData.add(new PopupData("Bill Type","Fees"));
        popupData.add(new PopupData("Roll No",rollNo.getText().toString()));
        popupData.add(new PopupData("Institute Name :",instName.getSelectedItem().toString()));
        popupData.add(new PopupData("Student Name",name.getText().toString()));
        popupData.add(new PopupData("Payable Fees",amount.getText().toString()));

        final Dialog dialog=new Dialog(SearchEducation.this);
        dialog.setContentView(R.layout.bill_payment_popup);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final RecyclerView recyclerView=dialog.findViewById(R.id.payment_popup_rec);
        final TextView total=dialog.findViewById(R.id.totalamount);
        final ImageView cross=dialog.findViewById(R.id.cros);
        final Button pay=dialog.findViewById(R.id.payes);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(dialog.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        PopUpAdapter popUpAdapter=new PopUpAdapter(getApplicationContext(),popupData);

        recyclerView.setAdapter(popUpAdapter);

        total.setText(amount.getText().toString());

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseClass.toast(getApplicationContext(),"Done");
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();

    }


}