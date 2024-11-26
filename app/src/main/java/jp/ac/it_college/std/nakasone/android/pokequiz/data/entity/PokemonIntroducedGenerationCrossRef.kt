package jp.ac.it_college.std.nakasone.android.pokequiz.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "pokemon_introduced_generation_cross_ref",
    primaryKeys = ["generation_id", "pokemon_id"]
)
data class PokemonIntroducedGenerationCrossRef(
    @ColumnInfo(name = "generation_id") val generationId: Int,
    @ColumnInfo(name = "pokemon_id", index = true) val pokemonId: Int,
)

data class GenerationWithPokemon(
    @Embedded val generation: GenerationEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PokemonIntroducedGenerationCrossRef::class,
            parentColumn = "generation_id",
            entityColumn = "pokemon_id",
        )
    )
    val pokemon: List<PokemonEntity>
)
