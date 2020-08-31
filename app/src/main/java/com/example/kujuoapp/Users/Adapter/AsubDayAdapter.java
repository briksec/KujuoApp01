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
import com.example.kujuoapp.Users.DataClass.AllTrans;
import com.example.kujuoapp.Users.DataClass.DayData;

import java.util.ArrayList;
import java.util.List;

public class AsubDayAdapter extends RecyclerView.Adapter<AsubDayAdapter.MyViewHolder> {


    Context context;
    List<DayData> data;
    boolean seemore=false;
    public AsubDayAdapter(Context context, List<DayData> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.day_card,parent,false);

        return new AsubDayAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position)
    {

        Glide.with(context).load(data.get(position).getAsub_cover()).into(holder.cover);

        holder.amount.setText("GET: "+data.get(position).getAsub_amount()+"$");
        holder.tittle.setText(data.get(position).getAsub_name());
        holder.startdate.setText(data.get(position).getAsubStartDate());
        holder.enddate.setText(data.get(position).getAsubEnddate());
        holder.description.setText(data.get(position).getAsub_description());
        holder.totalmember.setText(data.get(position).getAsub_totalmembers());
        holder.leftmember.setText(data.get(position).getAsub_leftmember());

        holder.showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seemore==false)
                {
                    holder.description.setMaxLines(Integer.MAX_VALUE);//your TextView
                    holder.showmore.setText("Showless");
                    seemore=true;
                }
                else
                    {
                        holder.description.setMaxLines(1);//your TextView
                        holder.showmore.setText("Showmore...");
                        seemore=false;
                    }

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView cover;

        TextView amount,tittle,startdate,enddate,description,showmore,totalmember,leftmember;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cover =itemView.findViewById(R.id.asub_cover);
            amount =itemView.findViewById(R.id.asub_amount);
            tittle =itemView.findViewById(R.id.asub_tittle);
            startdate  =itemView.findViewById(R.id.asubStartDate);
            enddate   =itemView.findViewById(R.id.asub_endDate);
            description    =itemView.findViewById(R.id.asub_des);
            showmore  =itemView.findViewById(R.id.seamore);
            totalmember =itemView.findViewById(R.id.asubtotalmember);
            leftmember  =itemView.findViewById(R.id.asub_leftmember);
        }
    }

    public void filterList(ArrayList<DayData> filteredList) {

        data=new ArrayList<>();
        data.addAll(filteredList);

        notifyDataSetChanged();
    }
}
