package jp.ac.it_college.std.s23015.android.pokequiz.asset

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
