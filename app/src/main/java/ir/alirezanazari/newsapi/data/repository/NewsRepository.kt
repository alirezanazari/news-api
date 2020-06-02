package ir.alirezanazari.newsapi.data.repository

import ir.alirezanazari.newsapi.data.net.entity.newsList.Article
import ir.alirezanazari.newsapi.data.net.entity.sources.Source


interface NewsRepository {

    suspend fun getSourcesList(): List<Source>?
    suspend fun getNewsList(sourceId: String, page: Int): List<Article>?
}