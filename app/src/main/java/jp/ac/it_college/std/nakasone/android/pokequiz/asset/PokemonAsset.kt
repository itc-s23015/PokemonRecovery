package jp.ac.it_college.std.nakasone.android.pokequiz.asset

import kotlinx.serialization.Serializable

@Serializable
data class PokemonAsset(
    val id: Int,
    val name: String,
    val officialArtwork: String
)
