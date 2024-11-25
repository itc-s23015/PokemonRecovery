package jp.ac.it_college.std.s23000.android.pokequiz.data.repository

import jp.ac.it_college.std.s23000.android.pokequiz.data.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonStream(id: Int): Flow<PokemonEntity?>
    suspend fun getPokemonCount(): Int
    suspend fun upsertPokemon(pokemon: PokemonEntity)
}