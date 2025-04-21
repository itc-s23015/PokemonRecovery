package jp.ac.it_college.std.s23015.android.pokequiz.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypes(
    val name: String,
    val types: List<PokemonTypeSlot>
)