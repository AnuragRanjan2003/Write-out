package com.example.writeout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.example.writeout.R;
import com.example.writeout.YourArticleDisplayActivity;
import com.example.writeout.model;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<model> list;

    public MyAdapter(Context context, ArrayList<model> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowdesignforself, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        model model = list.get(position);
        holder.Title.setText(model.getTitle());
        holder.Category.setText(model.getCategory());
        holder.Date.setText(model.getDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, YourArticleDisplayActivity.class);
                intent.putExtra("title", model.getTitle());
                intent.putExtra("category", model.getCategory());
                intent.putExtra("date", model.getDate());
                intent.putExtra("author", model.getAuthorName());
                activity.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title, Category, Date;
        CardView cardView;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);
            Title = itemview.findViewById(R.id.display_title);
            Category = itemview.findViewById(R.id.display_category);
            Date = itemview.findViewById(R.id.display_date);
            cardView = itemview.findViewById(R.id.cardview1);
        }

    }
}