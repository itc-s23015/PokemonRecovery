package jp.ac.it_college.std.s23000.android.pokequiz.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import jp.ac.it_college.std.s23000.android.pokequiz.data.dao.GenerationDao
import jp.ac.it_college.std.s23000.android.pokequiz.data.dao.PokemonDao
import jp.ac.it_college.std.s23000.android.pokequiz.data.entity.GenerationEntity
import jp.ac.it_college.std.s23000.android.pokequiz.data.entity.PokemonEntity

@Database(
    entities = [
        GenerationEntity::class,
        PokemonEntity::class,
    ],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class PokeQuizDatabase : RoomDatabase() {
    abstract val generationDao: GenerationDao
    abstract val pokemonDao: PokemonDao
}