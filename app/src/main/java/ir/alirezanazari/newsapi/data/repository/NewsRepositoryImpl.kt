package ir.alirezanazari.newsapi.data.repository

import ir.alirezanazari.newsapi.data.db.DateConverter
import ir.alirezanazari.newsapi.data.db.dao.ArticlesDao
import ir.alirezanazari.newsapi.data.db.dao.SourcesDao
import ir.alirezanazari.newsapi.data.db.entity.ArticleEntity
import ir.alirezanazari.newsapi.data.db.entity.SourceEntity
import ir.alirezanazari.newsapi.data.net.NetworkDataManager
import ir.alirezanazari.newsapi.data.net.entity.newsList.Article
import ir.alirezanazari.newsapi.data.net.entity.newsList.ArticleSource
import ir.alirezanazari.newsapi.data.net.entity.sources.Source
import ir.alirezanazari.newsapi.internal.Logger
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import kotlin.math.abs

class NewsRepositoryImpl(
    private val net: NetworkDataManager,
    private val sourcesDao: SourcesDao,
    private val articlesDao: ArticlesDao
) : NewsRepository {

    override suspend fun getSourcesList(): List<Source>? {

        val netResponse = net.getSourcesList()

        if (netResponse == null) {
            val dbResponse = sourcesDao.getSources().map(::transformToSource)
            if (dbResponse.isNotEmpty()) return dbResponse
        } else {
            sourcesDao.clear()
            sourcesDao.insert(netResponse.map(::transformToSourceEntity))
            return netResponse
        }

        return null
    }

    /**
     * database just save single source's news and first page.
     * first check saved source, if was equal to received source in input check time ,
     * if time was fewer than 15 minute after saved, data shown , else if was greater
     * or was not equal fetch data from server and saved to database and shown to user.
     */
    override suspend fun getNewsList(sourceId: String, page: Int): List<Article>? {
        Logger.showLog("fetch news $sourceId , page $page")
        val previousSavedSource = articlesDao.getSavedSource()
        Logger.showLog("prev saved db $previousSavedSource")
        return if (page == 1) {
            if (previousSavedSource == sourceId) {
                //check time difference
                val now = LocalDateTime.now()
                val previousSavedTime = DateConverter.stringToDate(articlesDao.getSavedTime())
                val minutesDiff = abs(Duration.between(now, previousSavedTime).toMinutes())

                Logger.showLog("time different $minutesDiff minutes")
                if (minutesDiff <= 15) {
                    articlesDao.getArticles().map { transformToArticles(it, sourceId) }
                } else {
                    getNewsFromServerAndSaveToDb(sourceId, page)
                }
            } else {
                getNewsFromServerAndSaveToDb(sourceId, page)
            }
        } else {
            getNewsFromServer(sourceId, page)
        }
    }

    private suspend fun getNewsFromServerAndSaveToDb(sourceId: String, page: Int): List<Article>? {
        val netResponse = getNewsFromServer(sourceId, page)
        articlesDao.clear()
        if (!netResponse.isNullOrEmpty()) {
            articlesDao.insert(netResponse.map(::transformToArticleEntity))
        }
        Logger.showLog("saved ${netResponse?.size} to db")
        return netResponse
    }

    private suspend fun getNewsFromServer(sourceId: String, page: Int): List<Article>? {
        Logger.showLog("get from server")
        return net.getSourceNews(sourceId, page)
    }

    private fun transformToArticleEntity(article: Article): ArticleEntity {
        return ArticleEntity(
            article.source.id,
            article.author,
            article.title,
            article.description,
            article.url,
            article.urlToImage,
            article.publishedAt,
            article.content,
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )
    }

    private fun transformToArticles(ae: ArticleEntity, sourceId: String): Article {
        return Article(
            ArticleSource(sourceId, ae.source),
            ae.author,
            ae.title,
            ae.description,
            ae.url,
            ae.image,
            ae.publishedDate,
            ae.content
        )
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