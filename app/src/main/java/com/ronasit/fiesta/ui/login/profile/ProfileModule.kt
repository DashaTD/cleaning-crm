package com.ronasit.fiesta.ui.login.profile

import dagger.Module
import dagger.Provides
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.InjectionViewModelProvider

@Module
class ProfileModule {

    @Provides
    @ViewModelInjection
    fun provideProfileVM(
        fragment: ProfileFragment,
        viewModelProvider: InjectionViewModelProvider<ProfileVM>
    ) = viewModelProvider.get(fragment, ProfileVM::class)
}