package ir.alirezanazari.newsapi.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.alirezanazari.newsapi.R
import ir.alirezanazari.newsapi.data.net.entity.newsList.Article
import ir.alirezanazari.newsapi.internal.ImageLoader
import kotlinx.android.synthetic.main.row_news_list.view.*


class NewsAdapter(
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var items: List<Article> = ArrayList()
    var onItemClicked: ((item: Article) -> Unit)? = null

    fun setItems(data: List<Article>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_news_list, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) =
        holder.bind(items[position])

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Article) {
            itemView.apply {
                tvTitle.text = item.title
                tvDescription.text = item.description
                tvAuthor.text = if (item.author.isNullOrEmpty()) "" else "- ${item.author}"
                imageLoader.load(ivPicture , item.urlToImage , R.drawable.placeholder)
            }

            itemView.setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }
}