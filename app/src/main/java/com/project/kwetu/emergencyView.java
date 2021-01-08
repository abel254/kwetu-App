package com.project.kwetu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class emergencyView extends AppCompatActivity {

    CardView cvHealth, cvFire, cvCrime, cvOthers;
    Button pageEdit;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_view);

        cvHealth = findViewById(R.id.cardView_health);
        cvFire = findViewById(R.id.cardView_fire);
        cvCrime = findViewById(R.id.cardView_Crime);
        cvOthers = findViewById(R.id.cardView_others);
        pageEdit = findViewById(R.id.page_Edit);


        mAuth = FirebaseAuth.getInstance();
    }

    public void confirmUser(View view){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Landlord");
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser != null){
            Intent intent = new Intent(emergencyView.this, emergencyContacts.class);
            startActivity(intent);


        }
        else{
           
        }

    }
}