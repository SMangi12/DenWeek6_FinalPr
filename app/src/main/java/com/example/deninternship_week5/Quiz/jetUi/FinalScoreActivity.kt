package com.example.deninternship_week5.Quiz.jetUi
import ResultScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.deninternship_week5.DashBoardActivity
import com.example.deninternship_week5.Quiz.MainFunctionality.QuizWelcome

class FinalScoreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("USERNAME") ?: "User"
        val score = intent.getIntExtra("SCORE", 0)

        setContent {
            ResultScreen(
                username = name,
                score = score,
                onRetry = {
                    startActivity(Intent(this, QuizWelcome::class.java))
                    finish() // optional: so user can't go back to score screen
                } ,
                onEnd = {
                 startActivity(Intent(this, DashBoardActivity::class.java)
                 )

                }
            )
        }
    }
}
