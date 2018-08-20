package com.cse.cou.alamgir.booksstore.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cse.cou.alamgir.booksstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etName,etEmail,etPassword;
    Button btnRegister;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etResEmail);
        etPassword=findViewById(R.id.etResPassword);
        mAuth=FirebaseAuth.getInstance();
        btnRegister=findViewById(R.id.btn_sign_up);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       register() ;
    }

    private void register() {
        String name=etName.getText().toString().trim();
        String email=etEmail.getText().toString().trim();
        String password=etPassword.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
            }
        });

    }
}
