package com.busyglide.shared

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit.SECONDS

class ArticlesService {

  fun getArticles(): Observable<List<Article>> {
    return listOf(
      Article("Kotlin 1.1.4 is out"),
      Article("Kotlin 1.2 M2 is out"),
      Article("Kotlin Workshop Material for you to use"),
      Article("KotlinConf – Speaker List Announced"),
      Article("Kotlin Future Features Survey Results")
    ).asObservable()
      .delay(1, SECONDS)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

}

fun <T> T.asObservable(): Observable<T> {
  return Observable.just(this)
}
