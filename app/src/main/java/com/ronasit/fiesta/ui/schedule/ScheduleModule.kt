package com.ronasit.fiesta.ui.schedule

import dagger.Module
import dagger.Provides
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.InjectionViewModelProvider

@Module
class ScheduleModule {

    @Provides
    @ViewModelInjection
    fun provideScheduleVM(
        fragment: ScheduleFragment,
        viewModelProvider: InjectionViewModelProvider<ScheduleVM>
    ) = viewModelProvider.get(fragment, ScheduleVM::class)
}