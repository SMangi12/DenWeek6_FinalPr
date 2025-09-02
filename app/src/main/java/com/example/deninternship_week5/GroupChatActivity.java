package com.example.deninternship_week5;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deninternship_week5.Adapter.MessageAdapter;
import com.example.deninternship_week5.Entity.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity {

    private TextView tvGroupName;
    private RecyclerView recyclerMessages;
    private EditText etMessage;
    private Button btnSend;

    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    private DatabaseReference groupRef;
    private FirebaseAuth auth;

    private String groupId, groupName, senderId, senderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupchat);

        tvGroupName = findViewById(R.id.tvGroupName);
        recyclerMessages = findViewById(R.id.recyclerGroupMessages);
        etMessage = findViewById(R.id.etGroupMessage);
        btnSend = findViewById(R.id.btnSendGroup);

        auth = FirebaseAuth.getInstance();
        senderId = auth.getCurrentUser().getUid();
        senderName = auth.getCurrentUser().getDisplayName(); // fallback for group messages

        // get group info from intent
        groupId = getIntent().getStringExtra("groupId");
        groupName = getIntent().getStringExtra("groupName");

        tvGroupName.setText(groupName);

        messageList = new ArrayList<>();
        recyclerMessages.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(this, messageList, senderId);
        recyclerMessages.setAdapter(messageAdapter);

        groupRef = FirebaseDatabase.getInstance().getReference("groups").child(groupId).child("messages");

        btnSend.setOnClickListener(v -> {
            String msg = etMessage.getText().toString().trim();
            if (!TextUtils.isEmpty(msg)) {
                sendGroupMessage(msg);
                etMessage.setText("");
            } else {
                Toast.makeText(this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
            }
        });

        readGroupMessages();
    }

    private void sendGroupMessage(String messageText) {
        String messageId = groupRef.push().getKey();
        Message message = new Message(senderId, senderName, messageText, System.currentTimeMillis());

        groupRef.child(messageId).setValue(message)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to send: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void readGroupMessages() {
        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Message msg = ds.getValue(Message.class);
                    if (msg != null) messageList.add(msg);
                }
                messageAdapter.notifyDataSetChanged();
                recyclerMessages.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GroupChatActivity.this, "Error loading messages", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
