package jp.ac.it_college.std.s23015.android.pokequiz.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import jp.ac.it_college.std.s23015.android.pokequiz.data.dao.GenerationDao
import jp.ac.it_college.std.s23015.android.pokequiz.data.dao.PokemonDao
import jp.ac.it_college.std.s23015.android.pokequiz.data.dao.PokemonIntroducedGenerationDao
import jp.ac.it_college.std.s23015.android.pokequiz.data.dao.PokemonIntroducedTypeDao
import jp.ac.it_college.std.s23015.android.pokequiz.data.dao.TypeDao
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.GenerationEntity
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.PokemonEntity
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.PokemonIntroducedGenerationCrossRef
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.PokemonIntroducedTypeCrossRef
import jp.ac.it_college.std.s23015.android.pokequiz.data.entity.TypeEntity

/**
 * [RoomDatabase] クラスを継承したアプリケーションで使用するデータベースクラス
 */
@Database(
    entities = [
        GenerationEntity::class,
        PokemonEntity::class,
        TypeEntity::class,
        PokemonIntroducedGenerationCrossRef::class,
        PokemonIntroducedTypeCrossRef::class,
    ],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
    ]
)
abstract class PokeQuizDatabase : RoomDatabase() {
    abstract val generationDao: GenerationDao
    abstract val typeDao: TypeDao
    abstract val pokemonDao: PokemonDao
    abstract val pokemonIntroducedGenerationDao: PokemonIntroducedGenerationDao
    abstract val pokemonIntroducedTypeDao: PokemonIntroducedTypeDao
}