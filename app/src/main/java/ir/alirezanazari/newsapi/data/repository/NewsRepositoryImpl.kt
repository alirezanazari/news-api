package ir.alirezanazari.newsapi.data.repository

import ir.alirezanazari.newsapi.data.db.dao.SourcesDao
import ir.alirezanazari.newsapi.data.db.entity.SourceEntity
import ir.alirezanazari.newsapi.data.net.NetworkDataManager
import ir.alirezanazari.newsapi.data.net.entity.sources.Source

class NewsRepositoryImpl(
    private val net: NetworkDataManager,
    private val sourcesDao: SourcesDao
) : NewsRepository {

    override suspend fun getSourcesList(): List<Source>? {

        val netResponse = net.getSourcesList()

        if (netResponse == null) {
            val dbResponse = sourcesDao.getSources().map(::transformToSource)
            if (dbResponse.isNotEmpty()) return dbResponse
        } else {
            sourcesDao.clear()
            sourcesDao.insert(netResponse.map(::transformToSourceEntity))
        }

        return null
    }

    private fun transformToSourceEntity(source: Source): SourceEntity {
        return SourceEntity(
            source.id,
            source.name,
            source.description,
            source.url,
            source.category
        )
    }

    private fun transformToSource(se: SourceEntity): Source {
        return Source(
            se.id,
            se.name,
            se.description,
            se.url,
            se.category
        )
    }
}