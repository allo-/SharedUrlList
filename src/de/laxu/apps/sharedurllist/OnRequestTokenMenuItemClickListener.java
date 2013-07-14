package de.laxu.apps.sharedurllist;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

class  OnRequestTokenMenuItemClickListener implements OnMenuItemClickListener{
	private final MainActivity mainActivity;
	public OnRequestTokenMenuItemClickListener(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem arg0) {
		mainActivity.requestToken();
		return true;
	}

	
}