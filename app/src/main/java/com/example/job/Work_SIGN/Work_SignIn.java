package com.example.job.Work_SIGN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.job.MainActivity;
import com.example.job.Model.User;
import com.example.job.R;
import com.example.job.work_info;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Work_SignIn extends AppCompatActivity {
    private static  final Pattern PASSWORD_PATTERN=
    Pattern.compile("^"+
            "(?=.*[0-9])"+
            "(?=.*[a-z])"+
            "(?=.*[A-Z])"+
            "(?=.*[a-zA-Z])"+
            "(?=.*[@#$%^&+=])"+
            "(?=\\S+$)"+
            ".{6,}"+
            "$");
    GoogleSignInClient mGoogleSignInClient;
    EditText et_signin,password;
    Button btn_sigin;
    FirebaseAuth mAuth;
    TextView tv_signup;
    ImageView img_google;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_sign_in);
        getSupportActionBar().hide();
        et_signin=findViewById(R.id.etEmail_signin);
        password=findViewById(R.id.etPass_signin);
        btn_sigin=findViewById(R.id.login_btn);
        mAuth= FirebaseAuth.getInstance();
        final FirebaseUser user =mAuth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        tv_signup=findViewById(R.id.tv_signup);
        img_google=findViewById(R.id.img_google_signin);
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Work_SignIn.this, Work_SignUp.class);
                startActivity(intent);
            }
        });


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        btn_sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = et_signin.getText().toString().trim();
                String Password = password.getText().toString().trim();
                if(TextUtils.isEmpty(Email)){
                    et_signin.setError("Email is Required");
                    return;
                }
                else if(TextUtils.isEmpty(Password)){
                    password.setError("Password is Required");
                    return;
                }
                else if(Password.length()<6) {
                    password.setError("Password must be >= 6 Characters");
                    return;
                }
                else if(!PASSWORD_PATTERN.matcher(Password).matches()){
                    password.setError("Password too weak");
                    return;
                }


                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Work_SignIn.this, work_signin_verification.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(Work_SignIn.this,"error !"+task.getException(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        img_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }
    int RC_SIGN_IN=65;
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            Intent intent = new Intent(Work_SignIn.this, work_info.class);
                            startActivity(intent);
                            finish();
                            FirebaseUser user = mAuth.getCurrentUser();
                            User users = new User();
                            users.setEmail(user.getEmail());
                            users.setId(user.getUid());
                            users.setUsername(user.getDisplayName());
                            database.getReference().child("Work").child(user.getUid()).child("Users").setValue(users);
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                        }
                    }
                });
    }
}