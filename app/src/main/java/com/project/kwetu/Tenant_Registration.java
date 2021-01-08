package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.kwetu.model.Users;

public class Tenant_Registration extends AppCompatActivity {

    private EditText mTenantName;
    private EditText mTenantEmail;
    private EditText mHouseNumber;
    private EditText mPhoneNumber;
    private EditText mPass1;
    private EditText mPass2;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant__registration);
        mTenantName = findViewById(R.id.tenant_name);
        mTenantEmail = findViewById(R.id.tenant_email);
        mTenantEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateName();
            }
        });
        mHouseNumber = findViewById(R.id.house_number);
        mHouseNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();
            }
        });
        mPhoneNumber = findViewById(R.id.phone_number);
        mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateHouse();
            }
        });
        mPass1 = findViewById(R.id.password_tenant);
        mPass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatePhone();
            }
        });
        mPass2 = findViewById(R.id.password_tenant2);
        mPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatePass();
            }
        });
        Button reg = findViewById(R.id.ten_reg_btn);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUserWithEmailAndPassword();
                writeTenantInfo();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tenant Registration");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    private Boolean validateName() {
        String val = mTenantName.getText().toString();
        if(val.isEmpty()){
            mTenantName.setError("Fill in the tenant's name");
            return false;
        }
        else {
            mTenantName.setError(null);
            return true;
        }

    }
    private Boolean validateEmail() {
        String val =   mTenantEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            mTenantEmail.setError("Fill in the email");
            return false;
        }else if(!val.matches(emailPattern)){
            mTenantEmail.setError("Invalid Email address");
            return false;
        }

        else {
            mTenantEmail.setError(null);
            return true;
        }
    }
    private Boolean validateHouse() {
        String val = mHouseNumber.getText().toString();
        if(val.isEmpty()){
            mHouseNumber.setError("Fill in the tenant's house number");
            return false;
        }
        else {
            mHouseNumber.setError(null);
            return true;
        }
    }
    private Boolean validatePhone() {
        String val = mPhoneNumber.getText().toString();
        String phonePattern = "[0-9._-]";
        if(val.isEmpty()){
            mPhoneNumber.setError("Fill in the phone number");
            return false;
        }
        else if (!val.matches(phonePattern)){
            mPhoneNumber.setError("Invalid phone number");
            return false;
        }
        else{
            mPhoneNumber.setError(null);
            return true;
        }
    }
    private Boolean validatePass() {
        String val = mPass1.getText().toString();
        if(val.isEmpty()){
            mPass1.setError("Password is missing");
            return false;
        }
        else {
            mPass1.setError(null);
            return true;
        }
    }
    private Boolean validatePass2() {
        String val = mPass2.getText().toString();
        String here = mPass1.getText().toString();
        if(val.isEmpty()){
            mPass2.setError("Password confirmation is missing.");
            return false;
        }
        else  if(!val.matches(here)){
            mPass2.setError("Your password do not match");
            return false;
        }
        else {
            mPass2.setError(null);
            return true;
        }
    }
    private void createUserWithEmailAndPassword() {
        if (!validateName() | !validateEmail()  | !validateHouse()|  !validatePhone() | !validatePass() | !validatePass2()) {
            return;
        }

        final Dialog dialog = new Dialog(Tenant_Registration.this);
        dialog.setContentView(R.layout.activity_reg_dialog);
        dialog.setCancelable(true);

        String email = mTenantEmail.getText().toString();
        String password = mPass1.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            Log.d(TAG, "User registered");
                            Toast.makeText(Tenant_Registration.this, "User registered successful", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else{
                            Log.w(TAG, "User registration failed", task.getException());
                            Toast.makeText(Tenant_Registration.this, "Authentication failed",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }
    private void writeTenantInfo() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Tenants");
        String tenant_name =  mTenantName.getText().toString();
        String email = mTenantEmail.getText().toString();
        String house_number = mHouseNumber.getText().toString();
        String phone_number = mPhoneNumber.getText().toString();
        String password = mPass1.getText().toString();
        Users helper = new Users(tenant_name, email,house_number,phone_number,password);
        myRef.push().setValue(helper);

    }


    private void updateUI(FirebaseUser user) {
        if(user != null){
            Toast.makeText(this,"You have registered",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Admin_Registration.class));
        }else {
            Toast.makeText(this,"You're already registered using this email. Try another email ",Toast.LENGTH_LONG).show();

        }

    }

}