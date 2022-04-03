package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class ArticleDisplayActivity extends AppCompatActivity {
    TextView Title, Category, Date, Author;
    TextView Article;
    ExtendedFloatingActionButton favFab;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String stringTitle, stringArticle, stringCategory, stringAuthor, stringDate;
    post post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_display);
        Intent intent1 = getIntent();
        Intent intent2 = getIntent();
        Intent intent4 = getIntent();
        Intent intent5 = getIntent();
        Title = findViewById(R.id.tv_title2);
        Category = findViewById(R.id.tv_category2);
        Author = findViewById(R.id.tv_author);
        Date = findViewById(R.id.tv_date2);
        Article = findViewById(R.id.etarticle2);
        favFab = findViewById(R.id.favfab2);
        stringTitle = intent1.getStringExtra("Title2");
        stringAuthor = intent2.getStringExtra("Author2");
        stringCategory = intent4.getStringExtra("Category2");
        stringDate = intent5.getStringExtra("Date2");
        Title.setText(stringTitle);

        Category.setText(stringCategory);
        Author.setText(stringAuthor);
        Date.setText(stringDate);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        database.getReference("post").child(stringTitle).child("article").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    stringArticle = String.valueOf(task.getResult().getValue());
                    Article.setText(stringArticle);
                }

            }
        });
        post = new post(stringArticle, firebaseUser.getUid(), stringCategory, stringTitle, stringAuthor, stringDate);
        favFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference("users").child(firebaseUser.getUid()).child("fav").child(stringTitle).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ArticleDisplayActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }

}