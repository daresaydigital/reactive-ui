package com.rongi.github.kotlin.rxui.classic.dagger;

import android.content.Context;

import com.busyglide.shared.ArticlesService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class AppModule {

  private final Context context;

  public AppModule(Context context) {
    this.context = context.getApplicationContext();
  }

  @Provides
  @Singleton
  Context provideContext() {
    return context;
  }

  @Provides
  @Singleton
  OkHttpClient provideOkHttpClient() {
    return new OkHttpClient();
  }

  @Provides
  @Singleton
  ArticlesService provideFeedService() {
    return new ArticlesService();
  }

}
