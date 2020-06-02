package ir.alirezanazari.newsapi.ui.sources

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.alirezanazari.newsapi.R
import ir.alirezanazari.newsapi.data.net.entity.sources.Source
import kotlinx.android.synthetic.main.row_source_list.view.*


class SourcesAdapter : RecyclerView.Adapter<SourcesAdapter.SourceViewHolder>() {

    private var items: List<Source> = ArrayList()
    var onItemClicked: ((item: Source) -> Unit)? = null

    fun setItems(data: List<Source>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        return SourceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_source_list, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) =
        holder.bind(items[position])

    inner class SourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Source) {
            itemView.apply {
                tvTitle.text = "${item.name} - ${item.category}"
                tvDescription.text = item.description
            }

            itemView.setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }
}