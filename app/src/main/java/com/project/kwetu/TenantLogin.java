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

public class TenantLogin extends AppCompatActivity {
    private EditText emailEdt, passwordEdt;
    private Button loginBtn;
    private ProgressBar progressBar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_login);

        emailEdt = findViewById(R.id.login_email);
        passwordEdt = findViewById(R.id.login_pass);
        loginBtn = findViewById(R.id.btn_tenant_login);
        progressBar = findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinUser();
            }
        });
    }

    private void signinUser() {
        String email = emailEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString().trim();

        if (email.isEmpty()){
            emailEdt.setError("Field cannot be empty");
            emailEdt.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEdt.setError("Invalid email address");
            emailEdt.requestFocus();
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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                    FirebaseUser user = mAuth.getCurrentUser();
                    SaveSharedPreference.setUserName(TenantLogin.this, "");
                    updateUI(user);

                }else {
                    Toast.makeText(getApplicationContext(), "Login failed, Check your credentials ", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });

    }

    public void updateUI(FirebaseUser account) {
        if (account != null) {
            startActivity(new Intent(TenantLogin.this, TenantMainPage.class));
            finish();
        }
    }
}