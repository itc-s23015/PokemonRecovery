package jp.ac.it_college.std.nakasone.android.pokequiz.asset

import kotlinx.serialization.Serializable

/**
 * assets/pokemon.json を読み込むときに使うデータクラス
 */
@Serializable
data class PokemonAsset(
    val id: Int,
    val name: String,
    val officialArtwork: String
)
