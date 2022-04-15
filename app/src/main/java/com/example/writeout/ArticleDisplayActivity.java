package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArticleDisplayActivity extends AppCompatActivity {
    TextView Title, Category, Date, Author;
    TextView Article;
    ExtendedFloatingActionButton favFab, commentFab;
    EditText Comment;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String stringTitle, stringArticle, stringCategory, stringAuthor, stringDate;
    post post;
    RecyclerView recComments;
    ArrayList<CommentModel> comments = new ArrayList<>();
    RecAdapter recAdapter;


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
        Comment = findViewById(R.id.et_comment);
        commentFab = findViewById(R.id.fab_comment);
        recComments = findViewById(R.id.rec_Comment2);
        recComments.setLayoutManager(new LinearLayoutManager(this));
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
        commentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Comment.getText().toString().isEmpty())
                    Toast.makeText(ArticleDisplayActivity.this, "No Comment", Toast.LENGTH_SHORT).show();
                else {
                    database.getReference("users").child(firebaseUser.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                CommentModel commentModel = new CommentModel(String.valueOf(task.getResult().getValue()), Comment.getText().toString());
                                database.getReference("post").child(stringTitle).child("comments").child(Comment.getText().toString()).setValue(commentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                            Toast.makeText(ArticleDisplayActivity.this, "Comment posted", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    });


                }
            }
        });
        database.getReference("post").child(stringTitle).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    CommentModel commentModel = snapshot1.getValue(CommentModel.class);
                    comments.add(commentModel);
                }
                recAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recAdapter = new RecAdapter(this, comments);
        recComments.setAdapter(recAdapter);


    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }

}