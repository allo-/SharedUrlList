package de.laxu.apps.sharedurllist;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;

public class DeleteUrlTask extends AsyncTask<Void, Void, String>{
	private MainActivity mainActivity;
	private String requesturl;
	private UrlListEntry entry;

	public DeleteUrlTask(MainActivity mainActivity, UrlListEntry entry){
		this.mainActivity = mainActivity;
		this.entry = entry;
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
		String serverurl = sharedPrefs.getString("pref_serverurl", "");
		String token = sharedPrefs.getString("pref_token", "");
		this.requesturl = serverurl + "?api=true&token="+Uri.encode(token)+"&delete=true&id="+Uri.encode(String.valueOf(entry.getId()));
	}

	@Override
	protected String doInBackground(Void... params) {
		String json_input="";
		try {
			json_input = Util.loadFromURL(this.mainActivity, this.requesturl);
			try {
				JSONObject json = new JSONObject(json_input);
				if(json.getString("status").equals("success")){
					return null;
				}else{
					return json.getString("errormessage");
				}
			} catch (JSONException e) {
				return "JSON error";
			}		
		} catch (LoadException e) {
			return e.getError();
		}
	}

	@Override
	protected void onPostExecute(String errormessage) {
		super.onPostExecute(errormessage);
		if(errormessage == null){
			for(int i=0;i<MainActivity.urlTabs.size();i++){
				@SuppressWarnings("unchecked")
				ArrayAdapter<UrlListEntry> arrayAdapter = ((ArrayAdapter<UrlListEntry>)MainActivity.urlTabs.get(i).getListAdapter());
				arrayAdapter.remove(entry);
				arrayAdapter.notifyDataSetChanged();
			}
			Util.createNotification(this.mainActivity, "Deleted URL", entry.getUrl(), android.R.drawable.ic_menu_delete, false);
		} else {
			Util.createNotification(this.mainActivity, "Error deleting URL", errormessage, android.R.drawable.ic_menu_delete, false);
		}
	}
}
