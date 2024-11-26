package jp.ac.it_college.std.nakasone.android.pokequiz.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import jp.ac.it_college.std.nakasone.android.pokequiz.R
import jp.ac.it_college.std.nakasone.android.pokequiz.data.entity.GenerationEntity
import jp.ac.it_college.std.nakasone.android.pokequiz.data.entity.GenerationWithPokemon

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    toResult: (Int, Int) -> Unit = { _, _ -> },
    viewModel: QuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isLoading) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(64.dp)
                        .fillMaxWidth(),
                )
                Text(text = stringResource(id = R.string.loading_quiz_data))
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row {
                Text(
                    text = uiState.generationLabel,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.quiz_count, uiState.number),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            val test by viewModel.test.collectAsState(
                GenerationWithPokemon(
                    GenerationEntity(
                        0,
                        "",
                        ""
                    ), emptyList()
                )
            )
            LazyColumn {
                items(test.pokemon) {
                    Text(text = "${it.name}")
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuizScreenPreview() {
    QuizScreen()
}