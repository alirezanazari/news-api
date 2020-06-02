package ir.alirezanazari.newsapi.internal

import ir.alirezanazari.newsapi.data.db.NewsDatabase
import ir.alirezanazari.newsapi.data.net.ApiConfig
import ir.alirezanazari.newsapi.data.net.NetworkDataManager
import ir.alirezanazari.newsapi.data.net.NetworkDataManagerImpl
import ir.alirezanazari.newsapi.data.repository.NewsRepository
import ir.alirezanazari.newsapi.data.repository.NewsRepositoryImpl
import ir.alirezanazari.newsapi.ui.news.NewsListViewModel
import ir.alirezanazari.newsapi.ui.sources.SourcesAdapter
import ir.alirezanazari.newsapi.ui.sources.SourcesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModules = module {

    single { ApiConfig.invoke() }
    single<NetworkDataManager> { NetworkDataManagerImpl(get()) }
    single { NewsDatabase(get()) }

    factory<NewsRepository> { NewsRepositoryImpl(get() , get<NewsDatabase>().sourceDao(), get<NewsDatabase>().articleDao()) }
    factory { SourcesAdapter() }

    viewModel { SourcesViewModel(get()) }
    viewModel { NewsListViewModel(get()) }
}