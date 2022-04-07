package com.example.writeout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.writeout.R;
import com.example.writeout.model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    Context context;
    ArrayList<model> list;

    public MyAdapter2(Context context, ArrayList<model> list) {
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
        FirebaseDatabase.getInstance().getReference("post").child(model.getTitle()).child("article").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(String.valueOf((task.getResult().getValue())).length()>=100)
                holder.SubArticle.setText(String.valueOf(task.getResult().getValue()).substring(0,100));
                else
                    holder.SubArticle.setText(String.valueOf(task.getResult().getValue()));

            }
        });
        holder.CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, ArticleDisplayActivity.class);
                intent.putExtra("Title2", model.getTitle());
                intent.putExtra("Category2", model.getCategory());
                intent.putExtra("Date2", model.getDate());
                intent.putExtra("Author2", model.getAuthorName());
                activity.startActivity(intent);

            }
        });
        if(position%3==0)
            holder.CardView.setCardBackgroundColor(Color.rgb(247, 101, 89));
        else if(position%3==1)
            holder.CardView.setCardBackgroundColor(Color.rgb(100, 163, 250));
        else
            holder.CardView.setCardBackgroundColor(Color.rgb(146, 214, 146));



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Category,Date,Author,SubArticle;
        CardView CardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.display_title2);
            Category=itemView.findViewById(R.id.display_category2);
            Date=itemView.findViewById(R.id.display_date2);
            Author=itemView.findViewById(R.id.display_author2);
            CardView=itemView.findViewById(R.id.cardview2);
            SubArticle=itemView.findViewById(R.id.sub_article);

        }
    }
}