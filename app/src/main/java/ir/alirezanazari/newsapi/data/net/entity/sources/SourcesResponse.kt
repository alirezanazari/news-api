package ir.alirezanazari.newsapi.data.net.entity.sources


import com.google.gson.annotations.SerializedName
import ir.alirezanazari.newsapi.data.net.entity.sources.Source

data class SourcesResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("sources")
    val sources: List<Source>
)