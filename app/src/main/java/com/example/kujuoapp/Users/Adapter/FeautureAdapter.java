package com.example.kujuoapp.Users.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.DataClass.FeautureData;
import com.example.kujuoapp.Users.Feautures.BalanceActivity;
import com.example.kujuoapp.Users.Feautures.BusTicket;
import com.example.kujuoapp.Users.Feautures.Donation;
import com.example.kujuoapp.Users.Feautures.MobilePrepaid;
import com.example.kujuoapp.Users.Feautures.PayBill;
import com.example.kujuoapp.Users.Feautures.Topup;
import com.example.kujuoapp.Users.Feautures.Topup2;
import com.example.kujuoapp.Users.Feautures.Transfer;
import com.example.kujuoapp.Users.Feautures.WalletToWallet;
import com.example.kujuoapp.Users.Fragments.UserMenuExtend;
import com.example.kujuoapp.Users.QRCodeGenerator;

import java.util.List;

public class FeautureAdapter extends RecyclerView.Adapter<FeautureAdapter.MyviewHolder> {

    List<FeautureData> feautureDataList;
    Context context;

    public FeautureAdapter(List<FeautureData> feautureDataList, Context context) {
        this.feautureDataList = feautureDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feauture_item_card,parent,false);
        return new FeautureAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, final int position) {
        holder.text.setText(feautureDataList.get(position).getText());
        holder.logo_bg.setBackgroundResource(feautureDataList.get(position).getBgimage());
        holder.logo.setBackgroundResource(feautureDataList.get(position).getLogo());

        itemclick(holder,position);

    }

    private void itemclick(MyviewHolder holder,final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(feautureDataList.get(position).getId()=="1")
                {
                    Intent intent=new Intent(context, Topup2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
                else if(feautureDataList.get(position).getId()=="2")
                {
                    Intent intent=new Intent(context, Transfer.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if(feautureDataList.get(position).getId()=="3")
                {
                    Intent intent=new Intent(context, BalanceActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if(feautureDataList.get(position).getId()=="4")
                {
                    Intent intent=new Intent(context, PayBill.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else if(feautureDataList.get(position).getId()=="5")
                {
                    Intent intent=new Intent(context, MobilePrepaid.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(feautureDataList.get(position).getId()=="6")
                {
                    Intent intent=new Intent(context, QRCodeGenerator.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(feautureDataList.get(position).getId()=="7")
                {
                    Intent intent=new Intent(context, BusTicket.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(feautureDataList.get(position).getId()=="8")
                {
                    /*Intent intent=new Intent(context, Donation.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);*/
                    setFragment(new UserMenuExtend());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return feautureDataList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        LinearLayout logo_bg;
        ImageView logo;
        TextView text;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            logo_bg=itemView.findViewById(R.id.fbackground);
            logo=itemView.findViewById(R.id.flogo);
            text=itemView.findViewById(R.id.fname);
        }
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction ft =((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.user_container, fragment, "Menu2");
        ft.commit();
    }
}
