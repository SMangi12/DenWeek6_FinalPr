package com.example.deninternship_week5;//package com.example.deninternship_week5;//package com.example.deninternship_week5;
////import android.content.Intent;
////import android.os.Bundle;
////import android.widget.Toast;
////import androidx.annotation.NonNull;
////import androidx.appcompat.app.AppCompatActivity;
////import androidx.recyclerview.widget.LinearLayoutManager;
////import androidx.recyclerview.widget.RecyclerView;
////import com.example.deninternship_week5.Adapter.UserAdapter;
////import com.example.deninternship_week5.Entity.User;
////import com.google.firebase.auth.FirebaseAuth;
////import com.google.firebase.database.*;
////import java.util.ArrayList;
////public class ChatListActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener {
////    private RecyclerView rvUsers;
////    private UserAdapter userAdapter;
////    private ArrayList<User> userList = new ArrayList<>();
////    private DatabaseReference userRef;
////    private FirebaseAuth mAuth;
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_chat_list);
////        rvUsers = findViewById(R.id.rvUsers);
////        rvUsers.setLayoutManager(new LinearLayoutManager(this));
////        userAdapter = new UserAdapter(userList, this);
////        rvUsers.setAdapter(userAdapter);
////        mAuth = FirebaseAuth.getInstance();
////        userRef = FirebaseDatabase.getInstance().getReference("users");
////        loadUsers();
////    }
////
////    private void loadUsers() {
////        userRef.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                userList.clear();
////                for (DataSnapshot ds : snapshot.getChildren()) {
////                    User user = ds.getValue(User.class);
////                    if (user != null && !user.getUid().equals(mAuth.getCurrentUser().getUid())) {
////                        userList.add(user);
////                    }
////                }
////                userAdapter.notifyDataSetChanged();
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                Toast.makeText(ChatListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
////            }
////        });
////    }
////
////
////
////
//////    public void onUserClick(User user) {
//////        Intent intent = new Intent(ChatListActivity.this, ChatDetailActivity.class);
//////        intent.putExtra("uid", user.getUid());
//////        intent.putExtra("name", user.getName());
//////        startActivity(intent);
//////    }
////    @Override
////    public void onUserClick(User user) {
////        Intent intent = new Intent(ChatListActivity.this, ChatDetailActivity.class);
////        intent.putExtra("receiverId", user.getUid());      // ✅ fixed
////        intent.putExtra("receiverName", user.getName());   // ✅ fixed
////        startActivity(intent);
////    }
////
////}
//
//
//
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deninternship_week5.Adapter.GroupAdapter;
import com.example.deninternship_week5.Adapter.UserAdapter;
import com.example.deninternship_week5.Entity.Group;
import com.example.deninternship_week5.Entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
//
//public class ChatListActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener {
//    private RecyclerView rvUsers;
//    private UserAdapter userAdapter;
//    private ArrayList<User> userList = new ArrayList<>();
//    private DatabaseReference userRef;
//    private FirebaseAuth mAuth;
//    private Button btnCreateGroup;
//    private DatabaseReference groupRef;
//    private ArrayList<Group> groupList = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_list);
//
//        rvUsers = findViewById(R.id.rvUsers);
//        rvUsers.setLayoutManager(new LinearLayoutManager(this));
//        userAdapter = new UserAdapter(userList, this);
//        rvUsers.setAdapter(userAdapter);
//
//        btnCreateGroup = findViewById(R.id.createGroup);
//        btnCreateGroup.setOnClickListener(v -> {
//            Intent intent = new Intent(ChatListActivity.this, CreateGroupActivity.class);
//            startActivity(intent);
//        });
//
//        mAuth = FirebaseAuth.getInstance();
//        userRef = FirebaseDatabase.getInstance().getReference("users");
//        loadUsers();
//
//
//        groupRef = FirebaseDatabase.getInstance().getReference("groups");
//        loadGroups();
//    }
//
//    private void loadUsers() {
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                userList.clear();
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    User user = ds.getValue(User.class);
//                    if (user != null && !user.getUid().equals(mAuth.getCurrentUser().getUid())) {
//                        userList.add(user);
//                    }
//                }
//                userAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ChatListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void loadGroups() {
//        groupRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                groupList.clear();
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    Group group = ds.getValue(Group.class);
//                    if (group != null) {
//                        groupList.add(group);
//                    }
//                }
//                // Update group adapter here
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
//    }
//
//    @Override
//    public void onUserClick(User user) {
//        Intent intent = new Intent(ChatListActivity.this, ChatDetailActivity.class);
//        intent.putExtra("receiverId", user.getUid());
//        intent.putExtra("receiverName", user.getName());
//        startActivity(intent);
//    }
//}



public class ChatListActivity extends AppCompatActivity
        implements UserAdapter.OnUserClickListener, GroupAdapter.OnGroupClickListener {

    private RecyclerView rvUsers, rvGroups;
    private UserAdapter userAdapter;
    private GroupAdapter groupAdapter;
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Group> groupList = new ArrayList<>();
    private DatabaseReference userRef, groupRef;
    private FirebaseAuth mAuth;
    private Button btnCreateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        rvUsers = findViewById(R.id.rvUsers);
        rvGroups = findViewById(R.id.rvGroups);

        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvGroups.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(userList, this);
        rvUsers.setAdapter(userAdapter);

        groupAdapter = new GroupAdapter(groupList, this);
        rvGroups.setAdapter(groupAdapter);

        btnCreateGroup = findViewById(R.id.createGroup);
        btnCreateGroup.setOnClickListener(v -> {
            Intent intent = new Intent(ChatListActivity.this, CreateGroupActivity.class);
            startActivity(intent);
        });

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("users");
        groupRef = FirebaseDatabase.getInstance().getReference("groups");

        loadUsers();
        loadGroups();
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

    private void loadGroups() {
        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Group group = ds.getValue(Group.class);
                    if (group != null) {
                        group.setGroupId(ds.getKey());
                        groupList.add(group);
                    }
                }
                groupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void onUserClick(User user) {
        Intent intent = new Intent(ChatListActivity.this, ChatDetailActivity.class);
        intent.putExtra("receiverId", user.getUid());
        intent.putExtra("receiverName", user.getName());
        startActivity(intent);
    }

    @Override
    public void onGroupClick(Group group) {
        Intent intent = new Intent(ChatListActivity.this, GroupChatActivity.class);
        intent.putExtra("groupId", group.getGroupId());
        intent.putExtra("groupName", group.getGroupName());
        startActivity(intent);
    }
}
