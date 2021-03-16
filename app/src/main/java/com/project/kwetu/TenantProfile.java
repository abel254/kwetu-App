package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TenantProfile extends AppCompatActivity {
    TextView name, email, phone,idNumber, houseNumber;
   DatabaseReference reference;
   String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_profile);

        name = findViewById(R.id.nameT);
        email = findViewById(R.id.emailT);
        phone = findViewById(R.id.phoneT);
        idNumber = findViewById(R.id.idT);
        houseNumber = findViewById(R.id.houseT);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TenantDetails").child(userId);
        reference.child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String emailT = snapshot.getValue(String.class);
                email.setText(emailT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("houseNumber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String houseNumberT = snapshot.getValue(String.class);
                houseNumber.setText(houseNumberT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("idNumber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String idNumberT = snapshot.getValue(String.class);
                idNumber.setText(idNumberT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameT = snapshot.getValue(String.class);
                name.setText(nameT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("phoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phoneNumberT = snapshot.getValue(String.class);
                phone.setText(phoneNumberT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}