package jp.ac.it_college.std.nakasone.android.pokequiz.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "generations")
data class GenerationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val region: String,
)
