package com.jeff.schoolproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    // cette activité est pris en compte comme le login de notre app avec Firebase comme back-end
    TextInputEditText editTextEmail, editTextPassword;
    Button idSendButton, account;

    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.resPassword);
        idSendButton = findViewById(R.id.idSendButton);
        account = findViewById(R.id.account);
        progressBar = findViewById(R.id.progressBar);


        //pour une première connexion sur l'appli il faut s'enregistrer
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });

        idSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Entrer votre email", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Entrer votre mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Connexion réussie",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Echec de la connexion",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}