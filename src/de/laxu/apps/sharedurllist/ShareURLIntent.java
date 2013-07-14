package de.laxu.apps.sharedurllist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ShareURLIntent extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		if(Intent.ACTION_SEND.equals(action) && type != null && type.equals("text/plain")){
			String url = intent.getStringExtra(Intent.EXTRA_TEXT);
			(new MessageDialogFragment()).setUrl(url). show(getFragmentManager(), "successDialog");
		}
		//finish();
	}
	public static class MessageDialogFragment extends DialogFragment{
		private String url;
		public MessageDialogFragment setUrl(String url){
			this.url = url;
			return this;
		}
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder
			.setTitle("added URL to URL-List")
			.setMessage("added URL: "+url)
			.setIcon(android.R.drawable.ic_dialog_info)
			.setPositiveButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					getActivity().finish();
				}
			});
			return builder.create();
		}
	}
}
