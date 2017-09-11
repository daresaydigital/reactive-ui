package com.busyglide.shared.support

import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.ObservableSource
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

fun immediate(): Scheduler = Schedulers.from { it.run() }

/**
 * Sets subscribe scheduler to io
 * and observe scheduler to default Android one
 */
val <T> Observable<T>.io : Observable<T> get() {
  return this
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.notEmpty() = just(false).mergeWith(this.map { true }).distinct()!!

fun <T> Observable<T>.render(renderFunction : (T) -> Unit) {
  this.observeOn(AndroidSchedulers.mainThread())
    .subscribe { renderFunction(it) }
}

fun <T, V, R> combineLatest(registered: ObservableSource<T>, playlistReceived: ObservableSource<V>, combineFunction: (T, V) -> R): Observable<R> {
  return Observable.combineLatest(registered, playlistReceived, BiFunction<T, V, R> { a, b -> combineFunction(a, b) })
}

fun <T, R> Observable<T>.ifThenElse(predicate: (T) -> Boolean, then: (T) -> Observable<R>, elze: (T) -> Observable<R>): Observable<R> = iff(predicate, then, elze)

fun <T, R> Observable<T>.iff(predicate: (T) -> Boolean, then: (T) -> Observable<R>, elze: (T) -> Observable<R>): Observable<R> {
  return flatMap {
    if (predicate(it)) {
      then.invoke(it)
    } else {
      elze.invoke(it)
    }
  }
}