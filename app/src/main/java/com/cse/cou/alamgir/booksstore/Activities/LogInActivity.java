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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    DatabaseReference db;
    EditText etEmail, etPassword;
    Button btnLogIn,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogIn=findViewById(R.id.btn_sign_in);
        btnRegister=findViewById(R.id.create_account);
        btnLogIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_sign_in:
                login();
                break;
            case R.id.create_account:
                register();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if (user!=null)
        {
            startActivity(new Intent(LogInActivity.this,HomeActivity.class));
        }
    }

    private void register() {
        startActivity(new Intent(LogInActivity.this,RegisterActivity.class));
    }

    private void login() {
        String email=etEmail.getText().toString().trim();
        String password=etPassword.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                  startActivity(new Intent(LogInActivity.this,HomeActivity.class));
            }
        });

    }
}
