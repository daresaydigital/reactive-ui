package com.rongi.github.kotlin.rxui.reactive.app.main

import android.widget.TextView
import com.busyglide.shared.Article
import io.reactivex.Observable

data class ArticleCounterModels(
  val countText: Observable<String>
)

fun articleCounterPresent(
  articles: Observable<List<Article>>
): ArticleCounterModels {
  val countText = articles.map { "Article count: ${it.size}" }

  return ArticleCounterModels(
    countText = countText
  )
}

fun render(articlesCounter: TextView, articleCounterModels: ArticleCounterModels) {
  articleCounterModels.countText.subscribe {
    articlesCounter.text = it
  }
}

