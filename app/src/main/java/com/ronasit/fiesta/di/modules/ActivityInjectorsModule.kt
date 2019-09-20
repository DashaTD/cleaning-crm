package com.ronasit.fiesta.di.modules

import com.ronasit.fiesta.ui.login.LoginActivity
import com.ronasit.fiesta.ui.login.LoginModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectorsModule {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun mainActivityInjector(): LoginActivity

}