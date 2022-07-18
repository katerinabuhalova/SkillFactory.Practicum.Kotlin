package com.awesomecompany.mykinopoisk.view.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.awesomecompany.mykinopoisk.R

class ConnectionChecker : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        if (intent == null) return
        when (intent.action) {
            Intent.ACTION_BATTERY_LOW -> if (context != null) {
                Toast.makeText(context, context.getString(R.string.ConnectionCheker_Battery_low), Toast.LENGTH_SHORT).show()
            }
            Intent.ACTION_POWER_CONNECTED -> if (context != null) {
                Toast.makeText(context, context.getString(R.string.ConnectionChecker_Power_Connected), Toast.LENGTH_SHORT).show()
            }
        }
    }
}