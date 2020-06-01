package ir.alirezanazari.newsapi.ui.sources

import androidx.lifecycle.viewModelScope
import ir.alirezanazari.newsapi.data.net.entity.sources.Source
import ir.alirezanazari.newsapi.data.repository.NewsRepository
import ir.alirezanazari.newsapi.internal.SingleLiveEvent
import ir.alirezanazari.newsapi.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SourcesViewModel(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    val sourcesResponse = SingleLiveEvent<List<Source>>()

    fun getSourcesList(){
        viewModelScope.launch(Dispatchers.IO) {
            setLoaderState(true)
            val response = newsRepository.getSourcesList()

            if (response != null){
                sourcesResponse.postValue(response)
                setLoaderState(false)
            }else{
                setLoaderState(false , isEffectRetry = true)
            }
        }
    }
}
