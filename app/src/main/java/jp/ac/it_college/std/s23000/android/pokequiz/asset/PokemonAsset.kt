package jp.ac.it_college.std.s23000.android.pokequiz.asset

import kotlinx.serialization.Serializable

@Serializable
data class PokemonAsset(
    val id: Int,
    val name: String,
    val officialArtwork: String
)
