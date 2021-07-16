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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

private TextView regBanner;
    private EditText regName;
    private EditText regAge;
    private EditText regUserName;
    private EditText regPassword;
    private Button regSubmit;
    private ProgressBar regProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        regBanner=(TextView)findViewById(R.id.RegBanner);

        regName=(EditText)  findViewById(R.id.RegName);
        regAge=(EditText)  findViewById(R.id.RegAge);
        regUserName=(EditText)  findViewById(R.id.RegUserName);
        regPassword=(EditText)  findViewById(R.id.RegPassword);

        regSubmit=(Button) findViewById(R.id.RegSubmit);

        regProgressBar=(ProgressBar) findViewById(R.id.RegProgressBar);

        regBanner.setOnClickListener(this);
        regSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.RegBanner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.RegSubmit:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String Name=regName.getText().toString().trim();
        String Age=regAge.getText().toString().trim();
        String UserName=regUserName.getText().toString().trim();
        String Password=regPassword.getText().toString().trim();

        if(Name.isEmpty()){
            regName.setError("Full Name is Required!");
            regName.requestFocus();
            return;
        }


        if(UserName.isEmpty()){
            regUserName.setError("Email Required!");
            regUserName.requestFocus();
            return;
        }


        if(Age.isEmpty()){
            regAge.setError("Age Required!");
            regAge.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(UserName).matches()){
            regUserName.setError("Enter Valid Email Address!");
            regUserName.requestFocus();
            return;
        }


        if(Password.isEmpty()){
            regPassword.setError("Password Required!");
            regPassword.requestFocus();
            return;
        }

        if (Password.length() < 6) {
            regPassword.setError("Minimum Password length should be 6 characters!");
            regPassword.requestFocus();
            return;
        }


        regProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(UserName,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user=new User(Name,Age,UserName);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterUser.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(RegisterUser.this,"Failed To Register! Try Again",Toast.LENGTH_LONG).show();
                            }
                            regProgressBar.setVisibility(View.GONE);
                        }
                    });
                }else{
                    Toast.makeText(RegisterUser.this,"Failed To Register!",Toast.LENGTH_LONG).show();
                    regProgressBar.setVisibility(View.GONE);
                }
            }
        });

    }


}