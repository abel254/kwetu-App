package com.project.kwetu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class TenantMainPage extends AppCompatActivity {
    CardView emergency, neighbours, profile, issues;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main_page);

        emergency = findViewById(R.id.emergency_cv);
        logout = findViewById(R.id.tenant_logout);
        neighbours = findViewById(R.id.neighbours_cv);
        profile = findViewById(R.id.card1);
        issues = findViewById(R.id.issues_cv);

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TenantMainPage.this, EmergencyView.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TenantMainPage.this, BLogin.class));
                finish();
            }
        });

        neighbours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TenantMainPage.this, ViewNeighbours.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TenantMainPage.this, TenantProfile.class));
            }
        });

        issues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TenantMainPage.this, ChatActivity.class));
            }
        });
    }
}