package com.ronasit.fiesta.ui.schedule.clients

import dagger.Module
import dagger.Provides
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.InjectionViewModelProvider

@Module
class ClientsModule {

    @Provides
    @ViewModelInjection
    fun provideClientsVM(
        fragment: ClientsFragment,
        viewModelProvider: InjectionViewModelProvider<ClientsVM>
    ) = viewModelProvider.get(fragment, ClientsVM::class)
}