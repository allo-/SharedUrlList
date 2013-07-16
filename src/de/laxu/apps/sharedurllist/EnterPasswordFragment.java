package de.laxu.apps.sharedurllist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.EditText;

public class EnterPasswordFragment extends DialogFragment{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		AlertDialog.Builder builder = new Builder(this.getActivity());
		final EditText input = new EditText(this.getActivity());
		input.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
		builder.setView(input).setTitle("Enter your Password");
		builder.setNegativeButton("Cancel", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.setPositiveButton("Add", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new RequestTokenTask((MainActivity) getActivity(), input.getText().toString()).execute();
			}
		});
		return builder.create();
	}
}