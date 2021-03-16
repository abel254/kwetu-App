package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmergencyView extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    CardView cvHealth, cvFire, cvCrime, cvOthers;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_view);

        cvHealth = findViewById(R.id.cardview_health);
        cvFire = findViewById(R.id.cardview_fire);
        cvCrime = findViewById(R.id.cardview_Crime);
        cvOthers = findViewById(R.id.cardview_others);

        cvHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeHealthCall();
            }
        });
        cvFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeFireCall();
            }
        });
        cvCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCrimeCall();
            }
        });
        cvOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeOtherCall();
            }
        });

    }

    public void makeHealthCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(EmergencyView.this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    EmergencyView.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL);
        } else {
            String healthNumber = "tel:" + "0798296901";
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(healthNumber));
            startActivity(intent);
        }
    }

    public void makeFireCall(){
        int permissionCheck = ContextCompat.checkSelfPermission(EmergencyView.this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    EmergencyView.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL);
        } else {
            String fireNumber = "tel:" + "0798296901";
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(fireNumber));
            startActivity(intent);
        }
    }

    public void makeCrimeCall(){
        int permissionCheck = ContextCompat.checkSelfPermission(EmergencyView.this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    EmergencyView.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL);
        } else {
            String crimeNumber = "tel:" + "0798296901";
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(crimeNumber));
            startActivity(intent);
        }
    }

    public void makeOtherCall(){
        int permissionCheck = ContextCompat.checkSelfPermission(EmergencyView.this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    EmergencyView.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL);
        } else {
            reference = FirebaseDatabase.getInstance().getReference("LandlordAuth").child("landload");
            reference.child("phoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String phoneNumber = snapshot.getValue(String.class);

                    String otherNumber = "tel:" + phoneNumber;
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(otherNumber));
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeHealthCall();
                makeFireCall();
                makeCrimeCall();
                makeOtherCall();
            }else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}