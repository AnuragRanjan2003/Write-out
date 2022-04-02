package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText Email,Username,password;
    Button SignUp;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        Email=findViewById(R.id.etSignUpEmail);
        Username=findViewById(R.id.etSignUpUserName);
        password=findViewById(R.id.etSignUpPassword);
        SignUp=findViewById(R.id.btnSignUp);
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        ProgressDialog progressDialog=new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Signing you Up");
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if(Email.getText().toString().isEmpty()){
                    Email.setError("Email is Required");
                    progressDialog.dismiss();
                    return;
                }

                if(password.getText().toString().isEmpty()){
                    password.setError("Password is Required");
                    progressDialog.dismiss();
                    return;
                }
                if(Username.getText().toString().isEmpty()){
                    Username.setError("Username Required");
                    progressDialog.dismiss();
                    return;
                }
                else {
                    mAuth.createUserWithEmailAndPassword(Email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                database.getReference("users").child(task.getResult().getUser().getUid()).child("name").setValue(Username.getText().toString().trim());
                                database.getReference("users").child(task.getResult().getUser().getUid()).child("id").setValue(task.getResult().getUser().getUid());

                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });


    }
}