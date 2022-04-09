package com.example.writeout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.example.writeout.R;
import com.example.writeout.YourArticleDisplayActivity;
import com.example.writeout.model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {
    Context context;
    ArrayList<model> list;
    ArrayList<model> completeList;

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
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase.getReference("post").child(model.getTitle()).child("article").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (String.valueOf(task.getResult().getValue()).length() >= 100)
                    holder.SubArticle.setText(String.valueOf(task.getResult().getValue()).substring(0, 100));
                else
                    holder.SubArticle.setText(String.valueOf(task.getResult().getValue()));
            }
        });


        if (position % 3 == 0)
            holder.cardView.setCardBackgroundColor(Color.rgb(247, 101, 89));
        else if (position % 3 == 1)
            holder.cardView.setCardBackgroundColor(Color.rgb(100, 163, 250));
        else
            holder.cardView.setCardBackgroundColor(Color.rgb(146, 214, 146));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            completeList=new ArrayList<>(list);
            String inputString=charSequence.toString().toLowerCase().trim();
            ArrayList<model> filteredList = new ArrayList<>();

             if(!inputString.isEmpty()) {
                for(model Model: completeList){
                    if(Model.getTitle().toLowerCase().trim().contains(inputString)){
                        filteredList.add(Model); }
                }

             if(filteredList.isEmpty())
                 Toast.makeText(context, "No Results found", Toast.LENGTH_SHORT).show();}
                else if (filteredList.isEmpty())
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
        TextView Title, Category, Date, SubArticle;
        CardView cardView;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);
            Title = itemview.findViewById(R.id.display_title);
            Category = itemview.findViewById(R.id.display_category);
            Date = itemview.findViewById(R.id.display_date);
            SubArticle = itemview.findViewById(R.id.sub_article2);
            cardView = itemview.findViewById(R.id.cardview1);
        }

    }
}