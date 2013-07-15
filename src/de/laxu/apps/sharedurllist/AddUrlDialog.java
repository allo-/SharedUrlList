package de.laxu.apps.sharedurllist;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;

public class AddUrlDialog extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new addUrlFragment().show(getFragmentManager(), "AddUrlDialog");
	}
	public static class addUrlFragment extends DialogFragment{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			super.onCreateDialog(savedInstanceState);
			AlertDialog.Builder builder = new Builder(this.getActivity());
			final EditText input = new EditText(this.getActivity());
			builder.setView(input).setTitle("Add URL");
			builder.setNegativeButton("Cancel", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					getActivity().finish();
				}
			});
			builder.setPositiveButton("Add", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					getActivity().finish();
					Intent intent = new Intent(getActivity(), ShareURLFragment.class).setAction(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, input.getText().toString());
					getActivity().startActivity(intent);
					
				}
			});
			return builder.create();
		}
	}
}
