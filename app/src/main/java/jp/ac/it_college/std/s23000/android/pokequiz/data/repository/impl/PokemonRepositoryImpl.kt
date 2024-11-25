package jp.ac.it_college.std.s23000.android.pokequiz.data.repository.impl

import jp.ac.it_college.std.s23000.android.pokequiz.data.dao.PokemonDao
import jp.ac.it_college.std.s23000.android.pokequiz.data.entity.PokemonEntity
import jp.ac.it_college.std.s23000.android.pokequiz.data.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val dao: PokemonDao
) : PokemonRepository {
    override fun getPokemonStream(id: Int): Flow<PokemonEntity?> = dao.getPokemon(id)

    override suspend fun getPokemonCount(): Int = dao.getPokemonCount()

    override suspend fun upsertPokemon(pokemon: PokemonEntity) = dao.upsert(pokemon)
}