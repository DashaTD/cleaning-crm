package com.ronasit.fiesta.ui.schedule.appointments.newAppointment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ronasit.fiesta.databinding.FragmentNewAppointmentBinding
import com.ronasit.fiesta.network.requests.AddAppointmentRequest
import com.ronasit.fiesta.ui.schedule.appointments.AppointmentsVM
import java.text.SimpleDateFormat
import java.util.*


class NewAppointmentBottomSheet : BottomSheetDialogFragment() {

    var appointment = AddAppointmentRequest()
    lateinit var viewModel: AppointmentsVM
    //ViewModelInjectionField<AppointmentsVM>
    lateinit var binding: FragmentNewAppointmentBinding

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewAppointmentBinding.inflate(inflater, container, false)
        binding.bottomSheet = appointment
//        binding.appoinmentsVM = viewModel.get()
        binding.cancelButton.setOnClickListener {
            this.dismiss()
        }
        binding.scheduleButton.setOnClickListener {
            viewModel.addAppointment(appointment)
//                Toast.makeText(this.context, "well done", Toast.LENGTH_SHORT).show()
//                this.dismiss()
//            }
//            else {Toast.makeText(this.context, "incorrect data input", Toast.LENGTH_SHORT).show()}
        }
        binding.startTime.setOnClickListener {
            setDatePicker(it as TextView)
        }
        binding.durationTime.setOnClickListener {
            durationPicker(it as TextView)
        }
        binding.priceText.setOnClickListener {
            showCurrencyPicker()
        }
        binding.startTime.text = SimpleDateFormat("MMM dd KK:mm a", Locale.US).format(Date().time)

        viewModel.onFinished.observe(this, androidx.lifecycle.Observer {
            if(it){
                dismiss()
            }
            else {
                Toast.makeText(context, "incorrect", Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    companion object newInstance {
        fun newInstance(): NewAppointmentBottomSheet {
            return NewAppointmentBottomSheet()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog

            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun setDatePicker(dateEditText: TextView) {

        val myCalendar = Calendar.getInstance()

        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                myCalendar.set(Calendar.HOUR_OF_DAY, 12)
                myCalendar.set(Calendar.MINUTE, 0)
                timePicker(myCalendar, dateEditText)
            }

        val datePicker = DatePickerDialog(
            this.context!!, datePickerOnDataSetListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.minDate = Date().time
        datePicker.show()
    }

    fun timePicker(myCalendar: Calendar, text: TextView): Calendar {

        val timePickerOnTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
            myCalendar.set(Calendar.HOUR_OF_DAY, i)
            myCalendar.set(Calendar.MINUTE, i2)
            updateLabel(myCalendar, text)
        }
        val timePicker = TimePickerDialog(
            this.context!!, timePickerOnTimeSetListener, myCalendar
                .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false
        )
        timePicker.show()
        return myCalendar
    }

    fun durationPicker(text: TextView) {
        val myCalendar = Calendar.getInstance()
        val timePickerOnTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
            myCalendar.set(Calendar.HOUR_OF_DAY, i)
            myCalendar.set(Calendar.MINUTE, i2)
            updateDuration(myCalendar, text)
        }
        val timePicker = TimePickerDialog(
            this.context!!,
            com.ronasit.fiesta.R.style.CustomDatePickerDialog,
            timePickerOnTimeSetListener,
            myCalendar
                .get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE),
            true
        )
        timePicker.show()
    }

    private fun updateLabel(myCalendar: Calendar, dateTimeTextView: TextView) {
        val serverFormat: String = "yyyy-MM-dd HH:mm:ss"
        val serverTime =
            SimpleDateFormat(serverFormat, Locale.US)
                .format(myCalendar.time)
        val myFormat: String = "MMM dd KK:mm a"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateTimeTextView.setText(sdf.format(myCalendar.time))

        appointment.startedAt = serverTime
    }

    private fun updateDuration(myCalendar: Calendar, dateTimeTExtView: TextView) {
        val myFormat: String = "HH:mm"
        val outputFormat:String = "HH 'hours' mm 'minutes'"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val outputSdf = SimpleDateFormat(outputFormat, Locale.US)
        val outputString = outputSdf.format(myCalendar.time)
        val tempString = sdf.format(myCalendar.time)
        val arrayOfTime = tempString.split(":")
        val hours = arrayOfTime[0]
        val minutes = arrayOfTime[1]
        val totalMinutes = Integer.parseInt(hours) * 60 + Integer.parseInt(minutes)
        dateTimeTExtView.setText("$outputString")

        appointment.duration = totalMinutes
    }

    fun showCurrencyPicker() {
        var money: String = ""
        val alert = AlertDialog.Builder(this.context)
        alert.setTitle("Price")
        val input = EditText(this.context)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.setRawInputType(Configuration.KEYBOARD_12KEY)
        alert.setView(input)
        alert.setPositiveButton("Ok",
            DialogInterface.OnClickListener { dialog, whichButton ->
                money = input.text.toString()
                binding.priceText.text = "$$money"
                appointment.price = money
            })
        alert.setNegativeButton("cancel",
            DialogInterface.OnClickListener { dialog, whichButton ->
                money = "0"
                appointment.price = money
            })
        alert.show()


    }

}