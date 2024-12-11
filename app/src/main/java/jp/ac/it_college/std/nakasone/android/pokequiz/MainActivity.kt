package jp.ac.it_college.std.nakasone.android.pokequiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import jp.ac.it_college.std.nakasone.android.pokequiz.asset.PokemonAsset
import jp.ac.it_college.std.nakasone.android.pokequiz.data.entity.PokemonEntity
import jp.ac.it_college.std.nakasone.android.pokequiz.data.repository.PokemonRepository
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.navigation.PokeQuizNavGraph
import jp.ac.it_college.std.nakasone.android.pokequiz.ui.theme.PokeQuizTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var pokemonRepository: PokemonRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        importPokemonAssetData()
        enableEdgeToEdge()
        setContent {
            PokeQuizTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) { innerPadding ->
                    PokeQuizNavGraph(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun importPokemonAssetData() {
        lifecycleScope.launch {
            if (pokemonRepository.getPokemonCount() != 0) return@launch

            val jsonStr = assets.open("pokemon.json").reader().readText()
            val json = Json { namingStrategy = JsonNamingStrategy.SnakeCase }
            val pokeList = json.decodeFromString<List<PokemonAsset>>(jsonStr)
            pokeList.forEach { poke ->
                with(poke) {
                    pokemonRepository.upsertPokemon(
                        PokemonEntity(id, name, officialArtwork)
                    )
                }
            }
        }
    }
}
