package com.example.butApp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.butApp.R;
import com.example.butApp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    EditText first,last,email,pass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        first = findViewById(R.id.first);
        last = findViewById(R.id.passl);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

    }
    public void Login(View v) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    public void Register(View v) {
        String str_first = first.getText().toString();
        String str_last = last.getText().toString();
        String str_email = email.getText().toString();
        String str_pass = pass.getText().toString();

        if(!str_first.isEmpty() && !str_last.isEmpty() && !str_email.isEmpty() && !str_pass.isEmpty()){
            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(str_email,str_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String uid = task.getResult().getUser().getUid();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        UserModel user = new UserModel(str_first,str_last,str_email);
                        db.collection("UserModel").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Başarılı Bir şekilde kayıt oldunuz",Toast.LENGTH_SHORT).show();
                                startActivity( new Intent(getApplicationContext(),LoginActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Kayıtta Bir problem var",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Kayıt Başarısız",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}