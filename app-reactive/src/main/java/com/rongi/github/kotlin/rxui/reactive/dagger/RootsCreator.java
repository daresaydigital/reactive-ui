package com.rongi.github.kotlin.rxui.reactive.dagger;

import android.content.Context;

public class RootsCreator {

  public static Roots create(Context context) {
    return DaggerRoots.builder().appModule(new AppModule(context)).build();
  }

}
