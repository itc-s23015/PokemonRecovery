package jp.ac.it_college.std.s23015.android.pokequiz.network.model

import kotlinx.serialization.Serializable

/**
 * PokeAPI が定義する汎用モデルのうち、使用する予定のモデルのみ定義
 */

/**
 * [PokeAPI / Common Models / NamedAPIResource](https://pokeapi.co/docs/v2)
 */
@Serializable
data class NamedApiResource(
    val name: String,
    val url: String,
)

/**
 * [PokeAPI / Common Models / Name](https://pokeapi.co/docs/v2)
 */
@Serializable
data class Name(
    val name: String,
    val language: NamedApiResource
)

/**
 * [PokeAPI / Common Models / Named](https://pokeapi.co/docs/v2)
 */
@Serializable
data class Named(
    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<NamedApiResource> = emptyList()
)

/**
 * [Name] のプロパティ [Name.language] が日本語(ja)かをチェックするための拡張プロパティ
 */
val Name.isJa: Boolean get() = language.name == "ja"

/**
 * [Name]のプロパティ[Name.language]がひらがな・カタカナ(ja-Hrkt)かをチェックするための拡張プロパティ
 */
val Name.isJaHrkt: Boolean get() = language.name == "ja-Hrkt"