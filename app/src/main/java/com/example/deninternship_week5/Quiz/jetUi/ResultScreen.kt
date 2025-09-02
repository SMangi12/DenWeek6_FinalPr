import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//
//@Composable
//fun ResultScreen(username: String, score: Int, onRetry: () -> Unit, onEnd: () -> Unit) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("🎉 Quiz Completed!", fontSize = 28.sp)
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(
//            text = if (score >= 3) "Well done, $username!" else "Nice try, $username!",
//            fontSize = 20.sp
//        )
//        Spacer(modifier = Modifier.height(12.dp))
//        Text("Your Score: $score / 5", fontSize = 18.sp)
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Button(onClick = onRetry) {
//                Text("Retry")
//            }
//            Button(onClick = onEnd) {
//                Text("Exit")
//            }
//        }
//    }
//}

@Composable
fun ResultScreen(
    username: String,
    score: Int,
    onRetry: () -> Unit,
    onEnd: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White // 💡 Force white
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("🎉 Quiz Completed!", fontSize = 28.sp, color = MaterialTheme.colors.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (score >= 3) "Well done, $username!" else "Nice try, $username!",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("Your Score: $score / 5", fontSize = 18.sp)

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Retry")
                }
                Button(
                    onClick = onEnd,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Exit")
                }
            }
        }
    }
}
