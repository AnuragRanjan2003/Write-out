package com.example.writeout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.writeout.R;
import com.example.writeout.model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder> implements Filterable {
    Context context;
    ArrayList<model> list;
    ArrayList<model> completeList;

    public MyAdapter3(Context context, ArrayList<model> list) {
        this.context = context;
        this.list = list;
        completeList=new ArrayList<>();
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
                if(String.valueOf(task.getResult().getValue()).length()>=100)
                    holder.subArticle.setText(String.valueOf(task.getResult().getValue()).substring(0,100));
                else
                    holder.subArticle.setText(String.valueOf(task.getResult().getValue()));
            }
        });
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

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<model> filteredList=new ArrayList<>();
            String inputString=charSequence.toString().toLowerCase().trim();
            if(!inputString.isEmpty()) {
                for(model Model: completeList){
                    if(Model.getCategory().toLowerCase().trim().contains(inputString)){
                        filteredList.add(Model); }
                }

                if(filteredList.isEmpty())
                    Toast.makeText(context, "No Results found", Toast.LENGTH_SHORT).show();}
            else if (inputString.isEmpty())
                filteredList.addAll(completeList);

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((Collection<? extends model>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Category,Date,Author,subArticle;
        CardView CardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.display_title2);
            Category=itemView.findViewById(R.id.display_category2);
            Date=itemView.findViewById(R.id.display_date2);
            Author=itemView.findViewById(R.id.display_author2);
            CardView=itemView.findViewById(R.id.cardview2);
            subArticle=itemView.findViewById(R.id.sub_article);
        }
    }
}