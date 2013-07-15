package de.laxu.apps.sharedurllist;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class ShareURLFragment extends FragmentActivity {
	NotificationManager notificationManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		if(Intent.ACTION_SEND.equals(action) && type != null && type.equals("text/plain")){
			String url = intent.getStringExtra(Intent.EXTRA_TEXT);
			Util.createNotification(this, "adding URL to List ...", url, android.R.drawable.ic_menu_upload, true);  //TODO: use own icon or local copy, as android guide says not to use android.drawable
			addUrl(url);
			finish();
		}else{
			Log.e("ShareURLFragment", "wrong call to ShareURLFragment");
			finish();
		}
	}
	public void addUrl(String url){
		(new addUrlTask(this, url)).execute();
	}
	public void successMessage(String url){
		Util.createNotification(this, "Added URL", url, android.R.drawable.ic_menu_add, false); //TODO: use own icon or local copy, as android guide says not to use android.drawable
	}
	public void errorMessage(String url, String errormessage){
		Util.createNotification(this, "Error adding URL", errormessage, android.R.drawable.ic_menu_add, false); //TODO: use own icon or local copy, as android guide says not to use android.drawable
	}

}
