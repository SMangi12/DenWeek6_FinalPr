package com.example.deninternship_week5.Quiz.MainFunctionality;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.ComponentActivity;
import androidx.compose.ui.platform.ComposeView;


import com.example.deninternship_week5.Quiz.jetUi.ComposeBridge;
import com.example.deninternship_week5.Quiz.jetUi.FinalScoreActivity;
import com.example.deninternship_week5.Quiz.jetUi.Question;
import com.example.deninternship_week5.Quiz.jetUi.QuestionData;

import java.util.Collections;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


//change 1
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class QuizActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getIntent().getStringExtra("USERNAME");
        String topic = getIntent().getStringExtra("TOPIC");
        List<Question> questions;
        switch (topic) {
            case "Computer Networks":
                questions = QuestionData.INSTANCE.getComputerNetworksQuestions();
                break;
            case "Data Structures and Algorithms":
                questions = QuestionData.INSTANCE.getDsaQuestions();
                break;
            case "Android Development":
                questions = QuestionData.INSTANCE.getAndroidDevelopmentQuestions();
                break;
            default:
                questions = QuestionData.INSTANCE.getComputerNetworksQuestions();
        }
        Collections.shuffle(questions);
        List<Question> selectedQuestions = questions.subList(0, 5);
        ComposeView composeView = ComposeBridge.createComposeView(
                this,
                selectedQuestions,
                name,
//                new Function1<Integer, Unit>() {
//                    @Override
//                    public Unit invoke(Integer score) {
//                        Intent intent = new Intent(QuizActivity.this, FinalScoreActivity.class);
//                        intent.putExtra("USERNAME", name);
//                        intent.putExtra("SCORE", score);
//                        startActivity(intent);
//                        finish(); // optional
//                        return Unit.INSTANCE;
//                    }
//                }

                //change 3
                new Function1<Integer, Unit>() {
                    @Override
                    public Unit invoke(Integer score) {
                        // Save quiz record to Firebase before moving to result screen
                        saveQuizRecord(topic, score);

                        Intent intent = new Intent(QuizActivity.this, FinalScoreActivity.class);
                        intent.putExtra("USERNAME", name);
                        intent.putExtra("SCORE", score);
                        startActivity(intent);
                        finish();
                        return Unit.INSTANCE;
                    }
                }

        );
        FrameLayout container = new FrameLayout(this);
        container.addView(composeView);
        setContentView(container);
    }


    //change 2
     private void saveQuizRecord(String topic, int score) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return; // No user logged in
        }

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid)
                .child("quizHistory");

        // Get current timestamp for unique ID and readable date
        String quizId = "quiz_" + System.currentTimeMillis();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Prepare quiz record
        HashMap<String, Object> quizRecord = new HashMap<>();
        quizRecord.put("id", quizId);
        quizRecord.put("topic", topic);
        quizRecord.put("score", score);
        quizRecord.put("date", date);

        // Push new quiz attempt into Firebase under quizHistory
        userRef.push().setValue(quizRecord)
                .addOnSuccessListener(unused ->
                        System.out.println("Quiz record saved successfully"))
                .addOnFailureListener(e ->
                        System.out.println("Failed to save quiz record: " + e.getMessage()));
    }

}