package de.laxu.apps.sharedurllist.listeners;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.TextView;
import de.laxu.apps.sharedurllist.DeleteUrlTask;
import de.laxu.apps.sharedurllist.MainActivity;
import de.laxu.apps.sharedurllist.R;
import de.laxu.apps.sharedurllist.UrlListEntry;

public class OnDeleteMenuItemClickListener implements OnMenuItemClickListener{
	private View view;
	private MainActivity mainActivity;

	public OnDeleteMenuItemClickListener(View v, MainActivity mainActivity) {
		this.view = v;
		this.mainActivity = mainActivity;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		TextView linkView = (TextView) this.view.findViewById(R.id.urlListEntryLink);
		UrlListEntry entry = (UrlListEntry) linkView.getTag();
		new DeleteUrlTask(mainActivity, entry).execute();
		return true;
	}

}
