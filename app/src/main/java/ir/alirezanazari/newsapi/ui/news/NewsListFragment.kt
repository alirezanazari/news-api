package ir.alirezanazari.newsapi.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ir.alirezanazari.newsapi.R
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
            mId = it.getString(ID_KEY , "")
            mTitle = it.getString(TITLE_KEY , "News")
            tvTitle.text = mTitle

            mCurrentPage = 1
            viewModel.getNewsOfSource(mId , mCurrentPage)
        }
    }

    private fun setupRecyclerview(){
        rvNews.apply {
            adapter = newsAdapter
        }
    }

    private fun setupListeners(){
        btnBack.setOnClickListener {
            popupBackStack()
        }

        btnRetry.setOnClickListener {
            mCurrentPage = 1
            viewModel.getNewsOfSource(mId , mCurrentPage)
        }

        viewModel.newsResponse.observe(viewLifecycleOwner , Observer {
            it?.let {news ->
                newsAdapter.setItems(news)
            }
        })

        viewModel.loaderVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                pbLoading.visibility = if (state) View.VISIBLE else View.GONE
            }
        })

        viewModel.errorVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                if (state) {
                    tvError.visibility = View.VISIBLE
                    btnRetry.visibility = View.VISIBLE
                } else {
                    tvError.visibility = View.GONE
                    btnRetry.visibility = View.GONE
                }
            }
        })

    }

    override fun onBackPressed(): Boolean = true
}
