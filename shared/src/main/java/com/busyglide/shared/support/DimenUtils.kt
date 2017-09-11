package com.busyglide.shared.support

import android.content.Context

fun Int.toPx(context: Context) = (this * context.resources.displayMetrics.density).round()