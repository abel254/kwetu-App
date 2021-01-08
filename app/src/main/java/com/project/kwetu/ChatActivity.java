 package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.kwetu.Adapters.AdapterChat;
import com.project.kwetu.model.ChatMessage;
import com.project.kwetu.model.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

 public class ChatActivity extends AppCompatActivity {

    Toolbar mToolbar;
    RecyclerView mView;
    ImageView profileC;
    TextView nameChat, statusUser;
    EditText messageEt;
    ImageButton sendBtn;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    ValueEventListener seenListener;
    DatabaseReference userRefForSeen;

    List<ChatMessage> chatList;
    AdapterChat adapterchat;
    
    String myUID;
    String herUid;
    String herImage;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //init views
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");


        mView = findViewById(R.id.chat_recyclerView);
        profileC = findViewById(R.id.profile_c);
        nameChat = findViewById(R.id.nameChat);
        statusUser = findViewById(R.id.statusUser);
        messageEt = findViewById(R.id.messageEt);
        sendBtn = findViewById(R.id.sendBtn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        
        mView.setHasFixedSize(true);
        mView.setLayoutManager(linearLayoutManager);
        
        Intent intent = getIntent();
        herUid = intent.getStringExtra("herUid");
        

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        Query userQuery = myRef.orderByChild("uid").equalTo(herUid);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String name = ""+ ds.child("tenant_name").getValue();
                    herImage = ""+ ds.child("image").getValue();
                    
                    nameChat.setText(name);
                    try{
                        Picasso.get().load(herImage).placeholder(R.drawable.ic_face).into(profileC);
                    }
                    catch(Exception e){
                        Picasso.get().load(R.drawable.ic_face).into(profileC);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageEt.getText().toString().trim();
                if(TextUtils.isEmpty(message)){
                    Toast.makeText(ChatActivity.this,"Cannot send empty message...",Toast.LENGTH_SHORT).show();
                }
                else{
                    sendMessage(message);
                }
            }
        });

        readMessages();
        seenMessages();
    }

     private void seenMessages() {
        userRefForSeen = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = userRefForSeen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    ChatMessage chat = ds.getValue(ChatMessage.class);
                    
                    if(chat.getReceiver().equals(myUID)  && chat.getSender().equals(herUid)){
                        HashMap<String, Object> hasSeenHashMap = new HashMap<>();
                        hasSeenHashMap.put("isSeen", true);

                        ds.getRef().updateChildren(hasSeenHashMap);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
     }

     private void readMessages() {
        chatList = new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for(DataSnapshot ds: snapshot .getChildren()){
                    ChatMessage chat = ds.getValue(ChatMessage.class);
                    if(chat.getReceiver().equals(myUID) &&chat.getSender().equals(herUid) || chat.getReceiver().equals(herUid) &&chat.getSender().equals(myUID) ){
                        chatList.add(chat);
                    }
                    adapterchat = new AdapterChat(ChatActivity.this, chatList, herImage);
                    adapterchat.notifyDataSetChanged();

                    mView.setAdapter(adapterchat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
     }

     private void sendMessage(String message) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        
         String timestamp = String.valueOf(System.currentTimeMillis());

         HashMap<String, Object> hashmap = new HashMap();
         hashmap.put("sender ", myUID);
         hashmap.put("receive", herUid);
         hashmap.put("message", message);
         hashmap.put("timestamp", timestamp);
         hashmap.put("isSeen", false);
         myRef.child("Chats").push().setValue(hashmap);

         messageEt.setText("");
     }

     private void checkUserStatus(){

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser != null){
            myUID = firebaseUser.getUid();
        
        }
        else{
            startActivity(new Intent(this, First_page.class));
            finish();
        }
    }

     @Override
     protected void onStart() {
        checkUserStatus();
         super.onStart();
     }

     @Override
     protected void onPause() {

         super.onPause();
         userRefForSeen.removeEventListener(seenListener);
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         return super.onCreateOptionsMenu(menu);
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.card_Logout){
            mAuth.signOut();
            checkUserStatus();}
         return super.onOptionsItemSelected(item);
     }
 }