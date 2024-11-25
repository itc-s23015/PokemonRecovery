package jp.ac.it_college.std.s23000.android.pokequiz.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import jp.ac.it_college.std.s23000.android.pokequiz.data.entity.GenerationWithPokemon
import jp.ac.it_college.std.s23000.android.pokequiz.data.entity.PokemonIntroducedGenerationCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonIntroducedGenerationDao {
    @Upsert
    suspend fun upsert(entity: PokemonIntroducedGenerationCrossRef)

    @Transaction
    @Query("SELECT * FROM generations WHERE id = :id")
    fun getGenerationWithPokemon(id: Int): Flow<GenerationWithPokemon>

    @Query("SELECT COUNT(*) FROM pokemon_introduced_generation_cross_ref WHERE generation_id = :id")
    suspend fun getEntryCount(id: Int): Int
}