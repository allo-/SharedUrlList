package de.laxu.apps.sharedurllist;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

class requestTokenTast extends AsyncTask<Void, Void, String>{
	private MainActivity mainActivity;
	private String url;
	private SharedPreferences sharedPrefs;
	
	public requestTokenTast(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
		String serverurl = sharedPrefs.getString("pref_serverurl", "");
		String username = sharedPrefs.getString("pref_username", "");
		String device = sharedPrefs.getString("pref_devicename", "");
		this.url=serverurl + "?api=true&tokenrequest=true&user="+Uri.encode(username)+"&device="+Uri.encode(device);
		Log.i("tokenURL", this.url);
	}
	@Override
	protected void onPreExecute() {
		this.mainActivity.infoMessage("requesting token ...");
	}
	@Override
	protected String doInBackground(Void... params) {
		String json_input="";
		try {
			json_input = Util.loadFromURL(this.mainActivity, this.url);
		} catch (LoadException e) {
			return e.getError();
		}
		try {
			JSONObject json = new JSONObject(json_input);
			if(json.getString("status").equals("success")){
				sharedPrefs.edit().putString("pref_token", json.getString("token")).commit();
			}else{
				return json.getString("errormessage");
			}
		} catch (JSONException e) {
			return "JSON error";
		}
		return null;
	}
	@Override
	protected void onPostExecute(String errors) {
		if(errors == null){
			mainActivity.infoMessage("token created. You need to accept it in the webinterface now.");
		}else{
			mainActivity.errorMessage(errors);
			
		}
		super.onPostExecute(errors);
	}
	
}