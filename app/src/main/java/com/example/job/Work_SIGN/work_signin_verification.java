package com.example.job.Work_SIGN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.job.MainActivity;
import com.example.job.R;
import com.example.job.work_info;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class work_signin_verification extends AppCompatActivity {
    Button resend,change,next;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_signin_verification);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        resend=findViewById(R.id.btn_resend);
        next=findViewById(R.id.button7);
        if (!mAuth.getCurrentUser().isEmailVerified()){
            mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(work_signin_verification.this,"Verification Mail Sent",Toast.LENGTH_SHORT).show();
                }
            });
        }
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAuth.getCurrentUser().isEmailVerified()){
                    mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(work_signin_verification.this,"Verification Mail Sent",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser().isEmailVerified()){
                    Intent intent = new Intent(work_signin_verification.this, work_info.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}