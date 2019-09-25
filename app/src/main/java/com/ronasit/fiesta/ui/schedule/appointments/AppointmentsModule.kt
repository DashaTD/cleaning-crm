package com.ronasit.fiesta.ui.schedule.appointments

import dagger.Module
import dagger.Provides
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.InjectionViewModelProvider

@Module
class AppointmentsModule {

    @Provides
    @ViewModelInjection
    fun provideAppointmentsVM(
        fragment: AppointmentsFragment,
        viewModelProvider: InjectionViewModelProvider<AppointmentsVM>
    ) = viewModelProvider.get(fragment, AppointmentsVM::class)
}