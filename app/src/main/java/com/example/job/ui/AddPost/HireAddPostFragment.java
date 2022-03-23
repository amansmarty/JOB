package com.example.job.ui.AddPost;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.job.Model.Add_post;
import com.example.job.databinding.FragmentHireAddpostBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
import java.util.Date;

public class HireAddPostFragment extends Fragment {
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    private FragmentHireAddpostBinding binding;
    private  String Job_Title,job_description,skill,experience,qualification,job,salary;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        binding = FragmentHireAddpostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Job_Title=binding.etJobTitle.getText().toString().trim();
                job_description = binding.editTextTextPersonName2.getText().toString().trim();
                if(TextUtils.isEmpty(Job_Title)){
                    binding.etJobTitle.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(job_description)){
                    binding.editTextTextPersonName2.setError("Required Field");
                    return;
                }
                binding.ll1.setVisibility(View.VISIBLE);

            }
        });
        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skill = binding.editTextTextPersonName3.getText().toString().trim();
                if (TextUtils.isEmpty(skill)){
                    binding.editTextTextPersonName3.setError("Required field");
                    return;
                }
                binding.ll2.setVisibility(View.VISIBLE);
            }
        });
        binding.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 experience = binding.editTextTextPersonName4.getText().toString().trim();
                qualification = binding.editTextTextPersonName5.getText().toString().trim();
                job = binding.editTextTextPersonName6.getText().toString().trim();
                if (TextUtils.isEmpty(experience)){
                    binding.editTextTextPersonName4.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(qualification)){
                    binding.editTextTextPersonName5.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(job)){
                    binding.editTextTextPersonName6.setError("Required Field");
                    return;
                }
                binding.ll3.setVisibility(View.VISIBLE);
            }
        });
        binding.button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               salary = binding.editTextTextPersonName7.getText().toString().trim();
                if (TextUtils.isEmpty(salary)){
                    binding.editTextTextPersonName7.setError("Required Field");
                    return;
                }
                progressDialog=new ProgressDialog(getActivity());
                progressDialog.show();
                String date = DateFormat.getDateInstance().format(new Date());
                Add_post add_post=new Add_post(Job_Title,job_description,skill,experience,qualification,job,salary,date);
                FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
                DatabaseReference db= database.getReference();
                db.child("Post").child(currentFirebaseUser.getUid()).child("Post_Details").push().setValue(add_post);
                Toast.makeText(getActivity(),"Posted Successfully",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}