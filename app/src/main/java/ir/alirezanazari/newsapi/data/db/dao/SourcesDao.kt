package ir.alirezanazari.newsapi.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.alirezanazari.newsapi.data.db.entity.SourceEntity
import ir.alirezanazari.newsapi.internal.Constants.DB.SOURCES_TABLE

@Dao
interface SourcesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sources: List<SourceEntity>)

    @Query("select * from $SOURCES_TABLE")
    suspend fun getSources(): List<SourceEntity>

    @Query("delete from $SOURCES_TABLE")
    suspend fun clear()


}