package jp.ac.it_college.std.s23000.android.pokequiz.ui.generation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GenerationScreen(
    modifier: Modifier = Modifier,
    onGenerationSelected: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "世代選択")
        Button(
            onClick = {
                onGenerationSelected(1)
            }
        ) {
            Text(text = "とりあえず第1世代でテスト")
        }
    }
}

@Preview
@Composable
private fun GenerationScreenPreview() {
    GenerationScreen()
}