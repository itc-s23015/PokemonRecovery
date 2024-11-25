package jp.ac.it_college.std.s23000.android.pokequiz.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "pokemon_introduced_generation_cross_ref",
    primaryKeys = ["generationId", "pokemonId"]
)
data class PokemonIntroducedGenerationCrossRef(
    @ColumnInfo(name = "generation_id") val generationId: Int,
    @ColumnInfo(name = "pokemon_id") val pokemonId: Int,
)

data class GenerationWithPokemon(
    @Embedded val generation: GenerationEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PokemonIntroducedGenerationCrossRef::class,
            parentColumn = "generationId",
            entityColumn = "pokemonId",
        )
    )
    val pokemon: List<PokemonEntity>
)
