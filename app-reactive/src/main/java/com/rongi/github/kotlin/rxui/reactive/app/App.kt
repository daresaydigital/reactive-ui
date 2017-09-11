package com.rongi.github.kotlin.rxui.reactive.app

import android.app.Application
import com.rongi.github.kotlin.rxui.reactive.dagger.Roots
import com.rongi.github.kotlin.rxui.reactive.dagger.RootsCreator
import io.paperdb.Paper
import timber.log.Timber
import timber.log.Timber.DebugTree

class App: Application() {

  override fun onCreate() {
    super.onCreate()

    Timber.plant(DebugTree())

    Paper.init(this)
    roots = RootsCreator.create(this)
  }

  companion object {
    lateinit var roots: Roots
  }

}