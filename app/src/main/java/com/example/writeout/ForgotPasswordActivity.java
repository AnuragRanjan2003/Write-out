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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText ForgetEmail;
    Button SendEmail;
    TextView BackToSignIn;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        ForgetEmail=findViewById(R.id.etForgetEmail);
        SendEmail=findViewById(R.id.btnSendEmail);
        BackToSignIn=findViewById(R.id.tvBackToSignIn);
        progressDialog=new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setMessage("Sending Mail");
        mAuth=FirebaseAuth.getInstance();





        BackToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPasswordActivity.this,SignInActivity.class));
            }
        });
        SendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if(ForgetEmail.getText().toString().isEmpty()){
                    ForgetEmail.setError("Email is required");
                    progressDialog.dismiss();
                }
                else {
                    mAuth.sendPasswordResetEmail(ForgetEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPasswordActivity.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPasswordActivity.this,SignInActivity.class));
                                finish();

                            }
                            else{
                                Toast.makeText(ForgotPasswordActivity.this, "Task Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    progressDialog.dismiss(); }
            }
        });
    }
}