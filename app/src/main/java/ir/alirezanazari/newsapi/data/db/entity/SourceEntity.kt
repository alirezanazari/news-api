package ir.alirezanazari.newsapi.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.alirezanazari.newsapi.internal.Constants.DB.SOURCES_TABLE

@Entity(tableName = SOURCES_TABLE)
data class SourceEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String
)