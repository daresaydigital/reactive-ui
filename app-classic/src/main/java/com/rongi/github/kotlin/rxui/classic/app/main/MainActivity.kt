package com.rongi.github.kotlin.rxui.classic.app.main

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.busyglide.shared.Article
import com.busyglide.shared.ArticleListAdapter
import com.busyglide.shared.ArticlesService
import com.busyglide.shared.support.toast
import com.busyglide.shared.support.visible
import com.rongi.github.kotlin.rxui.classic.R
import com.rongi.github.kotlin.rxui.classic.app.App
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainActivity : Activity(), MainView {

  @Inject
  lateinit var articlesService: ArticlesService

  private val list: RecyclerView by lazy {
    findViewById(R.id.list) as RecyclerView
  }

  private lateinit var presenter: Presenter

  private val updateButton: View by lazy {
    findViewById(R.id.update_button)
  }

  private val progress: View by lazy {
    findViewById(R.id.progress)
  }

  private val emptyView: View by lazy {
    findViewById(R.id.empty_view)
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

    val updateButton = updateButton
    updateButton.setOnClickListener {
      presenter.onUpdateButtonClick()
    }

    presenter = Presenter(this, articlesService)
    presenter.start()
  }

  override fun setArticles(articles: List<Article>) {
    articleListAdapter.articles = articles
  }

  override fun setUpdateButtonEnabled(enabled: Boolean) {
    updateButton.isEnabled = enabled
  }

  override fun setEmptyViewVisibility(visible: Boolean) {
    emptyView.visible = visible
  }

  override fun showOnArticleClickToast(message: String) {
    message.toast(this)
  }

}

