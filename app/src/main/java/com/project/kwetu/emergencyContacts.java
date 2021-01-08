package com.project.kwetu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class emergencyContacts extends AppCompatActivity {

    EditText contactFire, medicalContact, policeContact;
    Button updateEmergencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);


        contactFire = findViewById(R.id.contact_fire);
        medicalContact = findViewById(R.id.medical_contact);
        policeContact = findViewById(R.id.police_contact);
        updateEmergencies = findViewById(R.id.update_emergencies);
    }

    public void update(View view){


    }
}