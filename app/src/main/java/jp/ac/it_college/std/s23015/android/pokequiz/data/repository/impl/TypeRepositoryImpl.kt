package jp.ac.it_college.std.s23015.android.pokequiz.data.repository.impl

import jp.ac.it_college.std.s23015.android.pokequiz.data.dao.TypeDao
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.TypeEntity
import jp.ac.it_college.std.s23015.android.pokequiz.data.repository.TypeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TypeRepositoryImpl @Inject constructor(
    private val dao: TypeDao
) : TypeRepository {
    override fun getTypeStream(id: Int): Flow<TypeEntity> = dao. getType(id)

    override suspend fun upsertType(type: TypeEntity) = dao.upsert(type)
}