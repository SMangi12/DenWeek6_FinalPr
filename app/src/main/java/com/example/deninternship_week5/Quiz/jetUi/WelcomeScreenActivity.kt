package com.example.deninternship_week5.Quiz.jetUi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(onStartClick: (String) -> Unit) {
    var selectedTopic by remember { mutableStateOf("") }

    val topics = listOf(
        "Computer Networks",
        "Data Structures and Algorithms",
        "Android Development"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🚀 Welcome to the Quiz App",
            fontSize = 26.sp,
            color = MaterialTheme.colors.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "📘 Choose a Quiz Topic",
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        topics.forEach { topic ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = selectedTopic == topic,
                    onClick = { selectedTopic = topic }
                )
                Text(text = topic)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onStartClick(selectedTopic) },
            enabled = selectedTopic.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.elevation(6.dp)
        ) {
            Text(text = "Start Quiz", color = Color.White, fontSize = 18.sp)
        }
    }
}
