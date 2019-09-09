package com.ronasit.fiesta.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel () : ViewModel() {
    val compositeDisposable = CompositeDisposable()

}