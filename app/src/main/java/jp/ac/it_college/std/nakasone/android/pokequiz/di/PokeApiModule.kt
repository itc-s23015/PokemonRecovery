package jp.ac.it_college.std.nakasone.android.pokequiz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.ac.it_college.std.nakasone.android.pokequiz.network.PokeApi
import jp.ac.it_college.std.nakasone.android.pokequiz.network.PokeApiService
import javax.inject.Singleton

/**
 * Hilt 用 PokeAPI へアクセスするサービスモジュール
 */
@Module
@InstallIn(SingletonComponent::class)
object PokeApiModule {
    @Singleton
    @Provides
    fun providePokeApiService(): PokeApiService = PokeApi.service
}