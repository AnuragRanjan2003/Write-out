package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class WritingActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    AppCompatSpinner Spinner;
    EditText postTitle,postCategory,postArticle;
    Button Post;
    post post1,post2;
    String AuthorName;
    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        postTitle=findViewById(R.id.PostTitle);
        postCategory=findViewById(R.id.PostCategory);

        postArticle=findViewById(R.id.PostArticle);
        Post=findViewById(R.id.PostButton);
        progressDialog=new ProgressDialog(WritingActivity.this);
        progressDialog.setMessage("Posting");
        database.getReference("users").child(firebaseUser.getUid()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AuthorName=snapshot.getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WritingActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        });
        Post.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                progressDialog.show();

                if(postTitle.getText().toString().isEmpty()){
                    postTitle.setError("Please give a Title");
                    progressDialog.dismiss();
                    return;
                }
                if(postCategory.getText().toString().isEmpty()){
                    postCategory.setError("Please specify Category");
                    progressDialog.dismiss();
                    return;}
                if(postArticle.getText().toString().isEmpty()){
                    postArticle.setError("Please write something");
                    progressDialog.dismiss();
                    return;
                }
                
                if(!postCategory.getText().toString().equals("INFORMATIVE")&&!postCategory.getText().toString().equals("CASUAL")&&!postCategory.getText().toString().equals("POLITICAL")){
                    postCategory.setError("Please give a Valid Category \n Try All Caps");
                    progressDialog.dismiss();
                    return;
                }
                else{
                post1=new post(postArticle.getText().toString(),postCategory.getText().toString(),postTitle.getText().toString());
                database.getReference("users").child(firebaseUser.getUid()).child("posts").child(postTitle.getText().toString()).setValue(post1);
                post2=new post(postArticle.getText().toString(), firebaseUser.getUid(),postCategory.getText().toString(),postTitle.getText().toString(),AuthorName);
                database.getReference("post").child(postTitle.getText().toString()).setValue(post2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(WritingActivity.this, "posted Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(WritingActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });}

            }
        });
    }
}