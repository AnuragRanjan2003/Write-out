package com.example.writeout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.writeout.MyAdapter;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder>{
    Context context;
    ArrayList<CommentModel> arrayList;

    public RecAdapter(Context context, ArrayList<CommentModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new RecAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CommentModel commentModel=arrayList.get(position);
        holder.Sender.setText(commentModel.getSender());
        holder.Comment.setText(commentModel.getComment());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Sender,Comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Sender=itemView.findViewById(R.id.tv_sender);
            Comment=itemView.findViewById(R.id.tv_comment);
        }

    }

}