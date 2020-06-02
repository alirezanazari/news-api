package ir.alirezanazari.newsapi.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.alirezanazari.newsapi.data.db.entity.ArticleEntity
import ir.alirezanazari.newsapi.internal.Constants.DB.ARTICLES_TABLE
import org.threeten.bp.LocalDateTime

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sources: List<ArticleEntity>)

    @Query("select * from $ARTICLES_TABLE")
    suspend fun getArticles(): List<ArticleEntity>

    @Query("select * from $ARTICLES_TABLE where source= :selectedSource")
    suspend fun getArticles(selectedSource: String): List<ArticleEntity>

    @Query("select source from $ARTICLES_TABLE limit 1")
    suspend fun getSavedSource(): String?

    @Query("select addedDate from $ARTICLES_TABLE limit 1")
    suspend fun getSavedTime(): String?

    @Query("delete from $ARTICLES_TABLE where date(addedDate) < date(:selectedDate)")
    suspend fun clear(selectedDate: LocalDateTime)

    @Query("delete from $ARTICLES_TABLE")
    suspend fun clear()

}