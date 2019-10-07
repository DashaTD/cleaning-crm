package com.ronasit.fiesta.di.modules

import android.app.Application
import android.content.Context
import com.ronasit.fiesta.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: App): Application = app

    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(app: App) = app.getSharedPreferences("app",
        Context.MODE_PRIVATE
    )

}