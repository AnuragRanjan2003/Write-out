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
    TextView NewUser;
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
        progressDialog=new ProgressDialog(SignInActivity.this);
        progressDialog.setMessage("Signing in");
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(Email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                        progressDialog.dismiss();
                        //Make main activity and mention in explicit intent parameter
                        Intent intent=new Intent();
                        intent.putExtra("userid",task.getResult().getUser().getUid());
                        startActivity(intent);
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(SignInActivity.this, "Signing in Failed", Toast.LENGTH_SHORT).show();
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


    }
}