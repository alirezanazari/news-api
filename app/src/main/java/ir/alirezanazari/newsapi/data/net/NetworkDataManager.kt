package ir.alirezanazari.newsapi.data.net

import ir.alirezanazari.newsapi.data.net.entity.sources.Source


interface NetworkDataManager {

    suspend fun getSourcesList(): List<Source>?
}