package com.busyglide.shared.support

import android.content.Context
import android.widget.Toast

fun Any.toast(context: Context) {
  Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT).show()
}