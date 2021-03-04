package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


    private Boolean validateFire() {
        String val = contactFire.getText().toString();
        if(val.isEmpty()){
            contactFire.setError("Field cannot be empty");
            return false;
        }
        else {
            contactFire.setError(null);
            return true;
        }
    }
    private Boolean validateMedical() {
        String val = medicalContact.getText().toString();
        if (val.isEmpty()) {
            medicalContact.setError("Field cannot be empty");
            return false;
        } else {
            medicalContact.setError(null);
            return true;
        }
    }

    private Boolean validatePolice() {
        String val = policeContact.getText().toString();
        if (val.isEmpty()) {
            policeContact.setError("Field cannot be empty");
            return false;
        } else {
            policeContact.setError(null);
            return true;
        }
    }

    public void update(View view){
        if (!validateFire() | !validateMedical()  | !validatePolice()) {
            return;
        }

        getCurrentContacts();

    }

    private void getCurrentContacts() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Emergency");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String fire = ds.child("fire").getValue().toString();
                    String ambulance = ds.child("ambulance").getValue().toString();
                    String police = ds.child("police").getValue().toString();

                    contactFire.setText(fire);
                    medicalContact.setText(ambulance);
                    policeContact.setText(police);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("emergencyContacts", "Error occurred");

            }
        });

    }
}