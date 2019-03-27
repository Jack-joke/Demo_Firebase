package com.example.student.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.net.Authenticator;

public class LoginActivity extends AppCompatActivity {
    EditText edtemail, edtpass;
    private FirebaseAuth auth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        edtemail = findViewById(R.id.edt_email);
        edtpass = findViewById(R.id.edt_password);
        progressBar = findViewById(R.id.progressbar);
    }

    public void Login(View view) {
        String email = edtemail.getText().toString().trim();
        String password = edtpass.getText().toString().trim();

        if (email.isEmpty()) {
            edtemail.setError("Email is required");
            edtemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtemail.setError("Please enter a valid email");
            edtemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edtpass.setError("Password is required");
            edtpass.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edtpass.setError("Minimum lenght of password should be 6");
            edtpass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Signup(View view) {
        Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
        startActivity(intent);
    }
}
