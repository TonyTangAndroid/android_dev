package info.juanmendez.android.intentservice.ui;

import android.app.AlarmManager;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.model.MagazinesPref;
import info.juanmendez.android.intentservice.service.alarm.WakeReceiver;

/** Created by Juan on 8/31/2015. */
public class SettingsFragment extends PreferenceFragment
    implements SharedPreferences.OnSharedPreferenceChangeListener {
  SharedPreferences sharedPreferences;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.pref_screen);
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
  }

  private Application getApplication() {
    return getActivity().getApplication();
  }

  @Override
  public void onResume() {
    super.onResume();

    if (sharedPreferences != null) sharedPreferences.registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onPause() {
    super.onPause();

    if (sharedPreferences != null)
      sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    if (key.equals(MagazinesPref.MAGAZINE_UPDATES)
        || key.equals(MagazinesPref.MAGAZINE_UPDATES_INTERVALS)) {
      Boolean updates = sharedPreferences.getBoolean(MagazinesPref.MAGAZINE_UPDATES, false);

      if (updates) {
        WakeReceiver.startAlarm(this.getApplication(), getInterval());
      } else {
        WakeReceiver.cancelAlarm(this.getApplication());
      }
    }
  }

  private Long getInterval() {
    String key =
        sharedPreferences.getString(MagazinesPref.MAGAZINE_UPDATES_INTERVALS, "FIFTEEN_MINUTES");
    Long interval = 0l;

    switch (key) {
      case "ONE_MINUTE":
        interval = 60 * 1000l;
        break;

      case "FIFTEEN_MINUTES":
        interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        break;

      case "THIRTY_MINUTES":
        interval = AlarmManager.INTERVAL_HALF_HOUR;
        break;
      case "HOURLY":
        interval = AlarmManager.INTERVAL_HOUR;
        break;

      case "HALF_DAY":
        interval = AlarmManager.INTERVAL_HALF_DAY;
        break;

      case "DAILY":
        interval = AlarmManager.INTERVAL_DAY;
        break;
    }

    return interval;
  }
}
