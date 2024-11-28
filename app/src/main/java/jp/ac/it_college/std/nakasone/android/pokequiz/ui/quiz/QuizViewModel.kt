package jp.ac.it_college.std.nakasone.android.pokequiz.ui.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ac.it_college.std.nakasone.android.pokequiz.data.entity.PokemonEntity
import jp.ac.it_college.std.nakasone.android.pokequiz.data.repository.GenerationsRepository
import jp.ac.it_college.std.nakasone.android.pokequiz.data.repository.PokemonIntroducedGenerationRepository
import jp.ac.it_college.std.nakasone.android.pokequiz.network.PokeApiService
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.GENERATION_ID_ARG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class QuizStatus {
    PROGRESS, CORRECT, WRONG
}

data class QuizUiState(
    val isLoading: Boolean = true,
    val generationLabel: String = "",
    val status: QuizStatus = QuizStatus.PROGRESS,
    val number: Int = 0,
    val imageUrl: String = "",
    val targetName: String = "",
    val choices: List<String> = emptyList(),
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val generationsRepo: GenerationsRepository,
    private val pokeIntroRepo: PokemonIntroducedGenerationRepository,
    private val service: PokeApiService
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()
    private val generationId: Int = checkNotNull(
        savedStateHandle[GENERATION_ID_ARG]
    )
    private lateinit var pokeList: List<PokemonEntity>
    private lateinit var quizList: List<PokemonEntity>
    private var correctCount = 0

    init {
        viewModelScope.launch {
            val isCached = isGenerationWithPokemonCached(generationId)
            if (!isCached) {
                retrieveAndCacheGenerationWithPokemon(generationId)
            }
            generateQuizData()
            nextQuiz()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    generationLabel = generationsRepo.getGenerationStream(generationId)
                        .first()?.name ?: throw IllegalStateException("なにかおかしくなった")
                )
            }
        }
    }

    private suspend fun generateQuizData() {
        pokeList = pokeIntroRepo.getGenerationWithPokemon(generationId).first().pokemon
        quizList = pokeList.shuffled().subList(0, 10)
    }

    private fun nextQuiz(): Boolean {
        // カウントが10以上になったら処理しない
        if (uiState.value.number >= 10) return false

        val target = quizList[uiState.value.number]
        val url = target.officialArtwork
        val choices = (pokeList.filter { it.id != target.id }.shuffled().subList(0, 3)
            .map { it.name } + target.name).shuffled()
        _uiState.update {
            it.copy(
                status = QuizStatus.PROGRESS,
                targetName = target.name,
                imageUrl = url,
                choices = choices,
                number = uiState.value.number + 1
            )
        }
        return true
    }

    fun checkAnswer(selectName: String, end: (Int, Int) -> Unit) {
        val isCollect = selectName == uiState.value.targetName
        correctCount += if (isCollect) 1 else 0
        _uiState.update {
            it.copy(
                status = if (isCollect) QuizStatus.CORRECT else QuizStatus.WRONG,
            )
        }
        viewModelScope.launch {
            delay(2000)
            if (!nextQuiz()) end(generationId, correctCount)
        }
    }

    private suspend fun retrieveAndCacheGenerationWithPokemon(generationId: Int) {
        if (generationId != 0) {
            retrieveGenerationPokemonSpeciesIds(generationId).collect {
                pokeIntroRepo.upsertEntry(generationId, it)
            }
        } else {
            val gens = generationsRepo.getAllGenerationsStream().first()
            gens.forEach { gen ->
                retrieveGenerationPokemonSpeciesIds(gen.id).collect {
                    pokeIntroRepo.upsertEntry(generationId, it)
                }
            }
        }
    }

    private fun retrieveGenerationPokemonSpeciesIds(generationId: Int) = flow {
        val gen = service.getGenerationById(generationId)
        gen.pokemonSpecies.forEach {
            emit(service.getPokemonSpeciesByName(it.name).id)
        }
    }

    private suspend fun isGenerationWithPokemonCached(generationId: Int): Boolean {
        return if (generationId != 0) {
            getEntryCount(generationId) != 0
        } else {
            val gens = generationsRepo.getAllGenerationsStream().first()
            gens.map { pokeIntroRepo.getEntryCount(it.id) }.contains(0)
        }
    }

    private suspend fun getEntryCount(generationId: Int): Int {
        val gen = generationsRepo.getGenerationStream(generationId).first()
            ?: throw IllegalArgumentException("世代IDが間違っています")
        val count = pokeIntroRepo.getEntryCount(gen.id)
        return count
    }
}