package de.laxu.apps.commonurllist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ShareURLIntent extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		finish();
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		if(Intent.ACTION_SEND.equals(action) && type != null && type.equals("text/plain")){
			String url = intent.getStringExtra(Intent.EXTRA_TEXT);
			
		}
	}

}
