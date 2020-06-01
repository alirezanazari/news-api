package ir.alirezanazari.newsapi.data.repository

import ir.alirezanazari.newsapi.data.net.entity.sources.Source


interface NewsRepository {

    suspend fun getSourcesList(): List<Source>?
}