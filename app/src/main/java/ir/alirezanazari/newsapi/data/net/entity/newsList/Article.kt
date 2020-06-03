package ir.alirezanazari.newsapi.data.net.entity.newsList


import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("source")
    val source: ArticleSource,
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("content")
    val content: String?
)