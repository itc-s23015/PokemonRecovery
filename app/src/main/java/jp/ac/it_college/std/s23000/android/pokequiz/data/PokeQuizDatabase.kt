package jp.ac.it_college.std.s23000.android.pokequiz.data

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.ac.it_college.std.s23000.android.pokequiz.data.dao.GenerationDao
import jp.ac.it_college.std.s23000.android.pokequiz.data.entity.GenerationEntity

@Database(
    entities = [
        GenerationEntity::class,
    ],
    version = 1,
    autoMigrations = [

    ]
)
abstract class PokeQuizDatabase : RoomDatabase() {
    abstract val generationDao: GenerationDao
}