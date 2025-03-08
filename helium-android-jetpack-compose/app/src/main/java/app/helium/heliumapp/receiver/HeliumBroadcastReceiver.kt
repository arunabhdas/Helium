package app.helium.heliumapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import app.helium.heliumapp.services.HeliumService


class HeliumBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val startServiceIntent = Intent(context, HeliumService::class.java)
        context.startService(startServiceIntent)
    }
}