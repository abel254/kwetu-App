package com.project.kwetu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.kwetu.model.LandlordModel;

public class BLogin extends AppCompatActivity {
    CardView landlord, tenant;
    String username = "landlord";
    String password = "123456";
    String phoneNumber = "0797165991";
    DatabaseReference reference;
    LandlordModel landlordModel;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_login);

        landlord = findViewById(R.id.landlord_cardview);
        tenant = findViewById(R.id.tenant_cardview);

        landlord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference("LandloardAuth");
                landlordModel = new LandlordModel(username, password, phoneNumber);
                reference.child(username).setValue(landlordModel);
                startActivity(new Intent(BLogin.this, AdminLogin.class));

            }
        });

        tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BLogin.this, TenantLogin.class));

            }
        });
    }
}