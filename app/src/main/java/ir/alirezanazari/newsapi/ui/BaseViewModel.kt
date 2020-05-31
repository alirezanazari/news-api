package ir.alirezanazari.newsapi.ui

import androidx.lifecycle.ViewModel
import ir.alirezanazari.newsapi.internal.SingleLiveEvent


abstract class BaseViewModel : ViewModel(){

    val loaderVisibilityListener = SingleLiveEvent<Boolean>()
    val errorVisibilityListener = SingleLiveEvent<Boolean>()

    protected fun setLoaderState(state: Boolean , isEffectRetry: Boolean = false){
        if (state){
            loaderVisibilityListener.postValue(true)
            errorVisibilityListener.postValue(false)
        }else{
            loaderVisibilityListener.postValue(false)
            errorVisibilityListener.postValue(isEffectRetry)
        }
    }
}