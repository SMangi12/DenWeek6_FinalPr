package com.example.deninternship_week5.Quiz.MainFunctionality;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.deninternship_week5.Quiz.jetUi.ComposeLauncher;
public class QuizWelcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComposeLauncher.Companion.launch(this);
        finish();
    }
}