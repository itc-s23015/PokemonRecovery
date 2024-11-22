package jp.ac.it_college.std.s23000.android.pokequiz.network

import android.graphics.Region
import jp.ac.it_college.std.s23000.android.pokequiz.network.model.Generation
import jp.ac.it_college.std.s23000.android.pokequiz.network.model.Named
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://pokeapi.co/api/v2/"

@OptIn(ExperimentalSerializationApi::class)
private val json = Json {
    ignoreUnknownKeys = true
    namingStrategy = JsonNamingStrategy.SnakeCase
}
internal val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(
        json.asConverterFactory(
            "application/json; charset=UTF-8".toMediaType()
        )
    )
    .build()

interface PokeApiService {
    @GET("generation")
    suspend fun getGenerations(): Named

    @GET("generation/{id}")
    suspend fun getGenerationById(@Path("id") id: Int): Generation

    @GET("generation/{name}")
    suspend fun getGenerationByName(@Path("name") name: String): Generation

    @GET("regin/{name}")
    suspend fun getRegionByName(@Path("name") name: String): Region
}