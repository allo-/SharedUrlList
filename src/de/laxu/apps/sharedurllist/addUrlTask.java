package de.laxu.apps.sharedurllist;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

class addUrlTask extends AsyncTask<Void, Void, String>{
	private ShareURLFragment activity;
	private String requesturl;
	private String url;
	private SharedPreferences sharedPrefs;
	
	public addUrlTask(ShareURLFragment activity, String url){
		this.activity = activity;
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		String serverurl = sharedPrefs.getString("pref_serverurl", "");
		String token = sharedPrefs.getString("pref_token", "");
		this.url = url;
		this.requesturl=serverurl + "?api=true&token="+Uri.encode(token)+"&add=true&url="+Uri.encode(url);
	}
	@Override
	protected void onPreExecute() {
		
	}
	@Override
	protected String doInBackground(Void... params) {
		String json_input="";
		try {
			json_input = Util.loadFromURL(this.activity, this.requesturl);
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
	protected void onPostExecute(String errors) {
		if(errors == null){
			this.activity.successMessage(this.url);
		}else{
			this.activity.errorMessage(url, errors);
			
		}
		super.onPostExecute(errors);
	}
	
}