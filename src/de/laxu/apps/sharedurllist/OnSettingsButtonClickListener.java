package de.laxu.apps.sharedurllist;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class OnSettingsButtonClickListener implements OnClickListener{

	/**
	 * 
	 */
	private final MainActivity mainActivity;

	/**
	 * @param mainActivity
	 */
	OnSettingsButtonClickListener(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public void onClick(View v) {
		this.mainActivity.startActivity(new Intent(v.getContext(), SettingsActivity.class));
	}
	
}