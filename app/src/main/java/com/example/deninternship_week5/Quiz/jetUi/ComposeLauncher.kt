package com.example.deninternship_week5.Quiz.jetUi
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.lightColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.deninternship_week5.Quiz.MainFunctionality.QuizActivity

class ComposeLauncher : ComponentActivity() {

    private val lightColors = lightColors(
        background = Color.White,
        surface = Color.White
    )

    companion object {
        @JvmStatic
        fun launch(context: Context) {
            val intent = Intent(context, ComposeLauncher::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)








        setContent {
            MaterialTheme(
                colors = lightColors(
                    primary = Color(0xFF6200EE),
                    primaryVariant = Color(0xFF3700B3),
                    secondary = Color(0xFF03DAC6),
                    background = Color.White,
                    surface = Color.White,
                    onPrimary = Color.White,
                    onSecondary = Color.Black,
                    onBackground = Color.Black,
                    onSurface = Color.Black
                )
            ){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // Pass context and start QuizActivity when user clicks Start
                    WelcomeScreen {  topic ->
                        println("Topic: $topic")

                        val intent = Intent(this, QuizActivity::class.java).apply {
                            putExtra("USERNAME", "User")
                            putExtra("TOPIC", topic)
                        }
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}
