package ir.alirezanazari.newsapi.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.alirezanazari.newsapi.data.net.entity.newsList.Article
import ir.alirezanazari.newsapi.data.repository.NewsRepository
import ir.alirezanazari.newsapi.internal.SingleLiveEvent
import ir.alirezanazari.newsapi.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    val newsResponse = SingleLiveEvent<List<Article>>()

    fun getNewsOfSource(id: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoaderState(true)
            val response = newsRepository.getNewsList(id, page)
            if (response != null) {
                newsResponse.postValue(response)
                setLoaderState(false)
            } else {
                setLoaderState(false, isEffectRetry = true)
            }
        }
    }
}
