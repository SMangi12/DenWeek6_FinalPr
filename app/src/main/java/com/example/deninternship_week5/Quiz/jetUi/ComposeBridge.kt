
package  com.example.deninternship_week5.Quiz.jetUi;
import android.content.Context
import androidx.compose.ui.platform.ComposeView
import kotlin.jvm.functions.Function1

object ComposeBridge {
    @JvmStatic
    fun createComposeView(
        context: Context,
        questions: List<Question>,
        username: String,
        onQuizEnd: Function1<Int, Unit>
    ): ComposeView {
        val view = ComposeView(context)
        view.setContent {
            QuizScreen(
                questions = questions,
                username = username,
                onQuizEnd = { score -> onQuizEnd.invoke(score) }
            )
        }
        return view
    }
}
