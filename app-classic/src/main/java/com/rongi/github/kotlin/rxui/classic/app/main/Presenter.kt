package com.rongi.github.kotlin.rxui.classic.app.main

import com.busyglide.shared.Article
import com.busyglide.shared.ArticlesService

interface MainView {
  fun setArticles(articles: List<Article>)
  fun setUpdateButtonEnabled(enabled: Boolean)
  fun setEmptyViewVisibility(visible: Boolean)
  fun showOnArticleClickToast(message: String)
}

class Presenter {

  private val view: MainView
  private val articlesService: ArticlesService

  private var articles = emptyList<Article>()

  constructor(view: MainView, articlesService: ArticlesService) {
    this.view = view
    this.articlesService = articlesService
  }

  fun start() {
    view.setEmptyViewVisibility(true)
  }

  fun onArticleClick(position: Int) {
    val title = articles[position].title
    view.showOnArticleClickToast(title)
  }

  fun onUpdateButtonClick() {
    articlesService.getArticles().subscribe() {
      view.setArticles(it)
      view.setEmptyViewVisibility(false)
    }
  }

}

