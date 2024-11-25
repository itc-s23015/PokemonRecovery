package jp.ac.it_college.std.s23000.android.pokequiz.ui.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit = {},
    onGenerationClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "結果画面")
        Button(onClick = onRetryClick) {
            Text(text = "もういちど")
        }
        Button(onClick = onGenerationClick) {
            Text(text = "世代選択へ")
        }
    }
}

@Preview
@Composable
private fun ResultScreenPreview() {
    ResultScreen()
}