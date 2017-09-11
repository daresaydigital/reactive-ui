package com.rongi.github.kotlin.rxui.reactive.app.main

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.busyglide.shared.Article
import com.busyglide.shared.ArticleListAdapter
import com.busyglide.shared.ArticlesService
import com.busyglide.shared.support.toast
import com.busyglide.shared.support.visible
import com.rongi.github.kotlin.rxui.reactive.R
import com.rongi.github.kotlin.rxui.reactive.app.App
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainActivity : Activity() {

  @Inject
  lateinit var articlesService: ArticlesService

  private val list by lazy {
    findViewById(R.id.list) as RecyclerView
  }

  private val updateButton by lazy {
    findViewById(R.id.update_button)
  }

  private val progress by lazy {
    findViewById(R.id.progress)
  }

  private val emptyView by lazy {
    findViewById(R.id.empty_view)
  }

  private val articlesCounter by lazy {
    findViewById(R.id.articles_counter) as TextView
  }

  private val listItemClicks = PublishSubject.create<Int>()

  private val articleListAdapter: ArticleListAdapter by lazy {
    ArticleListAdapter().apply {
      onItemClickListener = { listItemClicks.onNext(it) }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    App.roots.inject(this)

    list.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = articleListAdapter
    }

    val models = present(
      articlesService = articlesService,
      updateButtonClicks = updateButton.clicks(),
      clickedArticlePosition = listItemClicks
    )

    with(models) {
      articles.subscribe {
        showArticles(it)
      }

      emptyViewVisibility.subscribe { show: Boolean ->
        emptyView.visible = show
      }

      showToastSignal.subscribe { "CLICK: \"$it\"".showToast() }

      render(articlesCounter, articleCounterModels)
    }

  }

  private fun showArticles(articles: List<Article>) {
    articleListAdapter.articles = articles
  }

  private fun String.showToast() {
    this.toast(this@MainActivity)
  }

}

fun View.clicks(): Observable<Unit> {
  val updateButtonClicks = PublishSubject.create<Unit>()
  this.setOnClickListener { updateButtonClicks.onNext(Unit) }
  return updateButtonClicks
}

