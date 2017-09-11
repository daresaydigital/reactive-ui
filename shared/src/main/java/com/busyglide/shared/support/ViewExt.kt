package com.busyglide.shared.support

import android.view.View

var View.visible: Boolean
  get() {
    return this.visibility == View.VISIBLE
  }
  set(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
  }

