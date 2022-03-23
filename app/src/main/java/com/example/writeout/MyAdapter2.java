package com.example.writeout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MyAdapter2 extends FirebaseRecyclerAdapter<model, MyAdapter2.myviewholder> {


    public MyAdapter2(@NonNull FirebaseRecyclerOptions<model> options) {
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
        database.getReference("users").child(firebaseUser.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    currentUserName = String.valueOf(task.getResult().getValue());
                }
            }
        });
        holder.displaytitle.setText(model.getTitle());
        holder.displayauthor.setText(model.getAuthorName());
        holder.displaycategory.setText(model.getCategory());
        holder.displaydate.setText(model.getDate());


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowdesign, parent, false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView displaytitle, displayauthor, displaycategory, displaydate;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            displaytitle = itemView.findViewById(R.id.display_title);
            displayauthor = itemView.findViewById(R.id.display_author);
            displaycategory = itemView.findViewById(R.id.display_category);
            displaydate = itemView.findViewById(R.id.display_date);
        }
    }
}

