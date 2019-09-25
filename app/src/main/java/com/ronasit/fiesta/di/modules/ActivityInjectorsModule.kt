package com.ronasit.fiesta.di.modules

import com.ronasit.fiesta.ui.schedule.ScheduleActivity
import com.ronasit.fiesta.ui.schedule.ScheduleModule
import com.ronasit.fiesta.ui.login.LoginActivity
import com.ronasit.fiesta.ui.login.LoginModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectorsModule {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun mainActivityInjector(): LoginActivity

    @ContributesAndroidInjector(modules = [com.ronasit.fiesta.ui.schedule.ScheduleModule::class])
    abstract fun scheduleActivityInjector(): com.ronasit.fiesta.ui.schedule.ScheduleActivity
}