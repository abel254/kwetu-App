package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.kwetu.model.User;

public class AdminRegistration extends AppCompatActivity {

    private EditText nameEdt, emailEdt, houseNameEdt, idNumberEdt, phoneNumberEdt, passwordEdt;
    private String name, email, houseName, idNumber, phoneNumber, password;
    private TextView adminLogin;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__registration);

        nameEdt = findViewById(R.id.admin_name);
        emailEdt = findViewById(R.id.email);
        houseNameEdt = findViewById(R.id.house_name);
        idNumberEdt = findViewById(R.id.id_number);
        phoneNumberEdt = findViewById(R.id.admin_phone);
        passwordEdt = findViewById(R.id.admin_password);
        registerBtn = findViewById(R.id.btn_admin_register);
        progressBar = findViewById(R.id.progressBar);
        adminLogin = findViewById(R.id.admin_login);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRegistration.this, AdminLogin.class));
                finish();
            }
        });

    }

    private void registerUser() {

        name = nameEdt.getText().toString().trim();
        email = emailEdt.getText().toString().trim();
        houseName = houseNameEdt.getText().toString().trim();
        idNumber = idNumberEdt.getText().toString().trim();
        phoneNumber = phoneNumberEdt.getText().toString().trim();
        password = passwordEdt.getText().toString().trim();

        if (name.isEmpty()){
            nameEdt.setError("field cannot be empty");
            nameEdt.requestFocus();
            return;
        }

        if (email.isEmpty()){
            emailEdt.setError("field cannot be empty");
            emailEdt.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEdt.setError("invalid email address");
            emailEdt.requestFocus();
            return;
        }

        if (houseName.isEmpty()){
            houseNameEdt.setError("field cannot be empty");
            houseNameEdt.requestFocus();
            return;
        }

        if (email.isEmpty()){
            emailEdt.setError("field cannot be empty");
            emailEdt.requestFocus();
            return;
        }

        if (idNumber.isEmpty()){
            idNumberEdt.setError("field cannot be empty");
            idNumberEdt.requestFocus();
            return;
        }

        if (phoneNumber.isEmpty()){
            phoneNumberEdt.setError("field cannot be empty");
            phoneNumberEdt.requestFocus();
            return;
        }

        if (password.isEmpty()){
            passwordEdt.setError("password is required");
            passwordEdt.requestFocus();
            return;
        }

        if (password.length() < 6){
            passwordEdt.setError("Min password length is 6 characters");
            passwordEdt.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()){
                   User adminDetails = new User(name, email, houseName, idNumber, phoneNumber, password);

                   FirebaseDatabase.getInstance().getReference("Landlord")
                           .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                           .setValue(adminDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                           if (task.isSuccessful()){
                               Toast.makeText(getApplicationContext(), "Landlord has been registered successfully", Toast.LENGTH_SHORT).show();
                               progressBar.setVisibility(View.GONE);

                               startActivity(new Intent(AdminRegistration.this, AdminLogin.class));
                               finish();

                           }else {
                               Toast.makeText(getApplicationContext(), "Failed to Register! Try again!", Toast.LENGTH_SHORT).show();
                               progressBar.setVisibility(View.GONE);
                           }
                       }
                   });
                }else {
                    Toast.makeText(getApplicationContext(), "Failed to Register! Try again!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}