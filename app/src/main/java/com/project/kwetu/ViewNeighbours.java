package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.kwetu.Adapters.NeighboursAdapter;
import com.project.kwetu.model.Tenant;

import java.util.ArrayList;
import java.util.List;

public class ViewNeighbours extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("TenantDetails");
    RecyclerView recyclerView;
    NeighboursAdapter adapter;
    ProgressBar mProgressBar;
    List<Tenant> tenants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_neighbours);

        recyclerView = findViewById(R.id.neighbours_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tenants = new ArrayList<>();

        mProgressBar = findViewById(R.id.progress_neighbours);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Tenant tenant = dataSnapshot.getValue(Tenant.class);

                    tenants.add(tenant);
                    adapter = new NeighboursAdapter(getApplicationContext(), tenants);
                    recyclerView.setAdapter(adapter);
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}