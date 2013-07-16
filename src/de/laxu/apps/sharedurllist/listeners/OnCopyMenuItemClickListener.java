package de.laxu.apps.sharedurllist.listeners;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import de.laxu.apps.sharedurllist.R;

public class OnCopyMenuItemClickListener implements OnMenuItemClickListener{
	private View view;
	private ClipboardManager clipboard;
	public OnCopyMenuItemClickListener(View v, ClipboardManager clipboard){
			this.view = v;
			this.clipboard = clipboard;
	}
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		ClipData clip = ClipData.newPlainText("url", ((TextView)this.view.findViewById(R.id.urlListEntryLink)).getText());
		clipboard.setPrimaryClip(clip);
		Toast.makeText(view.getContext(), "URL copied to clipboard.", Toast.LENGTH_SHORT).show();
		return true;
	}
}