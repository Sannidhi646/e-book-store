package com.example.vidya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vidya.databinding.ActivityAdminBinding;
import com.example.vidya.databinding.ActivityDashboardUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardUserActivity extends AppCompatActivity {

    private ActivityDashboardUserBinding binding;
    private FirebaseAuth firebseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebseAuth= FirebaseAuth.getInstance();
        checkUser();
        binding.fiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardUserActivity.this, fiction.class));
            }
        });

        binding.actionn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardUserActivity.this, action.class));
            }
        });

        binding.classics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardUserActivity.this, classics.class));
            }
        });
//
//        binding.history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DashboardUserActivity.this, historical.class));
//            }
//        });
//
//        binding.fantasy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DashboardUserActivity.this, fanatasy.class));
//            }
//        });
//
//        binding.kids.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DashboardUserActivity.this, kids.class));
//            }
//        });
//
//        binding.selfhelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DashboardUserActivity.this, selfhelp.class));
//            }
//        });
//        binding.magazines.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DashboardUserActivity.this, mag.class));
//            }
//        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebseAuth.signOut();
                checkUser();
            }
        });
    }
    private void checkUser() {
        FirebaseUser firebaseUser=firebseAuth.getCurrentUser();
        if(firebaseUser==null)
        {
            startActivity(new Intent(DashboardUserActivity.this,MainActivity.class));
            finish();
        }
        else
        {
            String email=firebaseUser.getEmail();
            binding.subtitle.setText(email);
        }
    }
}