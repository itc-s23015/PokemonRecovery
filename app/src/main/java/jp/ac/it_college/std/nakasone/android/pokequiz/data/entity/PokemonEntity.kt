package jp.ac.it_college.std.nakasone.android.pokequiz.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * pokemon テーブルに対応するエンティティクラス
 */
@Entity("pokemon")
data class PokemonEntity(
    @PrimaryKey() val id: Int,
    val name: String,
    @ColumnInfo(name = "official_artwork") val officialArtwork: String,
)
