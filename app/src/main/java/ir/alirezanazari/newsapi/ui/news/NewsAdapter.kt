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
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = ArrayList<Article?>()
    var onItemClicked: ((item: Article) -> Unit)? = null

    fun setItems(data: List<Article>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun addLoader() {
        items.add(null)
        notifyItemInserted(items.size - 1)
    }

    fun removeLoader() {
        if (items.size == 0) return
        val item = items[items.size - 1]
        if (item == null) {
            items.remove(item)
            notifyItemRemoved(items.size - 1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null)
            0
        else
            1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0)
            NewsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.row_news_list,
                    parent,
                    false
                )
            )
        else
            LoaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.row_loader,
                    parent,
                    false
                )
            )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoaderViewHolder) {
            holder.bind()
        } else if (holder is NewsViewHolder) {
            holder.bind(items[position]!!)
        }
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Article) {
            itemView.apply {
                tvTitle.text = item.title
                tvDescription.text = item.description
                tvAuthor.text = if (item.author.isNullOrEmpty()) "" else "- ${item.author}"
                imageLoader.load(ivPicture, item.urlToImage, R.drawable.placeholder)
            }

            itemView.setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }


    inner class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {

        }
    }

}