package com.example.kujuoapp.Users.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.DataClass.TransHistoryData;

import java.util.List;

public class TransHistoryAdapter extends RecyclerView.Adapter<TransHistoryAdapter.MyviewHolder>{

    Context context;
    List<TransHistoryData> data;


    public TransHistoryAdapter(Context context, List<TransHistoryData> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_trans_card,parent,false);
        return new TransHistoryAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position)
    {
        Glide.with(context)
                .load(data.get(position).getProfile())
                .placeholder(R.drawable.cap_boy)
                .error(R.drawable.cap_boy)
                .centerCrop()
                .into(holder.profile);

        holder.name.setText(data.get(position).getName());
        holder.trans_amount.setText(data.get(position).getTrasaction());
        holder.contact.setText(data.get(position).getContact());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView profile;
        TextView name,contact,trans_amount;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.transbody);
            profile=itemView.findViewById(R.id.trans_profile);
            name=itemView.findViewById(R.id.transname);
            contact=itemView.findViewById(R.id.transno);
            trans_amount=itemView.findViewById(R.id.trans_amount);
        }
    }
}
