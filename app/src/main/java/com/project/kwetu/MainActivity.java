package com.project.kwetu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.google.android.gms.common.internal.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int SPLASH_DISPLAY_LENGTH = 1000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
                {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, First_page.class);
                    startActivity(intent);
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

        mAuth = FirebaseAuth.getInstance();



    }
    private void checkUserStatus(){
        FirebaseUser user  = mAuth.getCurrentUser();
        if (user != null){
            startActivity(new Intent(MainActivity.this, First_page.class));
        }
        else {
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }

    }
    protected void onStart() {

        checkUserStatus();

        super.onStart();
    }




    }
