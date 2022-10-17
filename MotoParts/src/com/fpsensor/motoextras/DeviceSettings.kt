/*
 * Copyright (C) 2022 Eureka Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package com.fpsensor.motoextras

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.fpsensor.motoextras.battery.BatteryActivity
import com.fpsensor.motoextras.flashlight.FlashLightActivity
import com.fpsensor.motoextras.fps.FPSInfoService
import com.fpsensor.motoextras.interfaces.Display.DT2W
import com.fpsensor.motoextras.interfaces.Display.GloveMode
import com.fpsensor.motoextras.speaker.ClearSpeakerActivity

class DeviceSettings : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    private lateinit var mPrefs: SharedPreferences
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        System.loadLibrary("samsungparts_jni")
        setPreferencesFromResource(R.xml.preferences_samsung_parts, rootKey)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val mClearSpeakerPref = findPreference<Preference>(PREF_CLEAR_SPEAKER)!!
        mClearSpeakerPref.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                val intent = Intent(requireActivity().applicationContext, ClearSpeakerActivity::class.java)
                startActivity(intent)
                true
            }
        val mFpsInfo = findPreference<SwitchPreference>(PREF_KEY_FPS_INFO)!!
        mFpsInfo.isChecked = mPrefs.getBoolean(PREF_KEY_FPS_INFO, false)
        mFpsInfo.onPreferenceChangeListener = this
        val mDT2W = findPreference<SwitchPreference>(PREF_DOUBLE_TAP)!!
        mDT2W.onPreferenceChangeListener = this
        mDT2W.isEnabled = !(Build.PRODUCT.contains("a10") || Build.PRODUCT.contains("a20e"))
        mDT2W.isChecked = mPrefs.getBoolean(PREF_DOUBLE_TAP, false)
        val mGloveMode = findPreference<SwitchPreference>(PREF_GLOVE_MODE)!!
        mGloveMode.onPreferenceChangeListener = this
        mGloveMode.isEnabled = !Build.PRODUCT.contains("a10")
        mGloveMode.isChecked = mPrefs.getBoolean(PREF_GLOVE_MODE, false)
        val mFastCharge = findPreference<Preference>(PREF_BATTERY)!!
        mFastCharge.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                val intent = Intent(requireActivity().applicationContext, BatteryActivity::class.java)
                startActivity(intent)
                true
            }
    }

    override fun onPreferenceChange(preference: Preference, value: Any): Boolean {
        when (preference.key) {
            PREF_KEY_FPS_INFO -> {
                val mEnabled = value as Boolean
                val mFPSService = Intent(this.context, FPSInfoService::class.java)
                if (mEnabled) {
                    requireContext().startService(mFPSService)
                } else {
                    requireContext().stopService(mFPSService)
                }
                mPrefs.edit().putBoolean(PREF_KEY_FPS_INFO, mEnabled).apply()
            }
            PREF_DOUBLE_TAP -> {
                DT2W = value as Boolean
                mPrefs.edit().putBoolean(PREF_DOUBLE_TAP, value).apply()
            }
            PREF_GLOVE_MODE -> {
                GloveMode = value as Boolean
                mPrefs.edit().putBoolean(PREF_GLOVE_MODE, value).apply()
            }
            else -> {
                return false
            }
        }
        return true
    }

    companion object {
        const val PREF_KEY_FPS_INFO = "fps_info"
        private const val PREF_CLEAR_SPEAKER = "clear_speaker_settings"
        private const val PREF_FLASHLIGHT = "flashlight_settings"
        const val PREF_DOUBLE_TAP = "dt2w_settings"
        const val PREF_GLOVE_MODE = "glove_mode_settings"
        const val PREF_BATTERY = "battery_settings"
    }
}
