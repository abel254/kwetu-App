package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText mLogEmail;
    private EditText mLogPass;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final String TAG = getClass().getSimpleName();
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLogEmail = findViewById(R.id.log_email);
        mLogPass = findViewById(R.id.log_pass);

        pd = new ProgressDialog(this);
        Button signIn = findViewById(R.id.btn_signIn);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                signInWithEmailAndPassword();
            }
        });
        TextView forgot = findViewById(R.id.forget_text);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNetwork()){
                    resetPassword();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setMessage("Your device is not connected to the internet")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }

            }
        });
        Button adminPage = findViewById(R.id.adminPage);
        adminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Admin_Registration.class);
                startActivity(intent);
            }
        });

    }



    private Boolean validateName(){
        String val = mLogEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String email = mLogEmail.getText().toString().trim();
        if(val.isEmpty()){
            mLogEmail.setError("Field cannot be Empty");
            return false;
        }
        else {
            mLogEmail.setError(null);
            if (email.matches(emailPattern)){

            }
            else {
                Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }
    private Boolean validatePass(){
        String val = mLogPass.getText().toString();
        if(val.isEmpty()){
            mLogPass.setError("Field cannot be empty");
            return false;
        }
        else {
            mLogPass.setError(null);
            return true;
        }
    }

    private void signInWithEmailAndPassword() {
        if(!validateName()| !validatePass()){
            return;
        }



        final String email = mLogEmail.getText().toString();
        String password =  mLogPass.getText().toString();

        pd.setMessage("Log In...");
        pd.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            pd.dismiss();
                            Log.d(TAG, "Sign in successful");
                            FirebaseUser user = mAuth.getCurrentUser();
                            SaveSharedPreference.setUserName(Login.this, "");
                            updateUI(user);
                        }
                        else{
                            Log.w(TAG, "Login failed", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }}
                });
    }

    public void  updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"You've signed in successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,First_page.class));
        }else {
            Toast.makeText(this,"Sign in failed. Check your credentials",Toast.LENGTH_LONG).show();
        }
    }



    private boolean checkNetwork() {
        boolean have_WIFI= false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))if (info.isConnected())have_WIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE DATA"))if (info.isConnected())have_MobileData=true;
        }
        return have_WIFI||have_MobileData;
    }



    private void resetPassword() {
        String email = mLogEmail.getText().toString();


        final ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Verifying..");
        progressDialog.show();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Reset password instructions has sent to your email",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    "Email doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
