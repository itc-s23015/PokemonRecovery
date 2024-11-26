package jp.ac.it_college.std.nakasone.android.pokequiz.data.repository

import jp.ac.it_college.std.nakasone.android.pokequiz.data.entity.GenerationEntity
import kotlinx.coroutines.flow.Flow


interface GenerationsRepository {
    fun getAllGenerationsStream(): Flow<List<GenerationEntity>>
    fun getGenerationStream(id: Int): Flow<GenerationEntity?>
    suspend fun getGenerationCount(): Int
    suspend fun upsertGeneration(generation: GenerationEntity)
}