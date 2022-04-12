package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class WritingActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    AutoCompleteTextView act;
    EditText postTitle,postArticle;
    ExtendedFloatingActionButton Post;
    post post1,post2;
    String AuthorName;
    String Category;
    ArrayList<String> CategoryList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ProgressDialog progressDialog;
    int tapCounter=0;
    String date ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        postTitle=findViewById(R.id.PostTitle);
        date=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        act=findViewById(R.id.act_Category);
             //Drop down menu
        CategoryList.add("Informative");
        CategoryList.add("Casual");
        CategoryList.add("Political");
        arrayAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_item,CategoryList);
        act.setAdapter(arrayAdapter);

        postArticle=findViewById(R.id.PostArticle);
        Post=findViewById(R.id.PostButton);
        Post.shrink();

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
        act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                    Category="Informative";
                else if(i==1)
                    Category="Casual";
                else if(i==2)
                    Category="Political";


            }
        });
        Post.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                tapCounter+=1;
                if(tapCounter%2==0){
                    Post.shrink();
                    progressDialog.show();

                     if(postTitle.getText().toString().isEmpty()){
                    postTitle.setError("Please give a Title");
                    progressDialog.dismiss();
                    return;
                    }
                    if(Category==null){
                    act.setError("Please specify Category");
                    progressDialog.dismiss();
                    return;}
                    if(postArticle.getText().toString().isEmpty()){
                    postArticle.setError("Please write something");
                    progressDialog.dismiss();
                    return;
                    }
                    if(postArticle.getText().toString().length()<100){
                        postArticle.setError("Article must be at least 100 chars");
                        progressDialog.dismiss();
                        return;
                    }
                    else{
                        post1=new post(postArticle.getText().toString(),Category,postTitle.getText().toString(),date);
                    database.getReference("users").child(firebaseUser.getUid()).child("posts").child(postTitle.getText().toString()).setValue(post1);
                    post2=new post(postArticle.getText().toString(), firebaseUser.getUid(),Category,postTitle.getText().toString(),AuthorName,date);
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
            else
            Post.extend();
            }

        });

    }
    public void onBackPressed(){
        startActivity(new Intent(WritingActivity.this,MainActivity.class));
        finish();
    }
}