package com.example.deninternship_week5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deninternship_week5.Adapter.UserAdapter;
import com.example.deninternship_week5.Entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener {

    private RecyclerView rvUsers;
    private UserAdapter userAdapter;
    private ArrayList<User> userList = new ArrayList<>();

    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        rvUsers = findViewById(R.id.rvUsers);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(userList, this);
        rvUsers.setAdapter(userAdapter);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("users");

        loadUsers();
    }

    private void loadUsers() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user != null && !user.getUid().equals(mAuth.getCurrentUser().getUid())) {
                        userList.add(user);
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




//    public void onUserClick(User user) {
//        Intent intent = new Intent(ChatListActivity.this, ChatDetailActivity.class);
//        intent.putExtra("uid", user.getUid());
//        intent.putExtra("name", user.getName());
//        startActivity(intent);
//    }
    @Override
    public void onUserClick(User user) {
        Intent intent = new Intent(ChatListActivity.this, ChatDetailActivity.class);
        intent.putExtra("receiverId", user.getUid());      // ✅ fixed
        intent.putExtra("receiverName", user.getName());   // ✅ fixed
        startActivity(intent);
    }

}
