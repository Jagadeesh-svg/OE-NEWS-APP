package com.example.OENews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register;
    private EditText EditTextEmail;
    private EditText EditTextPassword;
    private Button signIn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn=(Button) findViewById(R.id.LoginButton);
        EditTextEmail=(EditText)findViewById(R.id.LoginEmail);
        EditTextPassword=(EditText)findViewById(R.id.LoginPassword);
        progressBar=(ProgressBar)findViewById(R.id.ProgressBar);

        signIn.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                startActivity(new Intent(this,RegisterUser.class));
                break;
                case R.id.LoginButton:
                userLogin();
                break;
        }


    }

    private void userLogin() {
        String email=EditTextEmail.getText().toString().trim();
        String password=EditTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            EditTextEmail.setError("Email Required");
            EditTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EditTextEmail.setError("Enter Valid Email Address!");
            EditTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            EditTextPassword.setError("Password  Required!");
            EditTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            EditTextPassword.setError("Minimum Password length should be 6 characters!");
            EditTextPassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);



        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                }
                else{
                    Toast.makeText(MainActivity.this,"Failed To Login! Please Check Email and Password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}