package jp.ac.it_college.std.s23015.android.pokequiz.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import jp.ac.it_college.std.s23015.android.pokequiz.R

/**
 * クイズを表示するこのアプリのメインとなる画面を表示するコンポーザブル関数
 *
 * @param[onQuizEnded] クイズが終了したときのコールバック関数
 * @see QuizViewModel
 */
@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    onQuizEnded: (generationId: Int, correctCount: Int) -> Unit = { _, _ -> },
    viewModel: QuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        // データ読み込み中なら円形のインジケーターを表示
        DataLoading(modifier = modifier)
    } else {
        // メインのクイズ UI を表示
        QuizView(
            modifier = modifier,
            uiState = uiState,
            onSelected = {
                // 選択肢ボタンがクリックされたら、viewModel の判定用メソッドを実行する
                viewModel.checkAnswer(it, onQuizEnded)
            }
        )
    }
}

@Preview
@Composable
private fun QuizScreenPreview() {
    QuizScreen()
}

/**
 * 読み込み中を表現するコンポーザブル関数
 */
@Composable
fun DataLoading(modifier: Modifier = Modifier) {
    // Column である必要があるかは不明。画面全体の中央に寄せるために使用
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        // 円形インジケーターと「読み込み中」の文字を重ねるために使用。
        // 横幅基準で目一杯広げたいので、fillMaxWidth を使用。
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Modifier.aspectRatio で縦横比率を正方形だと指定しないと
            // なぜかインジケーターの位置がズレます。
            CircularProgressIndicator(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(64.dp)
                    .fillMaxWidth(),
            )
            // ロード中の文言を表示
            Text(text = stringResource(id = R.string.loading_quiz_data))
        }
    }
}

/**
 * クイズのメインとなる UI コンポーザブル関数
 *
 * @param[uiState] 表示に使うデータ
 * @param[onSelected] 選択肢のボタンがクリックされたときのコールバック関数
 * @see QuizUiState
 */
@Composable
fun QuizView(
    modifier: Modifier = Modifier,
    uiState: QuizUiState,
    onSelected: (String) -> Unit = {}
) {
    // 各パーツを縦並びにするために使用
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // 世代名と今何問目かを表示するテキストは横並びにするので使用
        Row {
            // 世代名を表示
            Text(
                text = uiState.generationLabel,
                style = MaterialTheme.typography.titleLarge
            )
            // くっつくので Spacer を入れたが、 Row に間隔を指定するほうが良さそう
            Spacer(modifier = Modifier.size(8.dp))
            // 今何問目かを表示
            Text(
                text = stringResource(R.string.quiz_count, uiState.quizNumber),
                style = MaterialTheme.typography.titleLarge
            )
        }

        // ポケモンの画像を表示
        Row {
            PokemonArtwork(
                url = uiState.imageUrl,
                name = uiState.targetName,
                quizStatus = uiState.status,
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = stringResource(R.string.quiz_hint, uiState.quizType)
            )
        }


        // 選択肢を表示
        ChoiceSection(
            modifier = Modifier.fillMaxSize(),
            choices = uiState.choices,
            quizStatus = uiState.status,
            onSelected = onSelected
        )
    }
}