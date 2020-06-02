package ir.alirezanazari.newsapi.data.net

import ir.alirezanazari.newsapi.data.net.entity.newsList.Article
import ir.alirezanazari.newsapi.data.net.entity.sources.Source
import ir.alirezanazari.newsapi.internal.Logger
import retrofit2.HttpException
import java.io.IOException

class NetworkDataManagerImpl(
    private val api: Api
) : NetworkDataManager {

    override suspend fun getSourcesList(): List<Source>? {
        return try {
            val response = api.getSources().await()
            response.sources
        } catch (e: HttpException) {
            Logger.showLog("Error fetch source list : ${e.message()}")
            null
        } catch (ex: IOException) {
            Logger.showLog("Error in connection : ${ex.message}")
            null
        }
    }

    override suspend fun getSourceNews(id: String, page: Int): List<Article>? {
        return try {
            val response = api.getNews(id, page).await()
            Logger.showLog("server response ${response.articles.size} items")
            response.articles
        } catch (e: HttpException) {
            Logger.showLog("Error fetch articles : ${e.message()}")
            null
        } catch (ex: IOException) {
            Logger.showLog("Error in connection : ${ex.message}")
            null
        }
    }
}