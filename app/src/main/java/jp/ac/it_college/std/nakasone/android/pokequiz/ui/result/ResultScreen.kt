package jp.ac.it_college.std.nakasone.android.pokequiz.ui.result

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
import jp.ac.it_college.std.nakasone.android.pokequiz.R
import jp.ac.it_college.std.nakasone.android.pokequiz.mock.GenerationsRepositoryMock
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.CORRECT_ANSWER_COUNT_ARG
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.GENERATION_ID_ARG

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit = {},
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
        Text(
            text = uiState.generation,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            modifier = Modifier.padding(vertical = 96.dp),
            text = stringResource(
                id = R.string.result_label,
                uiState.correctCount
            ),
            style = MaterialTheme.typography.displayMedium
        )

        Column(
            modifier = Modifier
                .padding(vertical = 32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Bottom
            ),

            ) {
            ResultNavigateButton(
                onClick = onRetryClick,
                label = stringResource(id = R.string.retry)
            )
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