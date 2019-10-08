package com.ronasit.fiesta.ui.schedule

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ronasit.fiesta.R
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.ui.base.BaseActivity
import com.ronasit.fiesta.ui.schedule.appointments.AppointmentsFragment
import com.ronasit.fiesta.ui.schedule.clients.ClientsFragment
import com.ronasit.fiesta.ui.schedule.settings.SettingsFragment
import javax.inject.Inject


class ScheduleActivity : BaseActivity() {

    lateinit var navigation: BottomNavigationView

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<ScheduleVM>

    override fun layoutRes() = R.layout.activity_schedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigation =
            findViewById<View>(R.id.bottom_navigation) as BottomNavigationView

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        switchFragment(AppointmentsFragment())
        navigation.itemIconTintList = null

    }

    private val mOnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val fragment: Fragment
                when (item.itemId) {
                    R.id.navigation_appointments -> {
                        fragment = AppointmentsFragment()
                    }
                    R.id.navigation_clients -> {
                        fragment = ClientsFragment()
                    }
                    R.id.navigation_settings -> {
                        fragment = SettingsFragment()
                    }
                    else -> return false
                }
                switchFragment(fragment)
                return true

            }
        }

    fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}