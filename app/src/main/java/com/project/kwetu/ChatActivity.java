 package com.project.kwetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.kwetu.Adapters.ChatAdapter;
import com.project.kwetu.model.ChatModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

 public class ChatActivity extends AppCompatActivity {
     ArrayList<ChatModel> chats;
     RecyclerView recyclerView;
     ChatAdapter adapter;
     ImageButton sendBtn;
     DatabaseReference reference, reference1, referenceFeedBack;
     FirebaseUser user;
     EditText suggestionMessageEdt;
     String senderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chats = new ArrayList<>();

        recyclerView = findViewById(R.id.chat_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        reference = FirebaseDatabase.getInstance().getReference("Suggestions");
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference.keepSynced(true);


        suggestionMessageEdt = findViewById(R.id.message_edt);
        sendBtn = findViewById(R.id.send_button);

        reference1 = FirebaseDatabase.getInstance().getReference("TenantDetails").child(user.getUid());
        reference1.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                senderName = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        referenceFeedBack = FirebaseDatabase.getInstance().getReference("Suggestions");
        referenceFeedBack.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);
                    chats.add(chatModel);

                    adapter = new ChatAdapter(getApplicationContext(), chats);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSuggestion(senderName, suggestionMessageEdt.getText().toString().trim());
            }
        });


    }

    public void addSuggestion(String sender, String suggestion){
        String time = new SimpleDateFormat("hh:mm aaa").format(Calendar.getInstance().getTime());

        ChatModel chatModel = new ChatModel(sender, suggestion, user.getUid(), "no reciever", time);
        reference.child(reference.push().getKey()).setValue(chatModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        suggestionMessageEdt.setText("");
                    }
                });
    }
 }