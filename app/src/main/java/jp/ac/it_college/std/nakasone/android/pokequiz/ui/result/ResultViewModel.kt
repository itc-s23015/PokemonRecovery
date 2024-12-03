package jp.ac.it_college.std.nakasone.android.pokequiz.ui.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ac.it_college.std.nakasone.android.pokequiz.data.entity.GenerationEntity
import jp.ac.it_college.std.nakasone.android.pokequiz.data.repository.GenerationsRepository
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.CORRECT_ANSWER_COUNT_ARG
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.GENERATION_ID_ARG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 結果画面の UI で使用するデータ
 */
data class ResultUiState(
    val generationId: Int = 0,
    val generationName: String = "",
    val correctCount: Int = 0,
)

/**
 * 結果画面のビューモデルクラス
 *
 * @see GenerationsRepository
 */
@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    generationsRepo: GenerationsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()

    /**
     * ナビゲーションパラメータに含まれる世代ID
     */
    private val genId: Int = checkNotNull(
        savedStateHandle[GENERATION_ID_ARG]
    )

    /**
     * ナビゲーションパラメータに含まれるクイズの正解数
     */
    private val correctCount: Int = checkNotNull(
        savedStateHandle[CORRECT_ANSWER_COUNT_ARG]
    )

    init {
        viewModelScope.launch {
            // あらためてデータベースから世代情報を取ってくる
            val gen = generationsRepo.getGenerationStream(genId).first()
            // もし null が返ってくるなら 0 で全世代対象の場合のハズ
                ?: GenerationEntity(0, "全世代", "全国")

            // UIステートを更新
            _uiState.update {
                it.copy(
                    generationId = gen.id,
                    generationName = gen.name,
                    correctCount = correctCount
                )
            }
        }
    }
}