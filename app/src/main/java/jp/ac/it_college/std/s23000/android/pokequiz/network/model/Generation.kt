package jp.ac.it_college.std.s23000.android.pokequiz.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Generation(
    val id: Int,
    val name: String,
    val mainRegion: NamedApiResource,
    val names: List<Name>,
    val pokemonSpecies: List<NamedApiResource>
)
