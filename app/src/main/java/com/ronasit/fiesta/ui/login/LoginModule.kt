package com.ronasit.fiesta.ui.login

import dagger.Module
import dagger.Provides
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.InjectionViewModelProvider

@Module
class LoginModule {

    @Provides
    @ViewModelInjection
    fun provideLoginVM(
        activity: LoginActivity,
        viewModelProvider: InjectionViewModelProvider<LoginVM>
    ) = viewModelProvider.get(activity, LoginVM::class)
}