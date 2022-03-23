package com.example.job.ui.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.number.IntegerWidth;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.job.Model.Profiles;
import com.example.job.R;
import com.example.job.databinding.FragmentProfileBinding;
import com.example.job.ui.About.AboutFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileFragment extends Fragment {
    Uri uri;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    private FragmentProfileBinding binding;
    StorageReference storageReference;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        binding.btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = binding.etFirstName.getText().toString().trim();
                String last_name = binding.etLastName.getText().toString().trim();
                String pic = binding.profileImage.toString().trim();
                if(TextUtils.isEmpty(first_name)){
                    binding.etFirstName.setError("Required");
                    return;
                }
                else if (TextUtils.isEmpty(last_name)){
                    binding.etLastName.setError("Required");
                    return;
                }
                FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
                Profiles profile = new Profiles(first_name,last_name,pic);
                DatabaseReference db= database.getReference();
                db.child(currentFirebaseUser.getUid()).child("Profile").setValue(profile);
                Log.d("AAA",currentFirebaseUser.getUid());
//                        database.getReference().child(id).child("Profile").setValue(profile);
                AboutFragment aboutFragment = new AboutFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_work_info, aboutFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


//                SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
//                Date now = new Date();
//                String filename = format.format(now);
//                storageReference=FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Image/");
//                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        binding.profileImage.setImageURI(null);
//                    }
//                });
            }
        });
        return root;
    }


    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 &&data!=null && data.getData()!=null){
            uri=data.getData();
            binding.profileImage.setImageURI(uri);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}