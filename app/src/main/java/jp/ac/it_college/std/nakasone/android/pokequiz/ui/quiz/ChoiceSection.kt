package jp.ac.it_college.std.nakasone.android.pokequiz.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ChoiceSection(
    modifier: Modifier = Modifier,
    choices: List<String>,
    quizStatus: QuizStatus,
    onSelected: (String) -> Unit = {}
) {

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            choices.forEach {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    onClick = { onSelected(it) },
                    enabled = quizStatus == QuizStatus.PROGRESS
                ) {
                    Text(text = it)
                }
            }
        }

        if (quizStatus != QuizStatus.PROGRESS) {
            val isCollect = quizStatus == QuizStatus.CORRECT

            Icon(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize(),
                imageVector = if (isCollect) Icons.Outlined.Circle else Icons.Default.Close,
                contentDescription = null,
                tint = if (isCollect) Color.Cyan else Color.Red
            )
        }
    }
}

@Preview(heightDp = 1300, showBackground = true)
@Composable
private fun ChoiceSectionPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        ChoiceSection(
            choices = listOf(
                "マリルリ", "クロバット", "キレイハナ", "セレビィ"
            ),
            quizStatus = QuizStatus.PROGRESS
        )
        ChoiceSection(
            choices = listOf(
                "マリルリ", "クロバット", "キレイハナ", "セレビィ"
            ),
            quizStatus = QuizStatus.CORRECT
        )
        ChoiceSection(
            choices = listOf(
                "マリルリ", "クロバット", "キレイハナ", "セレビィ"
            ),
            quizStatus = QuizStatus.WRONG
        )
    }
}