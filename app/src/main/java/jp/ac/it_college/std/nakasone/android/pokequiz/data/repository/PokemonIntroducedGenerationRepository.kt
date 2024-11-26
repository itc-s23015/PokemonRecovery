package jp.ac.it_college.std.nakasone.android.pokequiz.data.repository

import jp.ac.it_college.std.nakasone.android.pokequiz.data.entity.GenerationWithPokemon
import kotlinx.coroutines.flow.Flow

interface PokemonIntroducedGenerationRepository {
    fun getGenerationWithPokemon(id: Int): Flow<GenerationWithPokemon>
    suspend fun getEntryCount(id: Int): Int
    suspend fun upsertEntry(generationId: Int, pokemonId: Int)
}