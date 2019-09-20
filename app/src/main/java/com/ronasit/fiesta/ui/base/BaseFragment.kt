package com.ronasit.fiesta.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.ronasit.fiesta.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @LayoutRes
    abstract fun layoutRes(): Int

    @Inject
    lateinit var applicationContext: Context

    private val progressBar by lazy {
        ProgressBar(activity!!, null, android.R.attr.progressBarStyle).apply {
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            layoutParams = params
        }
    }

    private val progressView by lazy {
        RelativeLayout(activity!!).apply {
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            layoutParams = params
            setBackgroundColor(ContextCompat.getColor(context, R.color.graySemiTransparent_20))
            addView(progressBar)
            activity!!.addContentView(this, params)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes(), container, false)
    }

    fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressView.visibility = View.GONE
    }
}