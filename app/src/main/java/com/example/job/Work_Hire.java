package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.job.Hire_SIGN.Hire_SignIn;
import com.example.job.Work_SIGN.Work_SignIn;

public class Work_Hire extends AppCompatActivity {
    Button button,btn_hire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_hire);
        getSupportActionBar().hide();
        button=findViewById(R.id.btn_work);
        btn_hire=findViewById(R.id.btn_hire);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Work_Hire.this, Work_SignIn.class);
                startActivity(intent);
            }
        });
        btn_hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Work_Hire.this, Hire_SignIn.class);
                startActivity(intent);
            }
        });
    }
}