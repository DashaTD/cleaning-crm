package com.ronasit.fiesta.ui.schedule.client

import com.ronasit.fiesta.di.InjectionViewModelProvider
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class ClientModule {

    @Provides
    @ViewModelInjection
    fun provideClientVM(
        fragment: ClientFragment,
        viewModelProvider: InjectionViewModelProvider<ClientVM>
    ) = viewModelProvider.get(fragment, ClientVM::class)
}