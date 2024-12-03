package jp.ac.it_college.std.nakasone.android.pokequiz.network.model

import kotlinx.serialization.Serializable

/**
 * PokeAPI の Regions (リージョン) エンドポイントが返すデータモデル
 *
 * アプリケーションで使用するプロパティのみ抜粋で定義
 *
 * [PokeAPI / Locations / Regions](https://pokeapi.co/docs/v2#regions)
 * @see Name
 */
@Serializable
data class Region(
    val id: Int,
    val names: List<Name>
)
