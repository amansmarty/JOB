package com.example.job.ui.College_Info;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.job.Model.Hire_Institute;
import com.example.job.Model.Profiles;
import com.example.job.R;
import com.example.job.databinding.FragmentCollegeInfoBinding;
import com.example.job.ui.About.AboutFragment;
import com.example.job.ui.College_Details.CollegeDetailsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class College_Info_Fragment extends Fragment {
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    private FragmentCollegeInfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCollegeInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        binding.clgNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clg_name = binding.collegeName.getText().toString().trim();
                String clg_address = binding.collegeAddress.getText().toString().trim();
                String clg_state = binding.collegeState.toString().trim();
                String clg_city = binding.collegeCity.toString().trim();
                if(TextUtils.isEmpty(clg_name)){
                    binding.collegeName.setError("Required");
                    return;
                }
                else if (TextUtils.isEmpty(clg_address)){
                    binding.collegeAddress.setError("Required");
                    return;
                }
                else if (TextUtils.isEmpty(clg_state)){
                    binding.collegeState.setError("Required");
                    return;
                }
                else if (TextUtils.isEmpty(clg_city)){
                    binding.collegeCity.setError("Required");
                    return;
                }
                FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
                Hire_Institute hire_institute = new Hire_Institute(clg_name,clg_address,clg_city,clg_state);
                DatabaseReference db= database.getReference();
                db.child("Hire").child(currentFirebaseUser.getUid()).child("College_Details").setValue(hire_institute);
                CollegeDetailsFragment collegeDetailsFragment = new CollegeDetailsFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_work_info, collegeDetailsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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