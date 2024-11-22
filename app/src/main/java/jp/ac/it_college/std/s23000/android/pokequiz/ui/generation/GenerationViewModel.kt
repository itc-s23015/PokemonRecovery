package jp.ac.it_college.std.s23000.android.pokequiz.ui.generation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ac.it_college.std.s23000.android.pokequiz.data.entity.GenerationEntity
import jp.ac.it_college.std.s23000.android.pokequiz.data.repository.GenerationsRepository
import jp.ac.it_college.std.s23000.android.pokequiz.network.PokeApiService
import jp.ac.it_college.std.s23000.android.pokequiz.network.model.Name
import jp.ac.it_college.std.s23000.android.pokequiz.network.model.isJa
import jp.ac.it_college.std.s23000.android.pokequiz.network.model.isJaHrkt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GenerationUiState(
    val isLoading: Boolean = true,
    val generations: Flow<List<GenerationEntity>> = emptyFlow()
)

@HiltViewModel
class GenerationViewModel @Inject constructor(
    private val repository: GenerationsRepository,
    private val service: PokeApiService
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        GenerationUiState(
            generations = repository.getAllGenerationsStream()
        )
    )
    val uiState: StateFlow<GenerationUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // データベースに世代データがなければ PokeAPI から取ってくる
            if (repository.getGenerationCount() == 0) {
                // API 空データ取ってきてデータベースに保存する
                retrieveAndCacheGenerations()
            }
            // ローディング中フラグを折る
            updateLoadingStatus(false)
        }
    }

    private fun updateLoadingStatus(isLoading: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    private suspend fun retrieveAndCacheGenerations() {
        service.getGenerations().results.forEach { result ->
            // 世代情報の詳細取る
            val gen = service.getGenerationByName(result.name)
            // 地域情報を取る
            val region = service.getRegionByName(gen.mainRegion.name)
            // まとめたものをデータベースに登録する
            repository.upsertGeneration(
                GenerationEntity(
                    id = gen.id,
                    name = gen.names.firstOrNull(Name::isJa)?.name ?: "????",
                    region = region.names.firstOrNull(Name::isJaHrkt)?.name ?: "????",
                )
            )
        }
    }
}