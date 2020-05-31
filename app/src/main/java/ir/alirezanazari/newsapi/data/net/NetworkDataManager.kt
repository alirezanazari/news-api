package ir.alirezanazari.newsapi.data.net

import ir.alirezanazari.newsapi.data.net.entity.newsList.Article
import ir.alirezanazari.newsapi.data.net.entity.sources.Source


interface NetworkDataManager {

    suspend fun getSourcesList(): List<Source>?

    suspend fun getSourceNews(id:String , page: Int): List<Article>?
}