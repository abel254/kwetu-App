package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.kwetu.Adapters.AdapterIssues;
import com.project.kwetu.model.Issues;

import java.util.ArrayList;
import java.util.List;

public class viewIssues extends AppCompatActivity {
    
    FirebaseAuth mAuth;
    
    RecyclerView mRecyclerView;
    List<Issues> issuesList;
    AdapterIssues adapterIssues;
    FloatingActionButton fab;

    private AppBarConfiguration mAppBarConfiguration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_issues);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dash, R.id.nav_neighbors, R.id.nav_pchats)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        
        mAuth = FirebaseAuth.getInstance();
        
        mRecyclerView = findViewById(R.id.issuesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(layoutManager);

        fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssuePost();
            }
        });
        
        issuesList = new ArrayList<>();
        
        loadPosts();
    }

    private void newIssuePost() {
        Intent intent = new Intent(this, issuesPost.class);
        startActivity(intent);
    }

    private void loadPosts() {
        
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Issues");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
                issuesList.clear();
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    Issues issues = ds.getValue(Issues.class);
                    issuesList.add(issues);
                    adapterIssues = new AdapterIssues(viewIssues.this, issuesList);
                    mRecyclerView.setAdapter(adapterIssues);
                            
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(viewIssues.this, ""+ error.getMessage(), Toast.LENGTH_SHORT).show();
                        

            }
        });
    }

    private void checkUserStatus(){

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser != null){


        }
        else{
            startActivity(new Intent(this, First_page.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    
}