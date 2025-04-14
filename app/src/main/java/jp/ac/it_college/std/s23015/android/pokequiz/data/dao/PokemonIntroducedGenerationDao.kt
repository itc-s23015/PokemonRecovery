package jp.ac.it_college.std.s23015.android.pokequiz.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.GenerationWithPokemon
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.PokemonIntroducedGenerationCrossRef
import kotlinx.coroutines.flow.Flow

/**
 * generationテーブルとpokemonテーブルを関連付ける pokemon_introduced_generation_cross_ref テーブル関連の DAO インタフェース
 *
 * 登録時以外で直接 [PokemonIntroducedGenerationCrossRef] エンティティは使用しない。
 *
 * データクラス [GenerationWithPokemon] を通じてデータを提供する。
 */
@Dao
interface PokemonIntroducedGenerationDao {
    @Upsert
    suspend fun upsert(entity: PokemonIntroducedGenerationCrossRef)

    @Transaction
    @Query("SELECT * FROM generations WHERE id = :id")
    fun getGenerationWithPokemon(id: Int): Flow<GenerationWithPokemon>

    @Transaction
    @Query("SELECT * FROM generations")
    fun getAllGenerationWithPokemon(): Flow<List<GenerationWithPokemon>>

    @Query("SELECT COUNT(*) FROM pokemon_introduced_generation_cross_ref WHERE generation_id = :id")
    suspend fun getEntryCount(id: Int): Int
}