package com.ronasit.fiesta.ui.base

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.ronasit.fiesta.base.SingleLiveEvent
import com.ronasit.fiesta.network.FiestaApi
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel() : ViewModel() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var fiestaApi: FiestaApi

    val showProgress = SingleLiveEvent<Boolean>()

    val compositeDisposable = CompositeDisposable()

}