//package com.example.deninternship_week5;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.deninternship_week5.Entity.User;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class ProfileActivity extends AppCompatActivity {
//
//    private TextView tvName, tvEmail, tvQuizHistory, tvTaskCount;
//    private Button btnEditProfile;
//    private FirebaseAuth mAuth;
//    private DatabaseReference userRef, quizRef, taskRef;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.profile);
//
//        tvName = findViewById(R.id.tvName);
//        tvEmail = findViewById(R.id.tvEmail);
//        tvQuizHistory = findViewById(R.id.tvQuizHistory);
//        tvTaskCount = findViewById(R.id.tvTaskCount);
//
//
//        mAuth = FirebaseAuth.getInstance();
//        String uid = mAuth.getCurrentUser().getUid();
//
//        userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);
//        quizRef = FirebaseDatabase.getInstance().getReference("quizResults").child(uid);
//        taskRef = FirebaseDatabase.getInstance().getReference("tasks").child(uid);
//
//        // Load user info
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//                if (user != null) {
//                    tvName.setText(user.getName());
//                    tvEmail.setText(user.getEmail());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {}
//        });
//
//        // Load quiz history
//        quizRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                long count = snapshot.getChildrenCount();
//                tvQuizHistory.setText(count + " Completed");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {}
//        });
//
//        // Load tasks count
//        taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                long count = snapshot.getChildrenCount();
//                tvTaskCount.setText(count + " Pending");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {}
//        });
//
//
//    }
//}


package com.example.deninternship_week5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deninternship_week5.Entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvQuizHistory, tvTaskCount;
    private Button btnEditProfile;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvQuizHistory = findViewById(R.id.tvQuizHistory);
        tvTaskCount = findViewById(R.id.tvTaskCount);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

        // Load user profile
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(ProfileActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Name & Email
                String name = snapshot.child("name").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                tvName.setText(name != null ? name : "Unknown User");
                tvEmail.setText(email != null ? email : "No Email");

                // Quiz History Count
//                DataSnapshot quizHistorySnap = snapshot.child("quizHistory");
//                long quizCount = quizHistorySnap.exists() ? quizHistorySnap.getChildrenCount() : 0;
//                tvQuizHistory.setText(quizCount + " Completed");
                DataSnapshot quizHistorySnap = snapshot.child("quizHistory");

                if (quizHistorySnap.exists()) {
                    System.out.println("QuizHistory exists. Children: " + quizHistorySnap.getChildrenCount());
                    for (DataSnapshot child : quizHistorySnap.getChildren()) {
                        System.out.println("Quiz key: " + child.getKey());
                    }
                } else {
                    System.out.println("No quiz history found at: " + snapshot.getRef().toString());
                }

                long quizCount = quizHistorySnap.getChildrenCount();
                tvQuizHistory.setText(quizCount + " Completed");


                // Task Count (Pending)
                DataSnapshot tasksSnap = snapshot.child("tasks");
                long pendingCount = 0;
                if (tasksSnap.exists()) {
                    for (DataSnapshot task : tasksSnap.getChildren()) {
                        Boolean completed = task.child("completed").getValue(Boolean.class);
                        if (completed != null && !completed) {
                            pendingCount++;
                        }
                    }
                }
                tvTaskCount.setText(pendingCount + " Pending");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
