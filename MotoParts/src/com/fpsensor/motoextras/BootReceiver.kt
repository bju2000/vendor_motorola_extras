package com.fpsensor.motoextras

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.preference.PreferenceManager
import com.fpsensor.motoextras.battery.BatteryFragment
import com.fpsensor.motoextras.interfaces.Battery
import com.fpsensor.motoextras.interfaces.Display
import com.fpsensor.motoextras.interfaces.Flashlight
import com.fpsensor.motoextras.interfaces.Swap
import com.fpsensor.motoextras.swap.SwapFragment

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val mSharedPreferences = p0?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        if (p1 != null && mSharedPreferences != null) {
            if (p1.action == Intent.ACTION_BOOT_COMPLETED) {
                System.loadLibrary("motoparts_jni")
                // Battery
                Battery.chargeSysfs = if (mSharedPreferences
                    .getBoolean(BatteryFragment.PREF_CHARGE, true)
                ) 0 else 1
                Battery.setFastCharge(
                    if (mSharedPreferences
                        .getBoolean(BatteryFragment.PREF_FASTCHARGE, true)
                    ) 0 else 1
                )
                // ZRAM
                Swap.setSize(mSharedPreferences.getInt(SwapFragment.PREF_SWAP_SIZE, 50))
                Swap.setSwapOn(mSharedPreferences.getBoolean(SwapFragment.PREF_SWAP_ENABLE, false))

                // Display
                Display.DT2W = mSharedPreferences.getBoolean(DeviceSettings.PREF_DOUBLE_TAP, true)
                Display.GloveMode = mSharedPreferences.getBoolean(DeviceSettings.PREF_GLOVE_MODE, false)
                Log.i("motoParts", "Applied settings")
            }
        }
    }
}
