package com.example.writeout;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MyAdapter3 extends FirebaseRecyclerAdapter<model, MyAdapter3.myviewholder> {


    public MyAdapter3(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    String currentUserName;

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {
        FirebaseDatabase database;
        FirebaseAuth mAuth;
        FirebaseUser firebaseUser;
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        holder.displaytitle.setText(model.getTitle());
        holder.displayauthor.setText(model.getAuthorName());
        holder.displaycategory.setText(model.getCategory());
        holder.displaydate.setText(model.getDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                Intent intent=new Intent(activity,FavDisplayActivity.class);
                intent.putExtra("Title3",model.getTitle());
                intent.putExtra("Category3",model.getCategory());
                intent.putExtra("Author3",model.getAuthorName());
                intent.putExtra("Date3",model.getDate());
                activity.startActivity(intent);

            }
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowdesign, parent, false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView displaytitle, displayauthor, displaycategory, displaydate;
        CardView cardView;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            displaytitle = itemView.findViewById(R.id.display_title);
            displayauthor = itemView.findViewById(R.id.display_author);
            displaycategory = itemView.findViewById(R.id.display_category);
            displaydate = itemView.findViewById(R.id.display_date);
            cardView=itemView.findViewById(R.id.cardview2);
        }
    }
}

