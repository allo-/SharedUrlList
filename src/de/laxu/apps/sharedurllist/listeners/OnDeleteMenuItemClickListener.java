package de.laxu.apps.sharedurllist.listeners;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import de.laxu.apps.sharedurllist.DeleteUrlTask;
import de.laxu.apps.sharedurllist.MainActivity;
import de.laxu.apps.sharedurllist.UrlListEntry;

public class OnDeleteMenuItemClickListener implements OnMenuItemClickListener{
	private View view;
	private MainActivity mainActivity;

	public OnDeleteMenuItemClickListener(MainActivity mainActivity, View v) {
		this.view = v;
		this.mainActivity = mainActivity;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		UrlListEntry entry = (UrlListEntry) this.view.getTag();
		new DeleteUrlTask(mainActivity, entry).execute();
		return true;
	}

}
