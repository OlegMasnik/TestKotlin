package ua.home.kubic.testkotlin

import android.os.Bundle
import android.preference.PreferenceActivity

/**
 * Created by Kubic on 23.02.2016.
 */
class SettingActivity : PreferenceActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)

    }

}