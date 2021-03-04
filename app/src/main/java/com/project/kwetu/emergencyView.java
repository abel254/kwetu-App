package com.project.kwetu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.kwetu.model.Contacts;

public class emergencyView extends AppCompatActivity {

    CardView cvHealth, cvFire, cvCrime, cvOthers;
    Button pageEdit;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseUser mFirebaseUser;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_view);

        cvHealth = findViewById(R.id.cardView_health);
        cvHealth.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ambulanceCall();
                return true;
            }
        });
        cvFire = findViewById(R.id.cardView_fire);
        cvFire.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                fireCall();
                return true;
            }
        });
        cvCrime = findViewById(R.id.cardView_Crime);
        cvCrime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                crimeCall();
                return true;
            }
        });
        cvOthers = findViewById(R.id.cardView_others);
        cvOthers.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                
                callLandlord();
                return true;
            }
        });
        pageEdit = findViewById(R.id.page_Edit);


    }

    private void callLandlord() {
    }

    private void crimeCall() {
    }

    private void fireCall() {
    }

    private void ambulanceCall() {

    }

    public void confirmUser(View view){


        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Landlord");


        if(mFirebaseUser != null){
            Intent intent = new Intent(emergencyView.this, emergencyContacts.class);
            startActivity(intent);


        }
        else{
            Toast.makeText(this, "You are not a landlord", Toast.LENGTH_SHORT).show();
        }

    }
}