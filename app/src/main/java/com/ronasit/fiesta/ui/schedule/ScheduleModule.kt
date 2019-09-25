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
        activity: com.ronasit.fiesta.ui.schedule.ScheduleActivity,
        viewModelProvider: InjectionViewModelProvider<com.ronasit.fiesta.ui.schedule.ScheduleVM>
    ) = viewModelProvider.get(activity, com.ronasit.fiesta.ui.schedule.ScheduleVM::class)
}