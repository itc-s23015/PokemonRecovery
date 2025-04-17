package jp.ac.it_college.std.s23015.android.pokequiz.mock


import jp.ac.it_college.std.s23015.android.pokequiz.network.PokeApiService
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.Generation
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.Named
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.NamedApiResource
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.PokemonSpecies
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.Region
import jp.ac.it_college.std.s23015.android.pokequiz.network.model.Type

/**
 * [PokeApiService] のモックオブジェクト
 */
object PokeApiServiceMock : PokeApiService {
    override suspend fun getGenerations(): Named = Named()

    override suspend fun getGenerationById(id: Int): Generation = Generation(
        id = 1,
        name = "ダミー",
        mainRegion = NamedApiResource(name = "ダミー地方", url = "http://example.com/"),
        names = emptyList(),
        pokemonSpecies = emptyList()
    )

    override suspend fun getTypeById(id: Int): Type = Type(
        id = 1,
        name = "ダミー(タイプ)",
        mainType = NamedApiResource(name = "ダミータイプ", url = "http://example.com/"),
        names = emptyList(),
        pokemonSpecies = emptyList()
    )


    override suspend fun getGenerationByName(name: String): Generation = getGenerationById(1)

    override suspend fun getRegionByName(name: String): Region = Region(id = 1, names = emptyList())

    override suspend fun getTypeByName(name: String): Type = getTypeById(1)

    override suspend fun getPokemonSpeciesByName(name: String): PokemonSpecies = PokemonSpecies(2)
}