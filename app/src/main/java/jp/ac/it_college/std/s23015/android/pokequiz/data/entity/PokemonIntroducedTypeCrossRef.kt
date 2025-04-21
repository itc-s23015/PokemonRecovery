package jp.ac.it_college.std.s23015.android.pokequiz.data.entity

/*
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "pokemon_introduced_type_cross_ref",
    primaryKeys = ["type_id", "pokemon_id"]
)
data class PokemonIntroducedTypeCrossRef(
    @ColumnInfo(name = "type_id") val typeId: Int,
    @ColumnInfo(name = "pokemon_id", index = true) val pokemonId: Int
)

data class TypeWithPokemon(
    @Embedded val type: TypeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PokemonIntroducedTypeCrossRef::class,
            parentColumn = "type_id",
            entityColumn = "pokemon_id"
        )
    )
    val pokemon: List<PokemonEntity>
)

 */