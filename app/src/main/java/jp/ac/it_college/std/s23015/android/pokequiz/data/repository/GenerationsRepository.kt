package jp.ac.it_college.std.s23015.android.pokequiz.data.repository

import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.GenerationEntity
import kotlinx.coroutines.flow.Flow

/**
 * [GenerationEntity] のリポジトリインタフェース
 */
interface GenerationsRepository {
    fun getAllGenerationsStream(): Flow<List<GenerationEntity>>
    fun getGenerationStream(id: Int): Flow<GenerationEntity?>
    suspend fun getGenerationCount(): Int
    suspend fun upsertGeneration(generation: GenerationEntity)
}