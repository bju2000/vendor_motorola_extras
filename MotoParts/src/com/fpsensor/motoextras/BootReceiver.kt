package com.fpsensor.motoextras

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.preference.PreferenceManager
import com.eurekateam.samsungextras.battery.BatteryFragment
import com.eurekateam.samsungextras.dolby.DolbyCore
import com.eurekateam.samsungextras.dolby.DolbyFragment
import com.eurekateam.samsungextras.flashlight.FlashLightFragment
import com.eurekateam.samsungextras.interfaces.Battery
import com.eurekateam.samsungextras.interfaces.Display
import com.eurekateam.samsungextras.interfaces.Flashlight
import com.eurekateam.samsungextras.interfaces.Swap
import com.eurekateam.samsungextras.swap.SwapFragment

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val mSharedPreferences = p0?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        if (p1 != null && mSharedPreferences != null) {
            if (p1.action == Intent.ACTION_BOOT_COMPLETED) {
                System.loadLibrary("samsungparts_jni")
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
                val mSwap = Swap()
                if (mSharedPreferences.getBoolean(SwapFragment.PREF_SWAP_ENABLE, false)) {
                    mSwap.setSwapOn(false)
                } else {
                    mSwap.setSwapOff()
                }

                // Display
                Display.DT2W = mSharedPreferences.getBoolean(DeviceSettings.PREF_DOUBLE_TAP, true)
                Display.GloveMode = mSharedPreferences.getBoolean(DeviceSettings.PREF_GLOVE_MODE, false)
                Log.i("SamsungParts", "Applied settings")
            }
        }
    }
}