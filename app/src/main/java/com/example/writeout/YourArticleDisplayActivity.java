package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class YourArticleDisplayActivity extends AppCompatActivity {
    String Title, Category, Date, Author;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    TextView title, category, date;
    EditText article;
    ExtendedFloatingActionButton parentFab, editFab, deleteFab, favFab;
    Boolean parentFabIsOpen = false;
    String prevArticle;
    post post;
    RecyclerView RecComments;
    ArrayList<CommentModel> Comments;
    Animation FabOpen, FabClose, FabClock, FabAntiClock;
    RecAdapter recAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_article_display);
        title = findViewById(R.id.tv_title);
        category = findViewById(R.id.tv_category);
        article = findViewById(R.id.etarticle);
        date = findViewById(R.id.tv_date1);
        parentFab = findViewById(R.id.parentfab);
        editFab = findViewById(R.id.editfab);
        favFab = findViewById(R.id.favfab);
        deleteFab = findViewById(R.id.deletefab);
        RecComments = findViewById(R.id.rec_Comment1);
        editFab.setVisibility(View.INVISIBLE);
        deleteFab.setVisibility(View.INVISIBLE);
        favFab.setVisibility(View.INVISIBLE);
        Comments = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        Intent intent2 = getIntent();
        Title = intent2.getStringExtra("title");
        Intent intent3 = getIntent();
        Category = intent3.getStringExtra("category");
        Intent intent4 = getIntent();
        Date = intent4.getStringExtra("date");
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        FabClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clock);
        FabAntiClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlock);
        RecComments.setLayoutManager(new LinearLayoutManager(this));
        database.getReference("users").child(firebaseUser.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Author = String.valueOf(task.getResult().getValue());
            }
        });
        title.setText(Title);
        category.setText(Category);
        date.setText(Date);
        database.getReference("post").child(Title).child("article").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    prevArticle = String.valueOf(task.getResult().getValue());
                    article.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });

        parentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!parentFabIsOpen) {
                    editFab.startAnimation(FabOpen);
                    deleteFab.startAnimation(FabOpen);
                    favFab.startAnimation(FabOpen);
                    parentFab.startAnimation(FabClock);
                    parentFabIsOpen = true;
                } else {
                    editFab.startAnimation(FabClose);
                    deleteFab.startAnimation(FabClose);
                    favFab.startAnimation(FabClose);
                    parentFab.startAnimation(FabAntiClock);
                    parentFabIsOpen = false;

                }

            }
        });
        editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (article.getText().toString().equals(prevArticle)) {
                    article.setError("Article UnChanged");
                    return;
                } else if (article.getText().toString().isEmpty()) {
                    article.setError("Article Empty");
                    return;
                } else {
                    HashMap hashMap1 = new HashMap();
                    hashMap1.put("article", article.getText().toString());
                    database.getReference("post").child(Title).updateChildren(hashMap1).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(YourArticleDisplayActivity.this, "Article Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    database.getReference("users").child(firebaseUser.getUid()).child("posts").child(Title).updateChildren(hashMap1);
                }

            }
        });
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(YourArticleDisplayActivity.this)
                        .setTitle("Delete the Article")
                        .setMessage("Are you sure you want to delete this article")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.getReference("post").child(Title).removeValue();
                                database.getReference("users").child(firebaseUser.getUid()).child("posts").child(Title).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(YourArticleDisplayActivity.this, "Article Deleted Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(YourArticleDisplayActivity.this, MainActivity.class));
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        })
                        .create()
                        .show();


            }
        });
        post = new post(prevArticle, firebaseUser.getUid(), Category, Title, Author, Date);
        favFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference("users").child(firebaseUser.getUid()).child("fav").child(Title).setValue(post);
                database.getReference("users").child(firebaseUser.getUid()).child("fav").child(Title).child("authorName").setValue(Author)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(YourArticleDisplayActivity.this, "Added to Favourite", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        database.getReference("post").child(Title).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    CommentModel commentModel = snapshot1.getValue(CommentModel.class);
                    Comments.add(commentModel);

                }
                recAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        RecComments.hasFixedSize();
        recAdapter = new RecAdapter(YourArticleDisplayActivity.this, Comments);
        RecComments.setAdapter(recAdapter);


    }

    public void onBackPressed() {
        startActivity(new Intent(YourArticleDisplayActivity.this, MainActivity.class));
        finishAffinity();

    }

}