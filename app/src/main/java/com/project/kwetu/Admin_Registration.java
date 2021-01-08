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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.kwetu.model.User;

public class Admin_Registration extends AppCompatActivity {

    private EditText mAdminName;
    private EditText mAdminEmail;
    private EditText mBuildingName;
    private EditText mAdminId;
    private EditText mPhoneNum;
    private EditText mPassA;
    private EditText mPassB;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__registration);
        mAdminName = findViewById(R.id.admin_name);
        mAdminEmail = findViewById(R.id.admin_email);
        mAdminEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateName();
            }
        });
        mBuildingName = findViewById(R.id.building_name);
        mBuildingName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();
            }
        });
        mAdminId = findViewById(R.id.admin_id);
        mAdminId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateBuilding();
            }
        });
        mPhoneNum = findViewById(R.id.phone_number_a);
        mPhoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateId();
            }
        });
        mPassA = findViewById(R.id.password_admin);
        mPassA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatePhone();
            }
        });
        mPassB = findViewById(R.id.password_admin2);
        mPassB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatePass();
            }
        });
        Button regA = findViewById(R.id.ten_adm_btn);
        regA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
                writeAdminDetails();
            }
        });
        TextView textAlready = findViewById(R.id.text_already);
        textAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Registration.this, Login.class);
                startActivity(intent);
            }
        });


    }


    private boolean validateName() {
        String val = mAdminName.getText().toString();
        if(val.isEmpty()){
            mAdminName.setError("Fill in your name");
            return false;
        }
        else {
            mAdminName.setError(null);
            return true;
        }
    }
    private boolean validateEmail() {
        String val =   mAdminEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            mAdminEmail.setError("Fill in the email");
            return false;
        }else if(!val.matches(emailPattern)){
            mAdminEmail.setError("Invalid Email address");
            return false;
        }

        else {
            mAdminEmail.setError(null);
            return true;
        }
    }
    private boolean validateBuilding() {
        String val =  mBuildingName.getText().toString();
        if(val.isEmpty()){
            mBuildingName.setError("Fill in the building's name");
            return false;
        }
        else {
            mBuildingName.setError(null);
            return true;
        }
    }
    private boolean validateId(){
        String val =  mAdminId.getText().toString();
        if(val.isEmpty()){
            mAdminId.setError("Fill in your ID");
            return false;
        }
        else {
            mAdminId.setError(null);
            return true;
        }
    }
    private boolean validatePhone() {
        String val = mPhoneNum.getText().toString();
        String phonePattern = "[0-9._-]";
        if(val.isEmpty()){
            mPhoneNum.setError("Fill in the phone number");
            return false;
        }

        else{
            mPhoneNum.setError(null);
            return true;
        }
    }
    private boolean validatePass() {
        String val = mPassA.getText().toString();
        if(val.isEmpty()){
            mPassA.setError("Password is missing");
            return false;
        }
        else {
            mPassA.setError(null);
            return true;
        }
    }
    private boolean validatePass2() {
        String val = mPassB.getText().toString();
        String here = mPassA.getText().toString();
        if(val.isEmpty()){
            mPassB.setError("Password confirmation is missing.");
            return false;
        }
        else  if(!val.matches(here)){
            mPassB.setError("Your password do not match");
            return false;
        }
        else {
            mPassB.setError(null);
            return true;
        }
    }

    private void signUp() {
        if (!validateName() | !validateEmail()  | !validateBuilding() |!validateId()|  !validatePhone() | !validatePass() | !validatePass2()) {
            return;
        }

        final Dialog dialog = new Dialog(Admin_Registration.this);
        dialog.setContentView(R.layout.activity_reg_dialog);
        dialog.setCancelable(true);

        String email = mAdminEmail.getText().toString();
        String password = mPassA.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete" +task.isSuccessful());
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            Log.d(TAG, "Registered");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else{
                            Log.w(TAG, "Registration failed", task.getException());
                            Toast.makeText(Admin_Registration.this, "Authentication failed",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            startActivity(new Intent(this,Login.class));

                }else {
                    Toast.makeText(this,"Error! Try again",Toast.LENGTH_LONG).show();

                }


    }

    private void writeAdminDetails(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Landlord");

        String name = mAdminName.getText().toString();
        String email = mAdminEmail.getText().toString();
        String house = mBuildingName.getText().toString();
        String ID = mAdminId.getText().toString();
        String phone = mPhoneNum.getText().toString();
        String password = mPassA.getText().toString();

        User helper = new User(name, email, house, ID,  phone, password);
        myRef.push().setValue(helper);
    }
}