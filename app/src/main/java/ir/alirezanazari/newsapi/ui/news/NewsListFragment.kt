package ir.alirezanazari.newsapi.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.alirezanazari.newsapi.R
import ir.alirezanazari.newsapi.ui.BaseFragment

class NewsListFragment : BaseFragment() {

    companion object {
        fun newInstance(id: String) = NewsListFragment()
    }

    private lateinit var viewModel: NewsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed(): Boolean = true
}
