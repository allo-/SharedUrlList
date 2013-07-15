package de.laxu.apps.sharedurllist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class ShareURLFragment extends FragmentActivity {
	protected final int NOTIFICATION_ID = 1;
	NotificationManager notificationManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		if(Intent.ACTION_SEND.equals(action) && type != null && type.equals("text/plain")){
			String url = intent.getStringExtra(Intent.EXTRA_TEXT);
			createNotification("adding URL to List ...", url, android.R.drawable.ic_menu_upload, true);  //TODO: use own icon or local copy, as android guide says not to use android.drawable
			addUrl(url);
			finish();
		}else{
			Log.e("ShareURLFragment", "wrong call to ShareURLFragment");
			finish();
		}
	}
	public void createNotification(String title, String text, int icon, boolean progress){
		notificationManager = ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE));
		notificationManager.cancelAll();
		Intent intent = new Intent(this, MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(intent);
		PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
		Builder builder = new NotificationCompat.Builder(this);
		builder
		.setSmallIcon(icon)
		.setContentTitle(title)
		.setContentText(text)
		.setAutoCancel(true)
		.setTicker(title+": "+text)
		.setContentIntent(pIntent);
		if(progress){
			builder.setProgress(1, 0, true);
		}
		Notification notification = builder.build();
		notificationManager.notify(NOTIFICATION_ID, notification);
		
	}

	public void removeNotification(){
		((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE))
		.cancel(NOTIFICATION_ID);
	}
	public void addUrl(String url){
		(new addUrlTask(this, url)).execute();
	}
	public void successMessage(String url){
		createNotification("Added URL", url, android.R.drawable.ic_menu_add, false); //TODO: use own icon or local copy, as android guide says not to use android.drawable
	}
	public void errorMessage(String url, String errormessage){
		createNotification("Error adding URL", errormessage, android.R.drawable.ic_menu_add, false); //TODO: use own icon or local copy, as android guide says not to use android.drawable
	}

}
