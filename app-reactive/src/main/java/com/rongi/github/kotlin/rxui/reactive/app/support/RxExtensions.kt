package com.rongi.github.kotlin.rxui.reactive.app.support

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.ObservableSource

fun Observable<Boolean>.invert(): Observable<Boolean> {
  return this.map { !it }
}

fun <T> Observable<JobStatus<T>>.getResults(): Observable<T> {
  @Suppress("UNCHECKED_CAST")
  return this.ofType(JobStatus.Done::class.java).map {
    (it as JobStatus.Done<T>).result
  }
}

fun <T, R> Observable<T>.mapWithDrop(source: Observable<R>): Observable<R> {
  return this.toFlowable(BackpressureStrategy.DROP).flatMap({ source.toFlowable(BackpressureStrategy.MISSING) }, 1).toObservable()
}

fun <T> Observable<T>.toJobStatus(): Observable<JobStatus<T>> {
  return Observable.concat(
    Observable.just<JobStatus<T>>(JobStatus.Start<T>()),
    this.map { JobStatus.Done(it) }
  )
}

fun <T, U, R> Observable<T>.withLatestFrom(observable: ObservableSource<U>, combine: (T, U) -> R): Observable<R> {
  return this.withLatestFrom(observable, io.reactivex.functions.BiFunction { t1, t2 -> combine(t1, t2) })
}

fun <T, U> Observable<T>.withLatestFrom(observable: ObservableSource<U>): Observable<U> {
  return this.withLatestFrom(observable, io.reactivex.functions.BiFunction { t1, t2 -> t2 })
}

fun <T, U, V> Observable<T>.withLatestFrom(observable1: ObservableSource<U>, observable2: ObservableSource<V>): Observable<Pair<U, V>> {
  return this.withLatestFrom(observable1, observable2, io.reactivex.functions.Function3 { t1, t2, t3 -> Pair(t2, t3) })
}

