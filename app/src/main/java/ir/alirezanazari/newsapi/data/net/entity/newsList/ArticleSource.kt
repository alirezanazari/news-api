package ir.alirezanazari.newsapi.data.net.entity.newsList


import com.google.gson.annotations.SerializedName

data class ArticleSource(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)