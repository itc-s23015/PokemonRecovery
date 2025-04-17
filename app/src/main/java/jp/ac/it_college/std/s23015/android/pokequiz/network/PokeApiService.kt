package jp.ac.it_college.std.s23015.android.pokequiz.network

import jp.ac.it_college.std.s23015.android.pokequiz.network.model.Generation
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.Named
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.PokemonSpecies
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.Region
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.Type
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * [PokeAPI v2](https://pokeapi.co/docs/v2) の各エンドポイントのベースとなる URL
 *
 */
private const val BASE_URL = "https://pokeapi.co/api/v2/"

/**
 * PokeAPI へアクセスして取得した JSON 文字列をパースするオブジェクト
 *
 * 各データモデルではアプリケーションで使用する予定のプロパティのみ定義しているので
 * 定義されていない JSON キーは無視するオプション [kotlinx.serialization.json.JsonBuilder.ignoreUnknownKeys] を有効化
 *
 * JSON 文字列側のキー名は SnakeCase のため、[kotlinx.serialization.json.JsonBuilder.namingStrategy] オプションに
 * [JsonNamingStrategy.SnakeCase] を指定して自動変換を有効化
 */
@OptIn(ExperimentalSerializationApi::class)
private val json = Json {
    ignoreUnknownKeys = true
    namingStrategy = JsonNamingStrategy.SnakeCase
}

/**
 * [Retrofit] のインスタンス。
 *
 * [Retrofit.baseUrl] へ [BASE_URL] を設定することで
 * サービスインタフェースではエンドポイント名のみ指定できるようになる
 */
internal val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(
        json.asConverterFactory(
            "application/json; charset=UTF-8".toMediaType()
        )
    )
    .build()

/**
 * [PokeAPI v2](https://pokeapi.co/docs/v2) のうち
 * アプリケーションで使用する予定のエンドポイントのみ抜粋で定義したサービスインタフェース
 */
interface PokeApiService {
    @GET("generation")
    suspend fun getGenerations(): Named

    @GET("generation/{id}")
    suspend fun getGenerationById(@Path("id") id: Int): Generation

    @GET("generation/{name}")
    suspend fun getGenerationByName(@Path("name") name: String): Generation

    @GET("region/{name}")
    suspend fun getRegionByName(@Path("name") name: String): Region


    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpeciesByName(@Path("name") name: String): PokemonSpecies

    @GET("type/{id}")
    suspend fun getTypeById(@Path("id") id: Int): Type

    @GET("type/{name}")
    suspend fun getTypeByName(@Path("name") name: String): Type
}