package jp.ac.it_college.std.s23015.android.pokequiz.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Type(
    val id: Int,
    val name: String,
    val mainType: NamedApiResource,
    val names: List<Name>,
    val pokemonSpecies: List<NamedApiResource>
)