package com.example.deninternship_week5;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.deninternship_week5.Adapter.MessageAdapter;
import com.example.deninternship_week5.Entity.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class ChatDetailActivity extends AppCompatActivity {

    private static final String TAG = "ChatDetailActivity";

    TextView tvReceiverName;
    RecyclerView recyclerMessages;
    EditText etMessage;
    Button btnSend;

    MessageAdapter messageAdapter;
    List<Message> messageList;

    DatabaseReference chatRef;
    DatabaseReference userRef;
    FirebaseAuth auth;

    String senderId, receiverId, receiverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate started");
        setContentView(R.layout.activity_chat_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        tvReceiverName = findViewById(R.id.tvReceiverName);
        recyclerMessages = findViewById(R.id.recyclerMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        auth = FirebaseAuth.getInstance();
        senderId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        // get receiver info from intent
        receiverId = getIntent().getStringExtra("receiverId");
        receiverName = getIntent().getStringExtra("receiverName");

        if (receiverName != null) {
            tvReceiverName.setText("Chat with " + receiverName);
        }

        recyclerMessages.setLayoutManager(new LinearLayoutManager(this));
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageList, senderId);
        recyclerMessages.setAdapter(messageAdapter);

        chatRef = FirebaseDatabase.getInstance().getReference("Chats");
        userRef = FirebaseDatabase.getInstance().getReference("users");

        btnSend.setOnClickListener(v -> {
            String msg = etMessage.getText().toString().trim();
            if (!TextUtils.isEmpty(msg) && senderId != null && receiverId != null) {
                sendMessage(senderId, receiverId, msg);
                etMessage.setText("");
            } else {
                Toast.makeText(ChatDetailActivity.this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
            }
        });

        if (senderId != null && receiverId != null) {
            readMessages(senderId, receiverId);
        }
    }

    private void sendMessage(String sender, String receiver, String message) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sender", sender);
        map.put("receiver", receiver);
        map.put("message", message);
        map.put("timestamp", System.currentTimeMillis());

        chatRef.push().setValue(map)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ChatDetailActivity.this, "Message sent!", Toast.LENGTH_SHORT).show();
                    // Notifications handled by Firebase Cloud Function or local notification
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ChatDetailActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void readMessages(final String myId, final String userId) {
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Message msg = ds.getValue(Message.class);
                    if (msg == null) continue;

                    String sender = msg.getSender();
                    String receiver = msg.getReceiver();

                    if (sender == null || receiver == null) continue;

                    if ((sender.equals(myId) && receiver.equals(userId)) ||
                            (sender.equals(userId) && receiver.equals(myId))) {
                        messageList.add(msg);

                        // Show local notification only for messages from the other user
                        if (!sender.equals(myId)) {
                            showLocalNotification("New message from " + receiverName, msg.getMessage());
                        }
                    }
                }
                messageAdapter.notifyDataSetChanged();
                if (!messageList.isEmpty()) {
                    recyclerMessages.scrollToPosition(messageList.size() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void showLocalNotification(String title, String message) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "chat_messages";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Chat Messages", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // replace with your app icon
                .setAutoCancel(true);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
