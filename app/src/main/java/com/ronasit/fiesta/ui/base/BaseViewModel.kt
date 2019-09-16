package com.ronasit.fiesta.ui.base

import androidx.lifecycle.ViewModel
import com.ronasit.fiesta.network.FiestaApi
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel () : ViewModel() {
    @Inject
    lateinit var fiestaApi: FiestaApi

    val compositeDisposable = CompositeDisposable()

}