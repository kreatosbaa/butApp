package com.example.butApp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.butApp.MainActivity;
import com.example.butApp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, pass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emaill);
        pass = findViewById(R.id.passl);
    }
    public void Login(View v) {
        String str_email = email.getText().toString();
        String str_pass = pass.getText().toString();
        if (!str_email.isEmpty() && !str_pass.isEmpty()) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(str_email,str_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                          startActivity(new Intent(getApplicationContext(), MainActivity.class));
                          finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"Hatalı Giriş",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"Boş Alanları Doldurunuz",Toast.LENGTH_SHORT).show();
        }
    }
    public void Register(View v) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }
}