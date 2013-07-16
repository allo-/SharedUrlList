package de.laxu.apps.sharedurllist.listeners;

import de.laxu.apps.sharedurllist.MainActivity;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class OnRefreshMenuItemClickListener implements OnMenuItemClickListener{
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