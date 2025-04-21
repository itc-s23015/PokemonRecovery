package jp.ac.it_college.std.s23015.android.pokequiz.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

/**
 * お題となるPokémonの画像を表示するコンポーザブル関数
 *
 * @param[url] ポケモン画像の URL
 * @param[name] ポケモンの名前
 * @param[quizStatus] クイズの進捗状況を表すデータ
 * @see QuizStatus
 * @see AsyncImage
 */
@Composable
fun PokemonArtwork(
    modifier: Modifier = Modifier,
    url: String,
    name: String,
    hint: String,
    quizStatus: QuizStatus
) {
    // 背景色と画像と名前を重ねて表示するために使用
    Box(
        modifier = modifier
            .padding(16.dp)
            .aspectRatio(1f)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Coil の AsyncImage を使用してインターネット上の画像を表示
        AsyncImage(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            // クイズが進行中なら画像を黒塗り。回答後なら黒塗りを解除
            colorFilter = if (quizStatus == QuizStatus.PROGRESS) ColorFilter.tint(
                Color.Black, BlendMode.SrcIn
            ) else null,
        )

        if (quizStatus == QuizStatus.PROGRESS) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(Color(0xAA000000)) // 半透明黒背景
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = hint,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        if (quizStatus != QuizStatus.PROGRESS) {
            // クイズが回答後なら、正解不正解に限らずポケモンの名前を表示
            // TODO: テキストが見づらいので表現方法を要検討
            Text(
                text = name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

@Preview(widthDp = 400, heightDp = 900, showBackground = true)
@Composable
private fun PokemonArtworkPreview() {
    Column {
        PokemonArtwork(
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/217.png",
            name = "ピカチュウ",
            quizStatus = QuizStatus.PROGRESS,
            hint = "でんき",
        )
        PokemonArtwork(
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/217.png",
            name = "ピカチュウ",
            quizStatus = QuizStatus.WRONG,
            hint = "でんき"
        )
    }
}