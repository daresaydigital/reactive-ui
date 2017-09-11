package com.rongi.github.kotlin.rxui.reactive.dagger;

import com.rongi.github.kotlin.rxui.reactive.app.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface Roots {

  MainActivity inject(MainActivity object);

}
