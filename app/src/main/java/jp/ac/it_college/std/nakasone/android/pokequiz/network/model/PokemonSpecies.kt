package jp.ac.it_college.std.nakasone.android.pokequiz.network.model

import kotlinx.serialization.Serializable

/**
 * PokeAPI の Pokemon Species (ポケモン種族 属性) エンドポイントが返すデータモデル
 *
 * アプリで使用する分のプロパティのみ抜粋で定義
 *
 * [PokeAPI / Pokémon / Pokemon Species](https://pokeapi.co/docs/v2#pokemon-species)
 */
@Serializable
data class PokemonSpecies(
    val id: Int
)
