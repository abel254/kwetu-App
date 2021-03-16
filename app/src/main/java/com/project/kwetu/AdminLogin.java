package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {

    private EditText usernameEdt, passwordEdt;
    DatabaseReference reference;
    private Button loginBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        usernameEdt = findViewById(R.id.log_email);
        passwordEdt = findViewById(R.id.log_pass);
        loginBtn = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinAdmin();
            }
        });

    }


    private void signinAdmin(){
        String username = usernameEdt.getText().toString().trim();
        final String password = passwordEdt.getText().toString().trim();

        if (username.isEmpty()){
            usernameEdt.setError("Field cannot be empty");
            usernameEdt.requestFocus();
            return;
        }

        if (password.isEmpty()){
            passwordEdt.setError("Field cannot be empty");
            passwordEdt.requestFocus();
            return;
        }

        if (password.length() < 6){
            passwordEdt.setError("short password");
            passwordEdt.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        reference = FirebaseDatabase.getInstance().getReference("LandloardAuth");
        reference.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.exists()){
                        String pass = snapshot.child("password").getValue(String.class);
                        if (pass.equals(password)){
                            startActivity(new Intent(AdminLogin.this, AdminMainPage.class));
                            finish();
                            progressBar.setVisibility(View.GONE);
                        }else {
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Wrong username", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
