package ir.alirezanazari.newsapi.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alirezanazari.newsapi.R
import ir.alirezanazari.newsapi.data.net.entity.newsList.Article
import ir.alirezanazari.newsapi.internal.Logger
import ir.alirezanazari.newsapi.ui.BaseFragment
import kotlinx.android.synthetic.main.news_list_fragment.*
import org.koin.android.ext.android.inject

class NewsListFragment : BaseFragment() {

    companion object {
        private const val ID_KEY = "id"
        private const val TITLE_KEY = "title"

        fun newInstance(id: String, title: String): NewsListFragment {
            return NewsListFragment().apply {
                arguments = bundleOf(ID_KEY to id, TITLE_KEY to title)
            }
        }
    }

    private val viewModel: NewsListViewModel by inject()
    private val newsAdapter: NewsAdapter by inject()
    private lateinit var mId: String
    private lateinit var mTitle: String
    private var mCurrentPage = 1
    private var isEndOfNews = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupRecyclerview()
        arguments?.let {
            mId = it.getString(ID_KEY, "")
            mTitle = it.getString(TITLE_KEY, "News")
            tvTitle.text = mTitle

            mCurrentPage = 1
            viewModel.getNewsOfSource(mId, mCurrentPage)
        }
    }

    private fun setupRecyclerview() {
        val lManager = LinearLayoutManager(rvNews.context)
        rvNews.apply {
            adapter = newsAdapter
            layoutManager = lManager
        }

        var visibleItemCount: Int
        var totalItemCount: Int
        var pastItemCount: Int

        rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = lManager.childCount
                    totalItemCount = lManager.itemCount
                    pastItemCount = lManager.findFirstVisibleItemPosition()

                    if (!viewModel.isLoading && !isEndOfNews) {
                        if ((visibleItemCount + pastItemCount) >= totalItemCount) {
                            mCurrentPage++
                            viewModel.getNewsOfSource(mId, mCurrentPage)
                        }
                    }
                }
            }
        })

    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            popupBackStack()
        }

        btnRetry.setOnClickListener {
            isEndOfNews = false
            mCurrentPage = 1
            viewModel.getNewsOfSource(mId, mCurrentPage)
        }

        viewModel.newsResponse.observe(viewLifecycleOwner, Observer {
            it?.let { news ->
                handleNewsResponse(news)
            }
        })

        viewModel.loaderVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                if (mCurrentPage == 1) {
                    pbLoading.visibility = if (state) View.VISIBLE else View.GONE
                }
            }
        })

        viewModel.errorVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                if (state) {
                    if (mCurrentPage == 1) {
                        tvError.visibility = View.VISIBLE
                        btnRetry.visibility = View.VISIBLE
                    } else {
                        Logger.showToast(tvError.context, R.string.no_item)
                    }
                } else {
                    tvError.visibility = View.GONE
                    btnRetry.visibility = View.GONE
                }
            }
        })

    }

    private fun handleNewsResponse(news: List<Article>) {
        if (mCurrentPage != 1) newsAdapter.removeLoader()
        newsAdapter.setItems(news)
        if (news.isNotEmpty()) {
            newsAdapter.addLoader()
        } else {
            isEndOfNews = true
        }

    }

    override fun onBackPressed(): Boolean = true
}
