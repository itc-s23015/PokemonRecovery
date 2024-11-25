package jp.ac.it_college.std.s23000.android.pokequiz.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("pokemon")
data class PokemonEntity(
    @PrimaryKey() val id: Int,
    val name: String,
    @ColumnInfo(name = "official_artwork") val officialArtwork: String,
)
