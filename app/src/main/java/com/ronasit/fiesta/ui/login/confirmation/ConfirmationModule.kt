package com.ronasit.fiesta.ui.login.confirmation

import dagger.Module
import dagger.Provides
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.InjectionViewModelProvider

@Module
class ConfirmationModule {

    @Provides
    @ViewModelInjection
    fun provideConfirmationVM(
        fragment: ConfirmationFragment,
        viewModelProvider: InjectionViewModelProvider<ConfirmationVM>
    ) = viewModelProvider.get(fragment, ConfirmationVM::class)
}