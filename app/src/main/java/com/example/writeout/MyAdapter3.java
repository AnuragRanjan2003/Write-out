package com.example.writeout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.writeout.R;
import com.example.writeout.model;

import java.util.ArrayList;
import java.util.Date;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder> {
    Context context;
    ArrayList<model> list;

    public MyAdapter3(Context context, ArrayList<model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rowdesign,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        model model = list.get(position);
        holder.Title.setText(model.getTitle());
        holder.Category.setText(model.getCategory());
        holder.Date.setText(model.getDate());
        holder.Author.setText(model.getAuthorName());
        holder.CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, FavDisplayActivity.class);
                intent.putExtra("Title3", model.getTitle());
                intent.putExtra("Category3", model.getCategory());
                intent.putExtra("Date3", model.getDate());
                intent.putExtra("Author3", model.getAuthorName());
                activity.startActivity(intent);

            }
        });
        if(position%4==0)
            holder.CardView.setCardBackgroundColor(Color.RED);
        else if(position%4==1)
            holder.CardView.setCardBackgroundColor(Color.BLUE);
        else if(position%4==2)
            holder.CardView.setCardBackgroundColor(Color.CYAN);
        else holder.CardView.setCardBackgroundColor(Color.GREEN);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Category,Date,Author;
        CardView CardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.display_title2);
            Category=itemView.findViewById(R.id.display_category2);
            Date=itemView.findViewById(R.id.display_date2);
            Author=itemView.findViewById(R.id.display_author2);
            CardView=itemView.findViewById(R.id.cardview2);
        }
    }
}