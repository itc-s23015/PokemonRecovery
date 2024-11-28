package jp.ac.it_college.std.nakasone.android.pokequiz.ui.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

data class ResultUiState(
    val generation: String = "",
    val correctCount: Int = 0,
)

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    generationsRepo: GenerationsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()

    init {
        val genId: Int = checkNotNull(
            savedStateHandle[GENERATION_ID_ARG]
        )
        val correctCount: Int = checkNotNull(
            savedStateHandle[CORRECT_ANSWER_COUNT_ARG]
        )
        viewModelScope.launch {
            val gen = generationsRepo.getGenerationStream(genId).first()?.name
                ?: throw IllegalStateException("何かがおかしい")
            _uiState.update {
                it.copy(
                    generation = gen,
                    correctCount = correctCount
                )
            }
        }
    }
}