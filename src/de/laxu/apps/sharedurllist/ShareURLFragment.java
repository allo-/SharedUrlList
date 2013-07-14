package de.laxu.apps.sharedurllist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class ShareURLFragment extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		if(Intent.ACTION_SEND.equals(action) && type != null && type.equals("text/plain")){
			String url = intent.getStringExtra(Intent.EXTRA_TEXT);
			addUrl(url);
		}else{
			Log.e("ShareURLFragment", "wrong call to ShareURLFragment");
			finish();
		}
	}
	public void addUrl(String url){
		(new addUrlTask(this, url)).execute();
		
	}
	public void successMessage(String url){
		(new MessageDialogFragment()).setUrl(url).show(this.getFragmentManager(), "successDialog");
	}
	public void errorMessage(String url, String errormessage){
		(new MessageDialogFragment()).setUrl(url).setError(errormessage).show(this.getFragmentManager(), "successDialog");
	}
	public static class MessageDialogFragment extends DialogFragment{
		private String url;
		private String errormessage;
		public MessageDialogFragment setUrl(String url){
			this.url = url;
			return this;
		}
		public MessageDialogFragment setError(String error){
			this.errormessage = error;
			return this;
		}
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			if(errormessage == null && url != null){
				builder
				.setTitle("Added URL to URL-List")
				.setMessage("Added URL: "+url)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("OK", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						getActivity().finish();
					}
				});
			}else if(errormessage!= null){
				builder
				.setTitle("Error adding URL: "+errormessage)
				.setMessage("Error adding URL: "+url)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("Retry", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((ShareURLFragment)getActivity()).addUrl(url);
					}
				})
				.setNegativeButton("Cancel", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						getActivity().finish();
					}
				});
			}
			return builder.create();
		}
	}
}
