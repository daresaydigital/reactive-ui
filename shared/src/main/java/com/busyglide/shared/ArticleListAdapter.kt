package com.busyglide.shared

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ArticleListAdapter : RecyclerView.Adapter<ViewHolder>() {

  var articles: List<Article> = emptyList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  var onItemClickListener: ((Int) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val article = articles[position]
    holder.title.text = article.title
    holder.itemView.setOnClickListener { onItemClickListener?.invoke(position) }
  }

  override fun getItemCount(): Int {
    return articles.count()
  }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  val title = itemView.findViewById(R.id.title) as TextView

}
