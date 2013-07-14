package de.laxu.apps.sharedurllist;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

class OnRefreshMenuItemClickListener implements OnMenuItemClickListener{
	private final MainActivity mainActivity;
	public OnRefreshMenuItemClickListener(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		mainActivity.updateLists();
		return true;
	}
	
}