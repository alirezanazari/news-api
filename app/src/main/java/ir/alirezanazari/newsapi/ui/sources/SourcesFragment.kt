package ir.alirezanazari.newsapi.ui.sources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import ir.alirezanazari.newsapi.R
import ir.alirezanazari.newsapi.internal.Navigator
import ir.alirezanazari.newsapi.ui.BaseFragment
import kotlinx.android.synthetic.main.sources_fragment.*
import org.koin.android.ext.android.inject

class SourcesFragment : BaseFragment() {

    private val viewModel: SourcesViewModel by inject()
    private val sourcesAdapter: SourcesAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sources_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupRecyclerView()
        viewModel.getSourcesList()
    }

    private fun setupRecyclerView() {
        rvSources.apply {
            adapter = sourcesAdapter
        }

        sourcesAdapter.onItemClicked = {
            if (activity != null) {
                Navigator.openNewsList(it.id, it.name, activity!!.supportFragmentManager)
            }
        }
    }

    private fun setupListeners() {
        btnRetry.setOnClickListener {
            viewModel.getSourcesList()
        }

        viewModel.sourcesResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                sourcesAdapter.setItems(it)
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
