package jp.ac.it_college.std.s23015.android.pokequiz.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

/**
 * [PokemonEntity] に対応する DAO インタフェース
 */
@Dao
interface PokemonDao {
    @Upsert
    suspend fun upsert(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemon WHERE id = :id")
    fun getPokemon(id: Int): Flow<PokemonEntity>

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getPokemonCount(): Int
}