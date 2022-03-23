package com.example.job.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.job.Adapter_1.Adapter1;
import com.example.job.Model.Add_post;
import com.example.job.databinding.FragmentHireHomeBinding;
import com.example.job.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ArrayList<Add_post> arrayList;
        Adapter1 adapter1;
        mAuth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance().getReference().child("Post").child(mAuth.getCurrentUser().getUid()).child("Post_Details");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        binding.rvJob1.setHasFixedSize(true);
        binding.rvJob1.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        adapter1 = new Adapter1(arrayList,getContext());
        binding.rvJob1.setAdapter(adapter1);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Add_post add_post = dataSnapshot.getValue(Add_post.class);
                    arrayList.add(add_post);
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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