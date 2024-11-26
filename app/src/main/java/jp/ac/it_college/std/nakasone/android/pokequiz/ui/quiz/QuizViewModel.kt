package jp.ac.it_college.std.nakasone.android.pokequiz.ui.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ac.it_college.std.nakasone.android.pokequiz.data.repository.GenerationsRepository
import jp.ac.it_college.std.nakasone.android.pokequiz.data.repository.PokemonIntroducedGenerationRepository
import jp.ac.it_college.std.nakasone.android.pokequiz.network.PokeApiService
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.navigation.PokeQuizDestinationArgs.GENERATION_ID_ARG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
    val number: Int = 1,
    val imageUrl: String = "",
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
//    private val generationWithPokemon: Flow<GenerationWithPokemon> =
//        pokeIntroRepo.getGenerationWithPokemon(generationId)

    val test = pokeIntroRepo.getGenerationWithPokemon(generationId)

    init {
        viewModelScope.launch {
            val isCached = isGenerationWithPokemonCached(generationId)
            if (!isCached) {
                retrieveAndCacheGenerationWithPokemon(generationId)
            }
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    private suspend fun retrieveAndCacheGenerationWithPokemon(generationId: Int) {
        if (generationId != 0) {
            retrieveGenerationPokemonSpeciesIds(generationId).forEach {
                pokeIntroRepo.upsertEntry(generationId, it)
            }
        } else {
            val gens = generationsRepo.getAllGenerationsStream().first()
            gens.forEach { gen ->
                retrieveGenerationPokemonSpeciesIds(gen.id).forEach {
                    pokeIntroRepo.upsertEntry(generationId, it)
                }
            }
        }
    }

    private suspend fun retrieveGenerationPokemonSpeciesIds(generationId: Int): List<Int> {
        val gen = service.getGenerationById(generationId)
        return gen.pokemonSpecies.map {
            service.getPokemonSpeciesByName(it.name).id
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