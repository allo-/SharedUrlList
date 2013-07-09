package de.laxu.apps.commonurllist;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

class  OnSettingsMenuItemClickListener implements OnMenuItemClickListener{
	/**
	 * 
	 */
	private final MainActivity mainActivity;
	private Activity activity;
	public OnSettingsMenuItemClickListener(MainActivity mainActivity, Activity activity){
		this.mainActivity = mainActivity;
		this.activity = activity;
	}
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		this.mainActivity.startActivity(new Intent(activity, SettingsActivity.class));
		return true;
	}
}