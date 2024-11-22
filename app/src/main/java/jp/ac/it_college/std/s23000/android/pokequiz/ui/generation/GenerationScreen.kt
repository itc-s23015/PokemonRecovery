package jp.ac.it_college.std.s23000.android.pokequiz.ui.generation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.ac.it_college.std.s23000.android.pokequiz.data.entity.GenerationEntity
import jp.ac.it_college.std.s23000.android.pokequiz.mock.GenerationsRepositoryMock
import jp.ac.it_college.std.s23000.android.pokequiz.mock.PokeApiServiceMock

@Composable
fun GenerationScreen(
    modifier: Modifier = Modifier,
    onGenerationSelected: (Int) -> Unit = {},
    viewModel: GenerationViewModel = hiltViewModel()
) {
    val uiState: GenerationUiState by viewModel.uiState.collectAsState()
    val generations: List<GenerationEntity> by uiState.generations.collectAsState(initial = emptyList())
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "世代選択")
        Button(
            onClick = {
                onGenerationSelected(1)
            }
        ) {
            Text(text = "とりあえず第1世代でテスト")
        }
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        } else {
            LazyColumn {
                items(generations) { item ->
                    Text(text = "${item.name} ${item.region}")
                }
                item {
                    Text(text = "全世代から出題")
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