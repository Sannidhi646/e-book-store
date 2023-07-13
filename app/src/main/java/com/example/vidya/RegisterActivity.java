package com.example.vidya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.vidya.databinding.ActivityRegisterMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
private ActivityRegisterMainBinding binding;
private FirebaseAuth firebaseAuth;
private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait");;
        progressDialog.setCanceledOnTouchOutside(false);

        binding.backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        binding.registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                validateData();
            }


        });

}
private String name="",email="",password="",cpassowrd="";
private void validateData(){
    name=binding.nameEt.getText().toString().trim();
    email=binding.emailEt.getText().toString().trim();
    password=binding.passwordEt.getText().toString().trim();
    cpassowrd=binding.cPasswordEt.getText().toString().trim();
    if(TextUtils.isEmpty(name))
        Toast.makeText(this,"Enter the name...",Toast.LENGTH_SHORT).show();
    else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        Toast.makeText(this,"Invalid Email name...",Toast.LENGTH_SHORT).show();
    else if(TextUtils.isEmpty(password))
        Toast.makeText(this,"Enter password...!",Toast.LENGTH_SHORT).show();
    else if(TextUtils.isEmpty(cpassowrd))
        Toast.makeText(this,"Confirm password...!",Toast.LENGTH_SHORT).show();
    else if (!password.equals(cpassowrd)) {
        Toast.makeText(this,"Password doesn't match...",Toast.LENGTH_SHORT).show();

    }
    else
        createAccount();

    }

    private void createAccount() {
    progressDialog.setMessage("Creating account...");
    progressDialog.show();

    firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    updateUserInfo();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    private void updateUserInfo(){
    progressDialog.setMessage("Saving user info....");
    long timestamp=System.currentTimeMillis();
    String uid=firebaseAuth.getUid();
        HashMap<String,Object> hashMap=new HashMap<>();

        hashMap.put("uid",uid);
        hashMap.put("email",email);
        hashMap.put("name",name);
        hashMap.put("profileImage","");
        hashMap.put("userType","user");
        hashMap.put("timestamp",timestamp);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
        ref.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Account created...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,DashboardUserActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}