package com.example.deninternship_week5;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.deninternship_week5.Quiz.MainFunctionality.QuizWelcome;
import com.example.deninternship_week5.TaskManager.TaskActivityFiles.TaskMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashBoardActivity extends AppCompatActivity {
    TextView tvWelcome;
    CardView cardQuiz, cardTasks, cardChat, cardProfile;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dashboard);
        tvWelcome = findViewById(R.id.tvWelcome);
        cardQuiz = findViewById(R.id.cardQuiz);
        cardTasks = findViewById(R.id.cardTasks);
        cardChat = findViewById(R.id.cardChat);
        cardProfile = findViewById(R.id.cardProfile);
        btnExit = findViewById(R.id.btnExit);
        userRef = FirebaseDatabase.getInstance().getReference("users");



        mAuth = FirebaseAuth.getInstance();


//        String username = getIntent().getStringExtra("USERNAME");

//        if (username != null && !username.isEmpty()) {
//            tvWelcome.setText("Welcome, " + username + "!");
//        } else {
//            tvWelcome.setText("Welcome, User!");
//        }

        // ---- Set Click Listeners for Cards ----
        cardQuiz.setOnClickListener(view -> {
            Intent intent = new Intent(DashBoardActivity.this, QuizWelcome.class);
            startActivity(intent);
        });

        cardTasks.setOnClickListener(view -> {
            Intent intent = new Intent(DashBoardActivity.this, TaskMainActivity.class);
           startActivity(intent);
        });

        cardChat.setOnClickListener(view -> {
            Intent intent = new Intent(DashBoardActivity.this, ChatListActivity.class);
           startActivity(intent);
        });

        cardProfile.setOnClickListener(view -> {
         Intent intent = new Intent(DashBoardActivity.this, ProfileActivity.class);
           startActivity(intent);
        });

        // ---- Exit Button ----
        btnExit.setOnClickListener(v -> {
            finishAffinity();  // Close all activities
            System.exit(0);   // Exit the app completely
        });
    }
}
