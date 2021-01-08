package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.kwetu.model.Users;
import com.squareup.picasso.Picasso;

public class user_profile extends AppCompatActivity {

    private EditText mInputName;
    private EditText mInputPhone;
    private EditText mInputEmail;
    private EditText mInputPass;
    ImageView avatar;
    Button signOut;
    FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private EditText mInputHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mInputName = findViewById(R.id.input_name);
        mInputPhone = findViewById(R.id.input_phone);
        mInputHouse = findViewById(R.id.input_house);
        mInputEmail = findViewById(R.id.input_email);
        mInputPass = findViewById(R.id.input_password);
        avatar = findViewById(R.id.profile_image);
        signOut = findViewById(R.id.btn_logout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
        getUserAccountsData();

    }

    private void getUserAccountsData() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        
        Query query = myRef.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String name = ""+ ds.child("tenant_name").getValue();
                    String email = ""+ ds.child("email").getValue();
                    String house_number = ""+ ds.child("house_number").getValue();
                    String phone_number = ""+ ds.child("phone_number").getValue();
                    String password = ""+ ds.child("password").getValue();
                    String image = ""+ ds.child("image").getValue();


                    mInputName.setText(name);
                    mInputPhone.setText(phone_number);
                    mInputHouse.setText(house_number);
                    mInputEmail.setText(email);
                    mInputPass.setText(password);

                    try{
                        Picasso.get().load(image).into(avatar);

                    }
                    catch(Exception e){
                        Picasso.get().load(R.drawable.ic_face).into(avatar);
                        
                    }


                }
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void logOut() {
        mAuth.signOut();
        finish();

    }
}