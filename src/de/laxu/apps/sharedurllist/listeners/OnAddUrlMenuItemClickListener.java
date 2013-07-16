package de.laxu.apps.sharedurllist.listeners;

import de.laxu.apps.sharedurllist.AddUrlDialog;
import de.laxu.apps.sharedurllist.MainActivity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class OnAddUrlMenuItemClickListener implements OnMenuItemClickListener{
	private final MainActivity mainActivity;
	public OnAddUrlMenuItemClickListener(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		mainActivity.startActivity(new Intent(mainActivity, AddUrlDialog.class));
		return true;
	}
	
}