package ir.alirezanazari.newsapi.data.net

import ir.alirezanazari.newsapi.data.net.entity.sources.SourcesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    @GET("sources")
    fun getSources(
        @Query("language") language: String = "en"
    ): Deferred<SourcesResponse>
}