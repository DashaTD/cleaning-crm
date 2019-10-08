package com.ronasit.fiesta.ui.schedule.clients.creation

import com.ronasit.fiesta.di.InjectionViewModelProvider
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class AddClientModule {

    @Provides
    @ViewModelInjection
    fun provideAddClientVM(
        fragment: AddClientDialogFragment,
        viewModelProvider: InjectionViewModelProvider<AddClientVM>
    ) = viewModelProvider.get(fragment, AddClientVM::class)
}