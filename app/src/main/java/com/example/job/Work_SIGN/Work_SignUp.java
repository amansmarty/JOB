package com.example.job.Work_SIGN;

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

import com.example.job.Model.User;
import com.example.job.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Work_SignUp extends AppCompatActivity {
    EditText et_email,et_username,et_password,et_cpassword;
    Button btn_signup;
    FirebaseAuth mAuth;
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
        setContentView(R.layout.activity_work_sign_up);
        getSupportActionBar().hide();
        et_email=findViewById(R.id.et_email_signup);
        et_username=findViewById(R.id.et_user_signup);
        et_password=findViewById(R.id.et_pass_signup);
        et_cpassword=findViewById(R.id.et_Cpass_signup);
        btn_signup=findViewById(R.id.btn_signup);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = et_email.getText().toString().trim();
                String username = et_username.getText().toString().trim();
                String Pass = et_password.getText().toString().trim();
                String C_Pass = et_cpassword.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                    et_email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(Pass)) {
                    et_password.setError("Password is Required");
                    return;
                }
                if (Pass.length() < 6) {
                    et_password.setError("Password must be >= 6 Characters");
                    return;
                }
                if (C_Pass.equals(C_Pass)) {
                } else {
                    et_cpassword.setError("Password don't matched");
                    return;
                }
                if(!PASSWORD_PATTERN.matcher(Pass).matches()){
                    et_password.setError("Password too weak");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = task.getResult().getUser().getUid();
                            User user = new User(Email,username,Pass,id);
                            DatabaseReference db = database.getReference();
                            db.child("Work").child(id).child("Users").setValue(user);
                            Intent intent = new Intent(Work_SignUp.this, work_signin_verification.class);
                            startActivity(intent);
                            // finish();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Work_SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}