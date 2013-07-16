package de.laxu.apps.sharedurllist.listeners;

import de.laxu.apps.sharedurllist.MainActivity;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class  OnRequestTokenMenuItemClickListener implements OnMenuItemClickListener{
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