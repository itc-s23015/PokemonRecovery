package jp.ac.it_college.std.nakasone.android.pokequiz.data.repository.impl

import jp.ac.it_college.std.nakasone.android.pokequiz.data.dao.GenerationDao
import jp.ac.it_college.std.nakasone.android.pokequiz.data.entity.GenerationEntity
import jp.ac.it_college.std.nakasone.android.pokequiz.data.repository.GenerationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenerationsRepositoryImpl @Inject constructor(
    private val dao: GenerationDao
) : GenerationsRepository {
    override fun getAllGenerationsStream(): Flow<List<GenerationEntity>> = dao.getAllGenerations()

    override fun getGenerationStream(id: Int): Flow<GenerationEntity?> = dao.getGenerationById(id)

    override suspend fun getGenerationCount(): Int = dao.getGenerationCount()

    override suspend fun upsertGeneration(generation: GenerationEntity) = dao.upsert(generation)
}