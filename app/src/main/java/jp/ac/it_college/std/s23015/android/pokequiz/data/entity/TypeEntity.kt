package jp.ac.it_college.std.s23015.android.pokequiz.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "type")
data class TypeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val region: String,
)