package jp.ac.it_college.std.s23015.android.pokequiz.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import jp.ac.it_college.std.s23015.android.pokequiz.R
import jp.ac.it_college.std.s23015.android.pokequiz.mock.GenerationsRepositoryMock
import jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.CORRECT_ANSWER_COUNT_ARG
import jp.ac.it_college.std.s23015.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.GENERATION_ID_ARG

/**
 * クイズの結果を表示するコンポーザブル関数
 */
@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    onRetryClick: (generationId: Int) -> Unit = {},
    onGenerationClick: () -> Unit = {},
    viewModel: ResultViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 選択した世代名を表示
        Text(
            text = uiState.generationName,
            style = MaterialTheme.typography.headlineLarge
        )
        // 正解数を表示
        Text(
            modifier = Modifier.padding(vertical = 96.dp),
            text = stringResource(
                id = R.string.result_label,
                uiState.correctCount
            ),
            style = MaterialTheme.typography.displayMedium
        )

        // 2つのボタンを縦に並べて表示しつつ、画面下部へ揃えるために使う
        Column(
            modifier = Modifier
                .padding(vertical = 32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Bottom
            ),
        ) {
            // 同じ世代でリトライのボタンを表示
            ResultNavigateButton(
                onClick = { onRetryClick(uiState.generationId) },
                label = stringResource(id = R.string.retry)
            )
            // 世代を選択する画面へのボタンを表示
            ResultNavigateButton(
                onClick = onGenerationClick,
                label = stringResource(id = R.string.to_generation_select)
            )
        }
    }
}

@Preview
@Composable
private fun ResultScreenPreview() {
    ResultScreen(
        viewModel = ResultViewModel(
            SavedStateHandle(
                mapOf(
                    GENERATION_ID_ARG to 2,
                    CORRECT_ANSWER_COUNT_ARG to 7
                )
            ),
            GenerationsRepositoryMock
        )
    )
}

/**
 * 結果を表示したあとのアクションを選んでもらうためのボタンを表示するコンポーザブル関数
 *
 * @param[label] ボタンに表示するテキスト
 * @param[onClick] ボタンをクリックされたときのコールバック関数
 */
@Composable
fun ResultNavigateButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.displaySmall
        )
    }
}