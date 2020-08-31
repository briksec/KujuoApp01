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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.DataClass.Faq;
import com.example.kujuoapp.Users.DataClass.FeautureData;
import com.example.kujuoapp.Users.Feautures.BalanceActivity;
import com.example.kujuoapp.Users.Feautures.BusTicket;
import com.example.kujuoapp.Users.Feautures.MobilePrepaid;
import com.example.kujuoapp.Users.Feautures.PayBill;
import com.example.kujuoapp.Users.Feautures.Topup2;
import com.example.kujuoapp.Users.Feautures.Transfer;
import com.example.kujuoapp.Users.Fragments.UserMenuExtend;
import com.example.kujuoapp.Users.QRCodeGenerator;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyviewHolder> {

    List<Faq> data;
    Context context;

    public FaqAdapter(List<Faq> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_card,parent,false);
        return new FaqAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, final int position) {

        holder.ans.setText(data.get(position).getAnswer());
        holder.question.setText(data.get(position).getQuestion());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.ans.getVisibility()==View.GONE) {
                    holder.arrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    holder.ans.setVisibility(View.VISIBLE);
                }
                else{
                    holder.arrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
                    holder.ans.setVisibility(View.GONE);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView arrow;
        TextView question,ans;
        LinearLayout layout;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            arrow=itemView.findViewById(R.id.arrow);
            question=itemView.findViewById(R.id.question);
            ans=itemView.findViewById(R.id.answer);
            layout=itemView.findViewById(R.id.layout);
        }
    }


}
