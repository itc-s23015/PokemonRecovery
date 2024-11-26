package jp.ac.it_college.std.nakasone.android.pokequiz.ui.generation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GenerationButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        onClick = onClick
    ) {
        Text(text = label)
    }
}

@Preview
@Composable
private fun GenerationButtonPreview() {
    GenerationButton(label = "第1世代 - カントー地方")
}