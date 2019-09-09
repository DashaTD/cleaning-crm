package com.ronasit.fiesta.di.modules

import com.ronasit.fiesta.ui.login.confirmation.ConfirmationFragment
import com.ronasit.fiesta.ui.login.confirmation.ConfirmationModule
import com.ronasit.fiesta.ui.login.signin.SignInFragment
import com.ronasit.fiesta.ui.login.signin.SignInModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjectorsModule {

    @ContributesAndroidInjector(modules = [SignInModule::class])
    abstract fun signInFragmentInjector(): SignInFragment

    @ContributesAndroidInjector(modules = [ConfirmationModule::class])
    abstract fun confirmationFragmentInjector(): ConfirmationFragment
}