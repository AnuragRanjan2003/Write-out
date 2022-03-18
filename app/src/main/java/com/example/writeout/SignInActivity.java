package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    EditText Email,password;
    Button SignIn;
    TextView NewUser,ForgotPassword;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        Email=findViewById(R.id.etSignInEmail);
        password=findViewById(R.id.etSignInPassword);
        SignIn=findViewById(R.id.btnSignIn);
        NewUser=findViewById(R.id.tvNewUser);
        ForgotPassword=findViewById(R.id.tvForgotPassword);
        progressDialog=new ProgressDialog(SignInActivity.this);
        progressDialog.setMessage("Signing in");
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        SignIn.setOnClickListener(view -> {
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
            else {

            mAuth.signInWithEmailAndPassword(Email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent=new Intent(SignInActivity.this,MainActivity.class);
                    intent.putExtra("userid",task.getResult().getUser().getUid());
                    startActivity(intent);

                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
            }
        });
        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,ForgotPasswordActivity.class));
            }
        });


    }
}