package jp.ac.it_college.std.s23015.android.pokequiz.ui.generation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.GenerationEntity
import jp.ac.it_college.std.s23015.android.pokequiz.data.repository.GenerationsRepository
import jp.ac.it_college.std.s23015.android.pokequiz.network.PokeApiService
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.Name
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.isJa
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.isJaHrkt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI 側で直接使用するデータクラス
 */
data class GenerationUiState(
    /**
     * ローディング中を表すフラグ
     */
    val isLoading: Boolean = true,

    /**
     * 世代情報のリスト
     */
    val generations: Flow<List<GenerationEntity>> = emptyFlow()
)

/**
 * 世代選択画面で使用するビューモデル
 *
 * @see GenerationsRepository
 * @see PokeApiService
 */
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

    /**
     * データ読み込み中状態を表す [GenerationUiState.isLoading] の
     * 状態を更新するメソッド
     */
    private fun updateLoadingStatus(isLoading: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    /**
     * 世代情報を [PokeAPI v2](https://pokeapi.co/docs/v2) から取得し、
     * データベースにキャッシュするメソッド
     */
    private suspend fun retrieveAndCacheGenerations() {
        // 世代情報リストが詰まったデータを取得
        service.getGenerations().results.forEach { result ->
            // 個別の世代情報(name/urlのみ)から詳細とリージョン情報を取ってきて保存する
            // 世代情報の詳細取る
            val gen = service.getGenerationByName(result.name)
            // リージョン情報を取る
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