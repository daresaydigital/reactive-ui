package com.rongi.github.kotlin.rxui.reactive.app.support

sealed class JobStatus<T> {

  class Start<T>: JobStatus<T>()

  class Done<T>(val result: T): JobStatus<T>()

}