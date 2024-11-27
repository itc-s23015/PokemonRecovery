package jp.ac.it_college.std.nakasone.android.pokequiz.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
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

@Composable
fun PokemonArtwork(
    modifier: Modifier = Modifier,
    url: String,
    name: String,
    quizStatus: QuizStatus
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .aspectRatio(1f)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.TopCenter
    ) {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            colorFilter = if (quizStatus == QuizStatus.PROGRESS) ColorFilter.tint(
                Color.Black, BlendMode.SrcIn
            ) else null,
        )

        if (quizStatus != QuizStatus.PROGRESS) {
            val isCollect = quizStatus == QuizStatus.CORRECT
            Column {
                Icon(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    imageVector = if (isCollect) Icons.Outlined.Circle else Icons.Default.Close,
                    contentDescription = null,
                    tint = if (isCollect) Color.Cyan else Color.Red
                )
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }

        if (quizStatus == QuizStatus.CORRECT) {

        }
    }
}

@Preview(widthDp = 400, heightDp = 1200, showBackground = true)
@Composable
private fun PokemonArtworkPreview() {
    Column {
        PokemonArtwork(
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/217.png",
            name = "ピカチュウ",
            quizStatus = QuizStatus.PROGRESS
        )
        PokemonArtwork(
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/217.png",
            name = "ピカチュウ",
            quizStatus = QuizStatus.WRONG
        )
        PokemonArtwork(
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/217.png",
            name = "ピカチュウ",
            quizStatus = QuizStatus.CORRECT
        )
    }

}