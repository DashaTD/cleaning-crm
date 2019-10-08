package com.ronasit.fiesta.di.modules

import com.ronasit.fiesta.ui.login.confirmation.ConfirmationFragment
import com.ronasit.fiesta.ui.login.confirmation.ConfirmationModule
import com.ronasit.fiesta.ui.login.profile.ProfileFragment
import com.ronasit.fiesta.ui.login.profile.ProfileModule
import com.ronasit.fiesta.ui.login.signin.SignInFragment
import com.ronasit.fiesta.ui.login.signin.SignInModule
import com.ronasit.fiesta.ui.login.splash.SplashFragment
import com.ronasit.fiesta.ui.login.splash.SplashModule
import com.ronasit.fiesta.ui.schedule.appointments.AppointmentsFragment
import com.ronasit.fiesta.ui.schedule.appointments.AppointmentsModule
import com.ronasit.fiesta.ui.schedule.client.ClientFragment
import com.ronasit.fiesta.ui.schedule.client.ClientModule
import com.ronasit.fiesta.ui.schedule.clients.ClientsFragment
import com.ronasit.fiesta.ui.schedule.clients.ClientsModule
import com.ronasit.fiesta.ui.schedule.clients.creation.AddClientDialogFragment
import com.ronasit.fiesta.ui.schedule.clients.creation.AddClientModule
import com.ronasit.fiesta.ui.schedule.settings.SettingsFragment
import com.ronasit.fiesta.ui.schedule.settings.SettingsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjectorsModule {

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun splashFragmentInjector(): SplashFragment

    @ContributesAndroidInjector(modules = [SignInModule::class])
    abstract fun signInFragmentInjector(): SignInFragment

    @ContributesAndroidInjector(modules = [ConfirmationModule::class])
    abstract fun confirmationFragmentInjector(): ConfirmationFragment

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun profileFragmentInjector(): ProfileFragment

    @ContributesAndroidInjector(modules = [AppointmentsModule::class])
    abstract fun appointmentsFragmentInjector(): AppointmentsFragment

    @ContributesAndroidInjector(modules = [ClientsModule::class])
    abstract fun clientsFragmentInjector(): ClientsFragment

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun settingsFragmentInjector(): SettingsFragment

    @ContributesAndroidInjector(modules = [AddClientModule::class])
    abstract fun addClientDialogFragmentInjector(): AddClientDialogFragment

    @ContributesAndroidInjector(modules = [ClientModule::class])
    abstract fun clientDialogFragmentInjector(): ClientFragment
}