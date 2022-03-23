package com.example.job.ui.WorkFeatures;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.job.Model.Hire_Institute;
import com.example.job.R;
import com.example.job.databinding.FragmentWorkFeatureBinding;
import com.example.job.ui.College_Details.CollegeDetailsFragment;
import com.example.job.ui.AddPost.HireAddPostFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class Work_Features extends Fragment {

    private FragmentWorkFeatureBinding binding;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    boolean[] selected;
    ArrayList<Integer> workFlow = new ArrayList<>();
    String [] flowArray={"Admission","Placement","Research","Recruitment","Training"};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkFeatureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        selected=new boolean[flowArray.length];
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        binding.tvWorkforce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Work Force");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(flowArray, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            workFlow.add(which);
                            Collections.sort(workFlow);
                        }
                        else {
                            workFlow.remove(which);
                        }
                    }
                }); builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for(int j=0;j<workFlow.size();j++){
                            stringBuilder.append(flowArray[workFlow.get(j)]);
                            if(j!=workFlow.size()-1){
                                stringBuilder.append(" , ");
                            }
                        }
                        binding.tvWorkforce.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int j=0;j<selected.length;j++){
                            selected[j]=false;
                            workFlow.clear();
                            binding.tvWorkforce.setText("");
                        }
                    }
                });builder.show();
            }
        });


        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String work = binding.tvWorkforce.getText().toString().trim();

                if(TextUtils.isEmpty(work)){
                    binding.tvWorkforce.setError("Required");
                    return;
                }
                FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
                Hire_Institute hire_institute = new Hire_Institute(work);
                DatabaseReference db= database.getReference();
                db.child("Hire").child(currentFirebaseUser.getUid()).child("College_Details").setValue(hire_institute);
                HireAddPostFragment dashboardFragment = new HireAddPostFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_work_info, dashboardFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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