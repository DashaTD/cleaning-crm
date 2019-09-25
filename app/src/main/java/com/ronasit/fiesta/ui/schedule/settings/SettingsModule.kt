package com.ronasit.fiesta.ui.schedule.settings

import dagger.Module
import dagger.Provides
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.InjectionViewModelProvider

@Module
class SettingsModule {

    @Provides
    @ViewModelInjection
    fun provideSettingsVM(
        fragment: SettingsFragment,
        viewModelProvider: InjectionViewModelProvider<SettingsVM>
    ) = viewModelProvider.get(fragment, SettingsVM::class)
}