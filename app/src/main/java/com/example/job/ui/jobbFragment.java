package com.example.job.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.job.R;
import com.example.job.databinding.FragmentJobbBinding;


public class jobbFragment extends Fragment {
    private FragmentJobbBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding=FragmentJobbBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        return root;
    }
}