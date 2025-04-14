package jp.ac.it_college.std.s23015.android.pokequiz.network.model

import kotlinx.serialization.Serializable

/**
 * PokeAPI の Generations (世代) エンドポイントが返すデータモデル
 *
 * アプリで使用する分のプロパティのみ抜粋で定義。
 *
 * [PokeAPI / Games / Generations](https://pokeapi.co/docs/v2#games-section)
 * @see NamedApiResource
 * @see Name
 */
@Serializable
data class Generation(
    val id: Int,
    val name: String,
    val mainRegion: NamedApiResource,
    val names: List<Name>,
    val pokemonSpecies: List<NamedApiResource>
)
