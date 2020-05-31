package ir.alirezanazari.newsapi.internal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ir.alirezanazari.newsapi.R
import ir.alirezanazari.newsapi.ui.category.CategoriesFragment
import ir.alirezanazari.newsapi.ui.news.NewsListFragment


class Navigator {

    companion object {

        fun openCategories(fm: FragmentManager) {
            load(true, fm, CategoriesFragment())
        }

        fun openNewsList(id: String, fm: FragmentManager?) {
            load(false, fm, NewsListFragment.newInstance(id))
        }

        private fun load(isReplace: Boolean, fm: FragmentManager?, fragment: Fragment) {
            if (isReplace) {
                fm?.let {
                    it.beginTransaction()
                        .addToBackStack(fragment.javaClass.name)
                        .replace(R.id.fragments_container, fragment, fragment.javaClass.name)
                        .commit()
                }
            } else {
                fm?.let {
                    it.beginTransaction()
                        .addToBackStack(fragment.javaClass.name)
                        .add(R.id.fragments_container, fragment, fragment.javaClass.name)
                        .commit()
                }
            }
        }
    }
}