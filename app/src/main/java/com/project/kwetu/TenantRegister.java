package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.project.kwetu.model.Tenant;
import com.project.kwetu.model.User;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TenantRegister extends AppCompatActivity {
    private EditText nameEdt, emailEdt, houseNumberEdt, idNumberEdt, phoneNumberEdt, passwordEdt;
    private String name, email, houseNumber, idNumber, phoneNumber, password;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button attachment;
    private ImageView imageView;
    public  final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant__registration);

        nameEdt = findViewById(R.id.tenant_name);
        emailEdt = findViewById(R.id.tenant_email);
        houseNumberEdt = findViewById(R.id.tenant_house_number);
        idNumberEdt = findViewById(R.id.tenant_id);
        phoneNumberEdt = findViewById(R.id.tenant_phone);
        passwordEdt = findViewById(R.id.tenant_password);
        registerBtn = findViewById(R.id.btn_tenant_register);
        progressBar = findViewById(R.id.progressBar);
        attachment = findViewById(R.id.attachment_btn);
        imageView = findViewById(R.id.take_photo);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });

        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        TenantRegister.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else {
                Toast.makeText(getApplicationContext(), "You dont have permission to file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && requestCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void registerUser() {
        name = nameEdt.getText().toString().trim();
        email = emailEdt.getText().toString().trim();
        houseNumber = houseNumberEdt.getText().toString().trim();
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

        if (houseNumber.isEmpty()){
            houseNumberEdt.setError("field cannot be empty");
            houseNumberEdt.requestFocus();
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
                    Tenant tenant = new Tenant(name, email, houseNumber, phoneNumber, idNumber, password);

                    FirebaseDatabase.getInstance().getReference("TenantDetails")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(tenant).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Tenant has been registered successfully", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                startActivity(new Intent(TenantRegister.this, AdminMainPage.class));
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