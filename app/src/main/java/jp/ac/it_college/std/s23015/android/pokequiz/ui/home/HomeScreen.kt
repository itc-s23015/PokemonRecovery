package jp.ac.it_college.std.s23015.android.pokequiz.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.it_college.std.s23015.android.pokequiz.R

/**
 * アプリ起動直後の最初の画面を表示するコンポーザブル関数
 *
 * @param[onStartClick] 「スタート」ボタンを押したときのコールバック関数
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onStartClick: () -> Unit = {}
) {
    // 全体を画面中央に揃えるために使用する。
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        // タイトルテキストとボタンを縦に並べるために使用する。
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            // タイトルテキスト
            Text(
                text = stringResource(R.string.home_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium
            )
            // スタートボタン
            Button(
                onClick = onStartClick
            ) {
                Text(
                    text = stringResource(R.string.start),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}