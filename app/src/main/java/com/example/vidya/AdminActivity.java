package com.example.vidya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vidya.databinding.ActivityAdminBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
//private ActivityAdminBinding binding;
    private ActivityAdminBinding binding;
private FirebaseAuth firebaseAuth;
private ArrayList<ModelCategory> categoryArrayList;
private AdapterCategory adapterCategory;
    MyAdapter myAdapter;
    ArrayList<User> list;

    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());
        firebaseAuth= FirebaseAuth.getInstance();
        //checkUser();
//        loadCategories();
        binding.categoriesRv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<User> options=
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Categories"),User.class)
                        .build();
        myAdapter=new MyAdapter(options);
        binding.categoriesRv.setAdapter(myAdapter);

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
        binding.addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,AddCategroyActivity.class));
            }
        });

    }

//    private void loadCategories() {
//        categoryArrayList = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                categoryArrayList.clear();
//                if (snapshot.exists()) {
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//                        if (ds.exists()) {
//                            ModelCategory model = ds.getValue(ModelCategory.class);
//                            if (model != null) {
//                                categoryArrayList.add(model);
//                            }
//                        }
//                    }
//                    adapterCategory = new AdapterCategory(AdminActivity.this, categoryArrayList);
//                    binding.categoriesRv.setAdapter(adapterCategory);
//                } else {
//                    // Handle the case where there are no categories available
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle onCancelled event if needed
//            }
//        });
//    }
//
//
    private void checkUser() {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser==null)
        {
            startActivity(new Intent(AdminActivity.this,MainActivity.class));
            finish();
        }
        else
        {
            String email=firebaseUser.getEmail();
            binding.subtitle.setText(email);
        }
    }
}