package jp.ac.it_college.std.s23000.android.pokequiz.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.ac.it_college.std.s23000.android.pokequiz.data.PokeQuizDatabase
import jp.ac.it_college.std.s23000.android.pokequiz.data.repository.GenerationsRepository
import jp.ac.it_college.std.s23000.android.pokequiz.data.repository.PokemonIntroducedGenerationRepository
import jp.ac.it_college.std.s23000.android.pokequiz.data.repository.PokemonRepository
import jp.ac.it_college.std.s23000.android.pokequiz.data.repository.impl.GenerationsRepositoryImpl
import jp.ac.it_college.std.s23000.android.pokequiz.data.repository.impl.PokemonRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            PokeQuizDatabase::class.java,
            "poke_quiz.db"
        ).build()

    @Singleton
    @Provides
    fun provideGenerationDao(db: PokeQuizDatabase) = db.generationDao

    @Singleton
    @Provides
    fun providePokemonDao(db: PokeQuizDatabase) = db.pokemonDao

    @Singleton
    @Provides
    fun providePokemonIntroducedGenerationDao(db: PokeQuizDatabase) =
        db.pokemonIntroducedGenerationDao
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindGenerationsRepository(impl: GenerationsRepositoryImpl): GenerationsRepository

    @Binds
    abstract fun bindPokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository

    @Binds
    abstract fun bindPokemonIntroducedGenerationRepository(impl: GenerationsRepositoryImpl): PokemonIntroducedGenerationRepository
}