package ru.overtired.yamblz2017.main_activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import ru.overtired.yamblz2017.R;
import ru.overtired.yamblz2017.service.WeatherRequestJob;

/**
 * Created by overtired on 09.07.17.
 */

public class PreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String TAG = "PreferenceFragment";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String autoupdateKey = getString(R.string.pref_key_autoupdate);
        String intervalKey = getString(R.string.pref_key_autoupdate_interval);
        String defaultInterval = getString(R.string.interval_default);

        if (key.equals(autoupdateKey) || key.equals(intervalKey)) {
            if (sharedPreferences.getBoolean(autoupdateKey, true)) {
                long intervalValue = Long.parseLong(sharedPreferences
                        .getString(intervalKey, defaultInterval));
                WeatherRequestJob.scheduleJob(intervalValue);
            } else {
                WeatherRequestJob.unscheduleJob();
            }
        }
    }
}
