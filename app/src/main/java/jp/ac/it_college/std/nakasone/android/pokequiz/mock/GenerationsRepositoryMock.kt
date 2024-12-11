package jp.ac.it_college.std.nakasone.android.pokequiz.mock

import jp.ac.it_college.std.nakasone.android.pokequiz.data.entity.GenerationEntity
import jp.ac.it_college.std.nakasone.android.pokequiz.data.repository.GenerationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * [GenerationsRepository] のモックオブジェクト
 */
object GenerationsRepositoryMock : GenerationsRepository {
    override fun getAllGenerationsStream(): Flow<List<GenerationEntity>> = flowOf(
        listOf(
            GenerationEntity(id = 1, name = "第1世代", "カントー"),
            GenerationEntity(id = 2, name = "第2世代", "ジョウト"),
        )
    )

    override fun getGenerationStream(id: Int): Flow<GenerationEntity?> = flowOf(
        GenerationEntity(id = 1, name = "第1世代", "カントー"),
    )

    override suspend fun getGenerationCount(): Int = 2

    override suspend fun upsertGeneration(generation: GenerationEntity) {}
}