package com.example.deninternship_week5.Quiz.jetUi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

//@Composable
//fun QuizScreen(
//    questions: List<Question>,
//    username: String,
//    onQuizEnd: (Int) -> Unit
//) {
//    var currentIndex by remember { mutableStateOf(0) }
//    var score by remember { mutableStateOf(0) }
//    var timeLeft by remember { mutableStateOf(10) }
//    var showResult by remember { mutableStateOf(false) }
//
//    val question = questions[currentIndex]
//    val totalTime = 10f
//    val progress = timeLeft / totalTime
//
//    // Timer logic
//    LaunchedEffect(currentIndex) {
//        timeLeft = 10
//        while (timeLeft > 0) {
//            delay(1000L)
//            timeLeft--
//        }
//        if (timeLeft == 0) {
//            if (currentIndex < questions.lastIndex) currentIndex++
//            else showResult = true
//        }
//    }
//
//    if (showResult) {
//        onQuizEnd(score)
//    } else {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.SpaceEvenly,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text("👋 Hello, $username!", fontSize = 20.sp)
//
//            // Timer Circle
//            Box(contentAlignment = Alignment.Center) {
//                CircularProgressIndicator(
//                    progress = progress,
//                    strokeWidth = 6.dp,
//                    modifier = Modifier.size(80.dp),
//                    color = MaterialTheme.colors.primary
//                )
//                Text("$timeLeft", fontSize = 18.sp)
//            }
//
//            // Question & Options in Card
//            Card(
//                elevation = 8.dp,
//                shape = MaterialTheme.shapes.medium,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    Text(
//                        text = question.questionText,
//                        fontSize = 22.sp,
//                        color = MaterialTheme.colors.primary
//                    )
//
//                    question.options.forEachIndexed { index, option ->
//                        Button(
//                            onClick = {
//                                if (index == question.correctAnswerIndex) score++
//                                if (currentIndex < questions.lastIndex) currentIndex++
//                                else showResult = true
//                            },
//                            modifier = Modifier.fillMaxWidth(),
//                            shape = MaterialTheme.shapes.medium
//                        ) {
//                            Text(option)
//                        }
//                    }
//                }
//            }
//
//            Text("📄 Question ${currentIndex + 1} of ${questions.size}", fontSize = 16.sp)
//        }
//    }
//}

@Composable
fun QuizScreen(
    questions: List<Question>,
    username: String,
    onQuizEnd: (Int) -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(10) }
    var showResult by remember { mutableStateOf(false) }

    val question = questions[currentIndex]
    val totalTime = 10f
    val progress = timeLeft / totalTime

    LaunchedEffect(currentIndex) {
        timeLeft = 10
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        if (timeLeft == 0) {
            if (currentIndex < questions.lastIndex) currentIndex++
            else showResult = true
        }
    }

    if (showResult) {
        onQuizEnd(score)
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White // 💡 Always white
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("👋 Hello, $username!", fontSize = 20.sp)

                // ⏱ Timer Circle
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = progress,
                        strokeWidth = 6.dp,
                        modifier = Modifier.size(80.dp),
                        color = MaterialTheme.colors.primary
                    )
                    Text("$timeLeft", fontSize = 18.sp)
                }

                // 🧠 Question Card
                Card(
                    elevation = 8.dp,
                    shape = MaterialTheme.shapes.medium,
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = question.questionText,
                            fontSize = 22.sp,
                            color = MaterialTheme.colors.primary
                        )

                        question.options.forEachIndexed { index, option ->
                            Button(
                                onClick = {
                                    if (index == question.correctAnswerIndex) score++
                                    if (currentIndex < questions.lastIndex) currentIndex++
                                    else showResult = true
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.medium,
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.primary,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(option)
                            }
                        }
                    }
                }

                Text("📄 Question ${currentIndex + 1} of ${questions.size}", fontSize = 16.sp)
            }
        }
    }
}
