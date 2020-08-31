package com.example.kujuoapp.Users.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.DataClass.DayData;
import com.example.kujuoapp.Users.DataClass.PopupData;

import java.util.ArrayList;
import java.util.List;

public class PopUpAdapter extends RecyclerView.Adapter<PopUpAdapter.MyViewHolder> {


    Context context;
    List<PopupData> data;


    public PopUpAdapter(Context context, List<PopupData> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.default_popup_card,parent,false);

        return new PopUpAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position)
    {
        holder.headnig.setText(data.get(position).getHeading());
        holder.value.setText(data.get(position).getParagraph());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView headnig,value;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            headnig=itemView.findViewById(R.id.hcompaniName);
            value=itemView.findViewById(R.id.pcompanyname);
        }
    }

}
