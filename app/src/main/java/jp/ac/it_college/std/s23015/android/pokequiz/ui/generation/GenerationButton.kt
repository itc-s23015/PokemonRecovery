package jp.ac.it_college.std.s23015.android.pokequiz.ui.generation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 世代選択ボタンを表示するコンポーザブル関数
 *
 * @param[label] ボタンに表示するテキスト
 * @param[onClick] ボタンをクリック(タップ)されたときのコールバック関数
 */
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