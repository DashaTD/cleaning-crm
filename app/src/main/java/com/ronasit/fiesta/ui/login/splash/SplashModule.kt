package com.ronasit.fiesta.ui.login.splash

import dagger.Module
import dagger.Provides
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.InjectionViewModelProvider

@Module
class SplashModule {

    @Provides
    @ViewModelInjection
    fun provideLoadingVM(
        fragment: SplashFragment,
        viewModelProvider: InjectionViewModelProvider<SplashVM>
    ) = viewModelProvider.get(fragment, SplashVM::class)
}