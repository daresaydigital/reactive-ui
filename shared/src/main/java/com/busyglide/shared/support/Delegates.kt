package com.busyglide.shared.support

import kotlin.reflect.KProperty

class LazyVar<in R, T>(private val initializer: () -> T) {

  private var value: T? = null

  private var initialized = false

  operator fun getValue(thisRef: R, property: KProperty<*>): T? {
    if (!initialized) {
      value  = initializer()
      initialized = true
    }
    return value
  }

  operator fun setValue(thisRef: R, property: KProperty<*>, value: T?) {
    this.value = value
  }
}

fun <T> lazyVar(initializer: () -> T): LazyVar<Any?, T> = LazyVar(initializer)
