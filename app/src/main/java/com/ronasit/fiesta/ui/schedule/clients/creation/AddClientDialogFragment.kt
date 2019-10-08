package com.ronasit.fiesta.ui.schedule.clients.creation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ronasit.fiesta.R
import com.ronasit.fiesta.databinding.DialogFragmentAddClientBinding
import com.ronasit.fiesta.di.DaggerBottomSheetDialogFragment
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.network.ClientModel
import com.ronasit.fiesta.ui.DialogProgressView
import javax.inject.Inject

class AddClientDialogFragment(private val clientModel: ClientModel) :
    DaggerBottomSheetDialogFragment() {

    @Inject
    @ViewModelInjection
    lateinit var addClientVM: ViewModelInjectionField<AddClientVM>

    private lateinit var dialog: BottomSheetDialog

    private val dialogProgressView: DialogProgressView by lazy {
        DialogProgressView(activity!!, dialog)
    }

    companion object {

        fun newInstance(clientModel: ClientModel): AddClientDialogFragment {
            return AddClientDialogFragment(clientModel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        val binding = DialogFragmentAddClientBinding.inflate(inflater, container, false)

        binding.viewModel = addClientVM.get()

        addClientVM.get().clientModel = clientModel

        with(addClientVM.get()) {
            onClose.observe(this@AddClientDialogFragment, Observer { dismiss() })
            onInternetConnectionError.observe(
                this@AddClientDialogFragment,
                Observer { showInternetConnectionErrorToast() })
            showProgress.observe(
                this@AddClientDialogFragment,
                Observer {
                    if (it) dialogProgressView.show() else dialogProgressView.hide()
                })
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.DialogTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialogInterface ->
            val d = dialogInterface as BottomSheetDialog

            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout

            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.setBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
                }
            })
            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    private fun showInternetConnectionErrorToast() {
        val toast = Toast.makeText(
            activity,
            resources.getText(R.string.toast_internet_connection_error),
            Toast.LENGTH_SHORT
        )
        toast.show()
    }
}