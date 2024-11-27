package jp.ac.it_college.std.nakasone.android.pokequiz.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ChoiceSection(
    modifier: Modifier = Modifier,
    choices: List<String>,
    onSelected: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        choices.forEach {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                onClick = { onSelected(it) }
            ) {
                Text(text = it)
            }
        }

    }
}

@Preview
@Composable
private fun ChoiceSectionPreview() {
    ChoiceSection(
        choices = listOf(
            "マリルリ", "クロバット", "キレイハナ", "セレビィ"
        )
    )
}