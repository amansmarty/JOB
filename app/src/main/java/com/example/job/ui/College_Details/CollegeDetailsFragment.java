package com.example.job.ui.College_Details;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.job.Model.Hire_Institute;
import com.example.job.R;
import com.example.job.databinding.FragmentCollegeDetailsBinding;
import com.example.job.ui.About.AboutFragment;
import com.example.job.ui.College_Info.College_Info_Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CollegeDetailsFragment extends Fragment {
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    private FragmentCollegeDetailsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCollegeDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        binding.btnClgPrev1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                College_Info_Fragment college_info_fragment = new College_Info_Fragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_work_info, college_info_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        binding.btnClgNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ranking = binding.etClgRanking.getText().toString().trim();
                String social = binding.etSocialUrl.getText().toString().trim();
                String clg_about = binding.etClgAbout.toString().trim();
                if(TextUtils.isEmpty(ranking)){
                    binding.etClgRanking.setError("Required");
                    return;
                }
                else if (TextUtils.isEmpty(social)){
                    binding.etSocialUrl.setError("Required");
                    return;
                }
                else if (TextUtils.isEmpty(clg_about)){
                    binding.etClgAbout.setError("Required");
                    return;
                }
                FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
                Hire_Institute hire_institute = new Hire_Institute(ranking,social,clg_about);
                DatabaseReference db= database.getReference();
                db.child("Hire").child(currentFirebaseUser.getUid()).child("College_Profile").setValue(hire_institute);
                AboutFragment aboutFragment = new AboutFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_work_info, aboutFragment);
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