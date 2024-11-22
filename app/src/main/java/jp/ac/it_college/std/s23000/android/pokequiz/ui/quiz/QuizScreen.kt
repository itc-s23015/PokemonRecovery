package jp.ac.it_college.std.s23000.android.pokequiz.ui.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    toResult: (Int, Int) -> Unit = { _, _ -> }
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "クイズ画面")
        Button(
            onClick = {
                toResult(1, 5)
            }
        ) {
            Text(text = "第1世代 5問正解でリザルトへ")
        }
    }
}

@Preview
@Composable
private fun QuizScreenPreview() {
    QuizScreen()
}