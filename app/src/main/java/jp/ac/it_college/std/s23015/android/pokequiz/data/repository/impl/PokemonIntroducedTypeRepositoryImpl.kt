package jp.ac.it_college.std.s23015.android.pokequiz.data.repository.impl

/*
import jp.ac.it_college.std.s23015.android.pokequiz.data.dao.PokemonIntroducedTypeDao
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.PokemonIntroducedTypeCrossRef
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.TypeWithPokemon
import jp.ac.it_college.std.s23015.android.pokequiz.data.repository.PokemonIntroducedTypeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PokemonIntroducedTypeRepositoryImpl @Inject constructor(
    private val dao: PokemonIntroducedTypeDao
) : PokemonIntroducedTypeRepository {
    override fun getTypeWithPokemon(id: Int): Flow<TypeWithPokemon> =
        dao.getTypeWithPokemon(id)

    override suspend fun upsertEntry(typeId: Int, pokemonId: Int) =
        dao.upsert(PokemonIntroducedTypeCrossRef(typeId, pokemonId))
}

 */