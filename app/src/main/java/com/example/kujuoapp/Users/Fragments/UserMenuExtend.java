package com.example.kujuoapp.Users.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.Feautures.Asub;
import com.example.kujuoapp.Users.Feautures.EletricCity;
import com.example.kujuoapp.Users.Feautures.GasBill;
import com.example.kujuoapp.Users.Feautures.GetBitCoin;
import com.example.kujuoapp.Users.Feautures.HealthInsauranceActivity;
import com.example.kujuoapp.Users.Feautures.InternetBill;
import com.example.kujuoapp.Users.Feautures.LifeInsuranceActivity;
import com.example.kujuoapp.Users.Feautures.MobilePrepaid;
import com.example.kujuoapp.Users.Feautures.SearchEducation;
import com.example.kujuoapp.Users.Feautures.SearchFood;
import com.example.kujuoapp.Users.Feautures.Topup2;
import com.example.kujuoapp.Users.Feautures.WalletToCnic;
import com.example.kujuoapp.Users.Feautures.WalletToWallet;


public class UserMenuExtend extends Fragment {

    public UserMenuExtend() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_user_menu_extend, container, false);

        moneyTransfer();

        billpayment();

        easyload();

        foodordering();

        education();

        healthInsaurance();
        
        asub();

        bitcoin();
        return view;
    }

    private void bitcoin()
    {
        LinearLayout bitcoin=view.findViewById(R.id.m2bitcoin);

        bitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), GetBitCoin.class));

            }
        });
    }

    private void asub()
    {
        LinearLayout asub=view.findViewById(R.id.m2asub);

        asub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Asub.class));

            }
        });
    }

    private void healthInsaurance() {
        LinearLayout healtIns, lifeIns;

        healtIns = view.findViewById(R.id.m2healthinsaurance);
        lifeIns = view.findViewById(R.id.m2lifeinsaurance);

        healtIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HealthInsauranceActivity.class));
            }
        });

        lifeIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LifeInsuranceActivity.class));
            }
        });
    }

    private void moneyTransfer()
    {
        LinearLayout walletTowallet, walletToNis, walletTophoneno, walletToQr;

        walletTowallet=view.findViewById(R.id.m2walletToWallet);
        walletToNis=view.findViewById(R.id.m2walletToNis);
        walletTophoneno=view.findViewById(R.id.m2walletToMbl);
        walletToQr=view.findViewById(R.id.m2walletToQR);

        transferintent(walletTowallet,"ww");
        transferintent(walletTophoneno,"wp");
        transferintent(walletToQr,"wq");

        walletToNis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WalletToCnic.class));
            }
        });

    }

    public void transferintent(LinearLayout linearLayout, final String string)
    {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getContext(), WalletToWallet.class);
                i.putExtra("active",string);
                startActivity(i);
            }
        });
    }

    private void billpayment()
    {
        LinearLayout internet, electricity,gas;

        internet=view.findViewById(R.id.m2internet);
        electricity=view.findViewById(R.id.m2elec);
        gas=view.findViewById(R.id.m2gas);

        internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InternetBill.class));
            }
        });

        electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EletricCity.class));
            }
        });
        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), GasBill.class));
            }
        });
    }

    private void easyload()
    {
        LinearLayout btc,aliv;

        btc=view.findViewById(R.id.m2btc);

        aliv=view.findViewById(R.id.m2aliv);


        btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MobilePrepaid.class));

            }
        });
        aliv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MobilePrepaid.class));

            }
        });
    }


    private void foodordering()
    {
        LinearLayout food;
        food=view.findViewById(R.id.m2food);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchFood.class));

            }
        });
    }

    private void education()
    {
        LinearLayout edu;

        edu=view.findViewById(R.id.m2edu);

        edu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchEducation.class));

            }
        });
    }


}