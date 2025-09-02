package com.example.deninternship_week5.TaskManager.TaskActivityFiles;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deninternship_week5.R;
import com.google.firebase.auth.FirebaseAuth;
public class TaskMainActivity extends AppCompatActivity {
//    private FirebaseAuth auth;
    private Button continueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // auth = FirebaseAuth.getInstance();

        // ✅ Check if user is already logged in
//        if (auth.getCurrentUser() != null) {
//            // User already signed in → go to TaskListActivity
//            startActivity(new Intent(TaskMainActivity.this, TaskListActivity.class));
//            finish(); // close splash
//            return;
//        }

        // User is NOT signed in → show splash layout
        setContentView(R.layout.taskmainactivity);
        continueButton = findViewById(R.id.continueButton);

        continueButton.setOnClickListener(v -> {
            // Go to RegisterActivity
            startActivity(new Intent(TaskMainActivity.this, TaskListActivity.class));
            finish();
        });
    }
}