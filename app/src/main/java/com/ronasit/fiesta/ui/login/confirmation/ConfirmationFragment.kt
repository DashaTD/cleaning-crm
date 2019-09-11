package com.ronasit.fiesta.ui.login.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ronasit.fiesta.R
import com.ronasit.fiesta.databinding.FragmentConfirmationBinding
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_confirmation.*
import javax.inject.Inject

class ConfirmationFragment : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_confirmation

    companion object {
        fun newInstance(): ConfirmationFragment {
            return ConfirmationFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<ConfirmationVM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentConfirmationBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar!!.show()
        binding.viewModel = viewModel.get()
        binding.lifecycleOwner = this

        return binding.root
    }

}