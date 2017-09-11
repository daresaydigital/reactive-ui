package com.rongi.github.kotlin.rxui.reactive.app.main

import com.busyglide.shared.Article
import com.busyglide.shared.ArticlesService
import com.rongi.github.kotlin.rxui.reactive.app.support.mapWithDrop
import com.rongi.github.kotlin.rxui.reactive.app.support.withLatestFrom
import io.reactivex.Observable
import io.reactivex.Observable.just

data class Models(
  val articles: Observable<List<Article>>,
  val emptyViewVisibility: Observable<Boolean>,
  val showToastSignal: Observable<String>,
  val articleCounterModels: ArticleCounterModels
)

fun present(
  updateButtonClicks: Observable<Unit>,
  clickedArticlePosition: Observable<Int>,
  articlesService: ArticlesService
): Models {
  val articles = updateButtonClicks
    .mapWithDrop(articlesService.getArticles())
    .publish().autoConnect()

  val emptyViewVisibility = just(true)
    .concatWith(articles.map { it.isEmpty() })

  val showToastSignal = clickedArticlePosition
    .withLatestFrom(articles) { position, articleList ->
      articleList[position]
    }
    .map { it.title }

  val articleCounterStates = articleCounterPresent(articles)

  return Models(
    articles = articles,
    emptyViewVisibility = emptyViewVisibility,
    showToastSignal = showToastSignal,
    articleCounterModels = articleCounterStates
  )
}

