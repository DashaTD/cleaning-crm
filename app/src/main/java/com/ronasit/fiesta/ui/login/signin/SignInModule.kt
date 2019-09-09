package com.ronasit.fiesta.ui.login.signin

import dagger.Module
import dagger.Provides
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.InjectionViewModelProvider

@Module
class SignInModule {

    @Provides
    @ViewModelInjection
    fun provideSignInVM(
        fragment: SignInFragment,
        viewModelProvider: InjectionViewModelProvider<SignInVM>
    ) = viewModelProvider.get(fragment, SignInVM::class)
}