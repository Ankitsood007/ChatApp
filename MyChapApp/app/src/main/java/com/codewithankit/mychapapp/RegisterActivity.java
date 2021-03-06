package com.codewithankit.mychapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username , email , password;
    Button btn_register;

    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btnRegister);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        auth  = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text_username = username.getText().toString();
                String text_password = password.getText().toString();
                String text_email    = email.getText().toString();

                if(TextUtils.isEmpty(text_username) || TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)){
                    Toast.makeText(RegisterActivity.this , "All Fields are required" , Toast.LENGTH_LONG).show();
                }else{
                    register(text_username , text_email , text_password);
                }
            }
        });
    }


    private void register(final String username , String email , String password){

        auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userId = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                    HashMap<String , String> hashMap = new HashMap<>();

                    hashMap.put("id" , userId);
                    hashMap.put("username" , username);
                    hashMap.put("imageURI" , "default");


                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Intent intent = new Intent(RegisterActivity.this , Main2Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this , "You can't register with this Email." , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
