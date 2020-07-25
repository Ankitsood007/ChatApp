package com.codewithankit.mychapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.widget.Toolbar;


public class LoginActivity extends AppCompatActivity {

    EditText email , password;
    Button btnLogin;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailtext = email.getText().toString();
                String passtext  = password.getText().toString();

                if(TextUtils.isEmpty(emailtext) || TextUtils.isEmpty(passtext)){
                    Toast.makeText(LoginActivity.this , "All Fiels are Required." , Toast.LENGTH_LONG).show();
                }else{
                    auth.signInWithEmailAndPassword(emailtext , passtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Intent intent = new Intent(LoginActivity.this , Main2Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this , "Authentication Failed" , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
