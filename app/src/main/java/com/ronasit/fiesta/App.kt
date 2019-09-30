package com.ronasit.fiesta

import android.app.Activity
import android.app.Application
import com.ronasit.fiesta.di.components.DaggerAppComponent
import com.ronasit.fiesta.service.db.FiestaService
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    //TODO REMINDER: register this class in AndroidManifest.xml

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .name("fiesta.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)

        // TODO REMINDER: delete the following three lines if there is no need to launch app with empty db
//        val fiestaService = FiestaService()
//        fiestaService.deleteAllData()
//        fiestaService.close()

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}