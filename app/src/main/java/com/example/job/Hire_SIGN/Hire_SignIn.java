package com.example.job.Hire_SIGN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.job.Hire_signin_verification;
import com.example.job.R;
import com.example.job.Work_SIGN.Work_SignIn;
import com.example.job.Work_SIGN.work_signin_verification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Hire_SignIn extends AppCompatActivity {
EditText et_hire_sigin_mail,et_hire_sigin_pass;
Button btn_hire_signin_sign;
TextView tv_hire_signup;
    private static  final Pattern PASSWORD_PATTERN=
            Pattern.compile("^"+
                    "(?=.*[0-9])"+
                    "(?=.*[a-z])"+
                    "(?=.*[A-Z])"+
                    "(?=.*[a-zA-Z])"+
                    "(?=.*[@#$%^&+=])"+
                    "(?=\\S+$)"+
                    ".{6,}"+
                    "$");
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_sign_in);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        tv_hire_signup=findViewById(R.id.tv_hire_signup);
        et_hire_sigin_mail=findViewById(R.id.et_hire_signIn_email);
        et_hire_sigin_pass=findViewById(R.id.et_hire_signin_pass);
        btn_hire_signin_sign=findViewById(R.id.btn_hire_signin);
        tv_hire_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hire_SignIn.this,Hire_Signup.class);
                startActivity(intent);
            }
        });
        btn_hire_signin_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = et_hire_sigin_mail.getText().toString().trim();
                String Password = et_hire_sigin_pass.getText().toString().trim();
                if(TextUtils.isEmpty(Email)){
                    et_hire_sigin_mail.setError("Email is Required");
                    return;
                }
                else if(TextUtils.isEmpty(Password)){
                    et_hire_sigin_pass.setError("Password is Required");
                    return;
                }
                else if(Password.length()<6) {
                    et_hire_sigin_pass.setError("Password must be >= 6 Characters");
                    return;
                }
                else if(!PASSWORD_PATTERN.matcher(Password).matches()){
                    et_hire_sigin_pass.setError("Password too weak");
                    return;
                }


                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Hire_SignIn.this, Hire_signin_verification.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(Hire_SignIn.this,"error !"+task.getException(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}