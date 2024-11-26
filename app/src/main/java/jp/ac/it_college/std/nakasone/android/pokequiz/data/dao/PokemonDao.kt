package jp.ac.it_college.std.nakasone.android.pokequiz.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import jp.ac.it_college.std.nakasone.android.pokequiz.data.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Upsert
    suspend fun upsert(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemon WHERE id = :id")
    fun getPokemon(id: Int): Flow<PokemonEntity>

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getPokemonCount(): Int
}