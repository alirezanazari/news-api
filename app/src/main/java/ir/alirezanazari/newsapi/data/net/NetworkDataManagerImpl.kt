package ir.alirezanazari.newsapi.data.net

import ir.alirezanazari.newsapi.data.net.entity.sources.Source

class NetworkDataManagerImpl(
    private val api: Api
) : NetworkDataManager {

    override suspend fun getSourcesList(): List<Source>? {
        return api.getSources().await().sources
    }
}