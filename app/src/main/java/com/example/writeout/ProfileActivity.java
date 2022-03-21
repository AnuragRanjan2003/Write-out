package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    EditText UserName;
    TextView ProfileUid,UserEmail;
    ExtendedFloatingActionButton fab;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog,progressDialog2;
    String UserNameOld;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserName=findViewById(R.id.etProfileUsername);
        UserEmail=findViewById(R.id.etProfileEmail);
        ProfileUid=findViewById(R.id.tvProfileUid);
        fab=findViewById(R.id.fabApplyChanges);
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(ProfileActivity.this);
        progressDialog2=new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Updating Profile");
        progressDialog2.setMessage("Fetching Data");
        progressDialog2.show();

        firebaseUser= mAuth.getCurrentUser();
        UserEmail.setText(firebaseUser.getEmail());

        database.getReference("users").child(firebaseUser.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    progressDialog2.dismiss();
                    UserName.setText(String.valueOf(task.getResult().getValue()));
                    UserNameOld=String.valueOf(task.getResult().getValue());
                }
                else
                    Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        UserEmail.setText(firebaseUser.getEmail());
        ProfileUid.setText(firebaseUser.getUid());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if(UserName.getText().toString().isEmpty()){
                    UserName.setError("Field Can't be Empty");
                    progressDialog.dismiss();
                    return;
                }
                if(UserName.getText().toString().equals(UserNameOld)){
                    UserName.setError("Username is Unchanged");
                    progressDialog.dismiss();
                    return;
                }
                else{
                HashMap hashMap=new HashMap();
                hashMap.put("name",UserName.getText().toString());
                database.getReference("users").child(firebaseUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                }


            }
        });



    }
}