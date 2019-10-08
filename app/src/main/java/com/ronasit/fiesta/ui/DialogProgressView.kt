package com.ronasit.fiesta.ui

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.ronasit.fiesta.R
import kotlinx.android.synthetic.main.dialog_fragment_add_client.*

class DialogProgressView(private val activity: Activity, private val dialog: Dialog) {

    private val progressBar by lazy {
        ProgressBar(activity, null, android.R.attr.progressBarStyle).apply {
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            layoutParams = params
        }
    }

    private val progressView by lazy {
        LinearLayout(activity).apply {
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            layoutParams = params
            setBackgroundColor(ContextCompat.getColor(context, R.color.graySemiTransparent_20))
            addView(progressBar)
            dialog.addContentView(this, params)
        }
    }

    fun show() {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        dialog.window!!.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        hideKeyboard()
        progressView.visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(dialog.view.windowToken,0)
    }

    fun hide() {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        progressView.visibility = View.GONE
    }

}