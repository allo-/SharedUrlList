package de.laxu.apps.commonurllist;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentActivity;

public class SettingsActivity extends FragmentActivity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		SettingsFragment settingsFragment = new SettingsFragment();
		getFragmentManager().beginTransaction().replace(android.R.id.content, settingsFragment).commit();
	}

	public static class SettingsFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.preferences);
		}
	}
}
