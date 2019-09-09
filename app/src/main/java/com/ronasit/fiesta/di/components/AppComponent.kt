package com.ronasit.fiesta.di.components

import com.ronasit.fiesta.App
import com.ronasit.fiesta.di.modules.ActivityInjectorsModule
import com.ronasit.fiesta.di.modules.FragmentInjectorsModule
import com.ronasit.fiesta.di.modules.AppModule
import com.ronasit.fiesta.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityInjectorsModule::class,
        FragmentInjectorsModule::class,
        NetworkModule::class,
        AppModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

}