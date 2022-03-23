package com.example.job.Hire_SIGN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.job.Hire_signin_verification;
import com.example.job.Model.Hire_Institute;
import com.example.job.Model.User;
import com.example.job.R;
import com.example.job.Work_SIGN.Work_SignUp;
import com.example.job.Work_SIGN.work_signin_verification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Hire_Signup extends AppCompatActivity {
EditText et_hire_email,et_hire_pass,et_hire_cpass;
Button btn_hire_signup;
FirebaseAuth mAuth;
TextView tv_hire_sigin;
FirebaseDatabase database;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_signup);
        et_hire_email=findViewById(R.id.et_hire_email);
        et_hire_pass=findViewById(R.id.et_hire_pass);
        et_hire_cpass=findViewById(R.id.et_hire_cpass);
        btn_hire_signup=findViewById(R.id.btn_hire_signup);
        tv_hire_sigin=findViewById(R.id.tv_hire_sigin);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        tv_hire_sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hire_Signup.this,Hire_SignIn.class);
                startActivity(intent);
            }
        });
        btn_hire_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = et_hire_email.getText().toString().trim();
                String Pass = et_hire_pass.getText().toString().trim();
                String C_Pass = et_hire_cpass.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                    et_hire_email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(Pass)) {
                    et_hire_pass.setError("Password is Required");
                    return;
                }
                if (Pass.length() < 6) {
                    et_hire_pass.setError("Password must be >= 6 Characters");
                    return;
                }
                if (!C_Pass.equals(C_Pass)) {
                    et_hire_cpass.setError("Password don't matched");
                    return;
                }
                if(!PASSWORD_PATTERN.matcher(Pass).matches()){
                    et_hire_pass.setError("Password too weak");
                    return;
                }


                mAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = task.getResult().getUser().getUid();
                            Hire_Institute hire_institute = new Hire_Institute(Email,Pass);
                            DatabaseReference db = database.getReference();
                            db.child("Hire").child(id).child("Login").setValue(hire_institute);
                            Intent intent = new Intent(Hire_Signup.this, Hire_signin_verification.class);
                            startActivity(intent);
                            // finish();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Hire_Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}