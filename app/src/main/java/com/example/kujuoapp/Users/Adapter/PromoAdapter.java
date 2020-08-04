package com.example.kujuoapp.Users.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.DataClass.PromoClass;
import com.example.kujuoapp.Users.Feautures.PromoExploreActivity;

import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.MyviewHolder> {

    List<PromoClass> data;
    Context context;

    public PromoAdapter(List<PromoClass> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_item_card,parent,false);
        return new PromoAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, final int position) {
        Glide.with(context)
                .load(data.get(position).getImage())
                .placeholder(R.drawable.promo_img)
                .error(R.drawable.promo_img)
                .centerCrop()
                .into(holder.imageView);
        holder.title.setText(data.get(position).getTitle());
        holder.content.setText(data.get(position).getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image_url = data.get(position).getImage();
                String title = data.get(position).getTitle();
                String content = data.get(position).getContent();

                Intent intent = new Intent(context, PromoExploreActivity.class);
                intent.putExtra("image_p",image_url);
                intent.putExtra("title_p",title);
                intent.putExtra("content_p",content);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((Activity)context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, content;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.pimage);
            title=itemView.findViewById(R.id.ptitle);
            content=itemView.findViewById(R.id.pcontent);
        }
    }
}
