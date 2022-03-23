package com.example.writeout;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyAdapter extends FirebaseRecyclerAdapter<model,MyAdapter.myviewholder> {

    public MyAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {
                holder.displaytitle.setText(model.getTitle());
                holder.displaycategory.setText(model.getCategory());
                holder.displaydate.setText(model.getDate());
                holder.displaytitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppCompatActivity activity=(AppCompatActivity)view.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.ViewPager,new yourArticleDisplayFragment(model.getTitle(),model.getCategory())).addToBackStack(null).commit();


                    }
                });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowdesignforself,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        TextView displaytitle,displaycategory,displaydate;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            displaytitle=itemView.findViewById(R.id.display_title);
            displaycategory=itemView.findViewById(R.id.display_category);
            displaydate=itemView.findViewById(R.id.display_date);
        }
    }
}
