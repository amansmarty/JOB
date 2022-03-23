package com.example.job.ui.About;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.job.Model.Abouts;
import com.example.job.R;
import com.example.job.databinding.FragmentAboutBinding;
import com.example.job.databinding.FragmentIntroBinding;
import com.example.job.ui.Intro.IntroFragment;
import com.example.job.ui.Profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AboutFragment extends Fragment {
    private FragmentAboutBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        binding.btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String describe = binding.etDescribe.getText().toString().trim();
                String motivate = binding.etMotivates.getText().toString().trim();
                if (TextUtils.isEmpty(describe)) {
                    binding.etDescribe.setError("Required");
                } else if (TextUtils.isEmpty(motivate)) {
                    binding.etMotivates.setError("Required");
                } else if (motivate.length() < 100) {
                    binding.etMotivates.setError("Enter more than 100 words");
                } else if (describe.length() < 100) {
                    binding.etDescribe.setError("Enter more than 100 words");
                } else {
                    Abouts about = new Abouts(describe,motivate);
                    database.getReference().child(mAuth.getCurrentUser().getUid()).child("About").setValue(about);
                    IntroFragment introFragment = new IntroFragment();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_work_info, introFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }
        });

        binding.btnPrev1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_work_info, profileFragment);
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