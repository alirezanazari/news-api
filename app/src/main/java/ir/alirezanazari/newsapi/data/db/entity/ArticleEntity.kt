package ir.alirezanazari.newsapi.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ir.alirezanazari.newsapi.internal.Constants.DB.ARTICLES_TABLE

@Entity(tableName = ARTICLES_TABLE, indices = [Index(value = ["addedDate"], unique = true)])
data class ArticleEntity(
    val source: String? = null,
    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    val image: String? = null,
    val publishedDate: String,
    val content: String? = null,
    val addedDate: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)