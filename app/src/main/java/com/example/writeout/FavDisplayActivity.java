package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class FavDisplayActivity extends AppCompatActivity {
    TextView title,category,date,author;
    EditText article;
    ExtendedFloatingActionButton unFav;
    String Title,Category,Author,Date;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_display);
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        title=findViewById(R.id.tv_title3);
        category=findViewById(R.id.tv_category3);
        author=findViewById(R.id.tv_author3);
        date=findViewById(R.id.tv_date3);
        article=findViewById(R.id.etarticle3);
        Intent intent=getIntent();
        Title=intent.getStringExtra("Title3");
        title.setText(Title);
        author.setText(intent.getStringExtra("Author3"));
        date.setText(intent.getStringExtra("Date3"));
        category.setText(intent.getStringExtra("Category3"));
        database.getReference("post").child(Title).child("article").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    article.setText(String.valueOf(task.getResult().getValue()));
                }

            }
        });


    }
}