//package com.example.deninternship_week5;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.deninternship_week5.Adapter.UserAdapter;
//import com.example.deninternship_week5.Entity.User;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class CreateGroupActivity extends AppCompatActivity {
//    private EditText etGroupName;
//    private RecyclerView rvSelectUsers;
//    private Button btnCreate;
//    private ArrayList<User> userList = new ArrayList<>();
//    private ArrayList<String> selectedUserIds = new ArrayList<>();
//    private UserAdapter userAdapter;
//    private DatabaseReference groupRef;
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.creategroup);
//
//        etGroupName = findViewById(R.id.etGroupName);
//        rvSelectUsers = findViewById(R.id.rvSelectUsers);
//        btnCreate = findViewById(R.id.btnCreateGroupFinal);
//
//        rvSelectUsers.setLayoutManager(new LinearLayoutManager(this));
//        userAdapter = new UserAdapter(userList, user -> {
//            if (selectedUserIds.contains(user.getUid())) {
//                selectedUserIds.remove(user.getUid());
//            } else {
//                selectedUserIds.add(user.getUid());
//            }
//        });
//        rvSelectUsers.setAdapter(userAdapter);
//
//        mAuth = FirebaseAuth.getInstance();
//        groupRef = FirebaseDatabase.getInstance().getReference("groups");
//
//        loadUsers();
//
//        btnCreate.setOnClickListener(v -> createGroup());
//    }
//
//    private void loadUsers() {
//        FirebaseDatabase.getInstance().getReference("users")
//                .get().addOnSuccessListener(snapshot -> {
//                    userList.clear();
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//                        User user = ds.getValue(User.class);
//                        if (user != null && !user.getUid().equals(mAuth.getCurrentUser().getUid())) {
//                            userList.add(user);
//                        }
//                    }
//                    userAdapter.notifyDataSetChanged();
//                });
//    }
//
//    private void createGroup() {
//        String groupName = etGroupName.getText().toString().trim();
//        if (TextUtils.isEmpty(groupName)) {
//            Toast.makeText(this, "Enter a group name", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String groupId = groupRef.push().getKey();
//        Map<String, Object> groupData = new HashMap<>();
//        groupData.put("groupName", groupName);
//        groupData.put("createdBy", mAuth.getCurrentUser().getUid());
//
//        Map<String, Boolean> members = new HashMap<>();
//        members.put(mAuth.getCurrentUser().getUid(), true);
//        for (String uid : selectedUserIds) {
//            members.put(uid, true);
//        }
//        groupData.put("members", members);
//
//        groupRef.child(groupId).setValue(groupData)
//                .addOnSuccessListener(aVoid ->
//                        Toast.makeText(this, "Group created successfully", Toast.LENGTH_SHORT).show()
//                )
//                .addOnFailureListener(e ->
//                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
//                );
//    }
//}



package com.example.deninternship_week5;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

public class CreateGroupActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener {

    private EditText etGroupName;
    private RecyclerView rvUsers;
    private Button btnCreateGroup;
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<String> selectedUserIds = new ArrayList<>();
    private UserAdapter userAdapter;

    private DatabaseReference userRef, groupRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategroup);

        etGroupName = findViewById(R.id.etGroupName);
        rvUsers = findViewById(R.id.rvSelectUsers);
        btnCreateGroup = findViewById(R.id.btnCreateGroup);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("users");
        groupRef = FirebaseDatabase.getInstance().getReference("groups");

        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList, this);
        rvUsers.setAdapter(userAdapter);

        loadUsers();

        btnCreateGroup.setOnClickListener(v -> createGroup());
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
                Toast.makeText(CreateGroupActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createGroup() {
        String groupName = etGroupName.getText().toString().trim();
        if (TextUtils.isEmpty(groupName)) {
            Toast.makeText(this, "Enter a group name!", Toast.LENGTH_SHORT).show();
            return;
        }

        String groupId = groupRef.push().getKey();
        if (groupId == null) return;

        Map<String, Object> groupData = new HashMap<>();
        groupData.put("groupName", groupName);

        Map<String, Boolean> members = new HashMap<>();
        members.put(mAuth.getCurrentUser().getUid(), true); // Add current user
        for (String userId : selectedUserIds) {
            members.put(userId, true);
        }
        groupData.put("members", members);

        groupRef.child(groupId).setValue(groupData)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Group Created!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    public void onUserClick(User user) {
        if (selectedUserIds.contains(user.getUid())) {
            selectedUserIds.remove(user.getUid());
        } else {
            selectedUserIds.add(user.getUid());
        }
    }
}
