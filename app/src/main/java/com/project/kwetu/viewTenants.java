package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.kwetu.Adapters.usersAdapter;
import com.project.kwetu.model.Users;

import java.util.ArrayList;
import java.util.List;

public class viewTenants extends AppCompatActivity {
    RecyclerView mRecyclerView;
    usersAdapter mUsersAdapter;
    List<Users> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tenants);
        mRecyclerView = findViewById(R.id.users_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();

        getAllUsers();
    }

    private void getAllUsers() {
        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("Tenants");
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Users users = ds.getValue(Users.class);

                    if(!users.getUid().equals(fUser.getUid())){
                        mList.add(users);
                    }

                    mUsersAdapter = new usersAdapter(getApplicationContext(), mList);
                    mRecyclerView.setAdapter(new usersAdapter(getApplicationContext(), mList));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}