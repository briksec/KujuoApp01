package com.example.kujuoapp.Users.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.DataClass.TransHistoryData;
import com.example.kujuoapp.Users.DataClass.TransportData;
import com.example.kujuoapp.Users.Feautures.BusSeatReserved;
import com.example.kujuoapp.Users.Feautures.ViewAll;

import java.util.List;

public class TransportAdapter extends RecyclerView.Adapter<TransportAdapter.MyviewHolder>{

    Context context;
    List<TransportData> data;


    public TransportAdapter(Context context, List<TransportData> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transport_card,parent,false);
        return new TransportAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, final int position)
    {
        if(position%2==1)
        {
            holder.trans_amount.setTextColor(Color.BLUE);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFF1F0"));
        }
        Glide.with(context)
                .load(data.get(position).getTrans_image())
                .placeholder(R.drawable.cap_boy)
                .error(R.drawable.cap_boy)
                .centerCrop()
                .into(holder.trans_bg);

        holder.tranport_name.setText(data.get(position).getTrans_name());
        holder.description.setText(data.get(position).getTrans_description());
        holder.trans_amount.setText("BookNow $"+data.get(position).getTran_charges());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, BusSeatReserved.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("bg",data.get(position).getTrans_image());
                intent.putExtra("name",data.get(position).getTrans_name());
                context.startActivity(intent);

            }
        });

      /*  Glide.with(context)
                .load(data.get(position).getProfile())
                .placeholder(R.drawable.cap_boy)
                .error(R.drawable.cap_boy)
                .centerCrop()
                .into(holder.profile);
        holder.name.setText(data.get(position).getName());
        holder.trans_amount.setText("-$ "+data.get(position).getTrasaction());

       String lastFourDigits = "*****"+data.get(position).getContact().substring(data.get(position).getContact().length() - 4);

        holder.contact.setText(lastFourDigits);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ViewAll.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView trans_bg;
        TextView tranport_name,description, trans_amount;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            tranport_name=itemView.findViewById(R.id.transname);
            description=itemView.findViewById(R.id.transdes);
            trans_bg=itemView.findViewById(R.id.trans_bg);
            cardView=itemView.findViewById(R.id.transbody);
            trans_amount=itemView.findViewById(R.id.trans_amount);

        }
    }
}
