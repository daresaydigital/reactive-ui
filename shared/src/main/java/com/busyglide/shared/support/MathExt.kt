package com.busyglide.shared.support;

/**
 * Full circle constant, PI * 2
 */
const val TAU = Math.PI * 2.0

fun Float.abs(): Float {
  return Math.abs(this)
}

fun Float.pwr2(): Double {
  return this.toDouble() * this
}

fun Double.round(): Long {
  return Math.round(this)
}

fun Float.floor(): Int {
  return Math.floor(this.toDouble()).toInt()
}

fun Float.round(): Int {
  return Math.round(this)
}

fun Long.toMillis(): Long {
  return this * 1000
}

fun Float.capTo(arg: Float): Float {
  return Math.min(arg, this)
}