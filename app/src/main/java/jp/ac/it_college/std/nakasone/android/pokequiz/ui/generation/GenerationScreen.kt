package jp.ac.it_college.std.nakasone.android.pokequiz.ui.generation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import jp.ac.it_college.std.nakasone.android.pokequiz.mock.GenerationsRepositoryMock
import jp.ac.it_college.std.nakasone.android.pokequiz.mock.PokeApiServiceMock

@Composable
fun GenerationScreen(
    modifier: Modifier = Modifier,
    onGenerationSelected: (Int) -> Unit = {},
    viewModel: GenerationViewModel = hiltViewModel()
) {
    val uiState: GenerationUiState by viewModel.uiState.collectAsState()
    val generations: List<GenerationEntity> by uiState.generations.collectAsState(initial = emptyList())
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.select_generation))

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(generations) { item ->
                    GenerationButton(
                        label = stringResource(R.string.generation_choice, item.name, item.region),
                        onClick = { onGenerationSelected(item.id) }

                    )
                }
                item {
                    GenerationButton(
                        label = stringResource(R.string.all_generation),
                        onClick = { onGenerationSelected(0) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GenerationScreenPreview() {
    GenerationScreen(
        viewModel = GenerationViewModel(
            repository = GenerationsRepositoryMock,
            service = PokeApiServiceMock
        )
    )
}