package de.laxu.apps.sharedurllist.listeners;

import de.laxu.apps.sharedurllist.MainActivity;
import de.laxu.apps.sharedurllist.SettingsActivity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class OnSettingsButtonClickListener implements OnClickListener{
	private final MainActivity mainActivity;
	public OnSettingsButtonClickListener(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}
	
	@Override
	public void onClick(View v) {
		this.mainActivity.startActivity(new Intent(v.getContext(), SettingsActivity.class));
	}
	
}