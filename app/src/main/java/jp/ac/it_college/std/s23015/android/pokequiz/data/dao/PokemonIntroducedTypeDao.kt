package jp.ac.it_college.std.s23015.android.pokequiz.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.PokemonIntroducedTypeCrossRef
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.TypeWithPokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonIntroducedTypeDao {
    @Upsert
    suspend fun upsert(entity: PokemonIntroducedTypeCrossRef)

    @Transaction
    @Query("SELECT * FROM type WHERE id = :id")
    fun getTypeWithPokemon(id: Int): Flow<TypeWithPokemon>
}