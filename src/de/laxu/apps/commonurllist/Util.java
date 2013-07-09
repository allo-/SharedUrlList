package de.laxu.apps.commonurllist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

public abstract class Util  {

	public static String loadFromURL(MainActivity mainActivity, String url)
			throws LoadException {
				String text="";
				ConnectivityManager cm = (ConnectivityManager) mainActivity
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni != null && ni.isConnected()) {
					try {
						HttpClient client = new DefaultHttpClient();
						HttpResponse response = client.execute(new HttpGet(
								(String) url));
						StatusLine statusLine = response.getStatusLine();
						int statusCode = statusLine.getStatusCode();
			
						if (statusCode == 200) {
							MainActivity.hostnames = new ArrayList<String>();
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(response.getEntity()
											.getContent()));
							String line;
							while ((line = reader.readLine()) != null) {
								text += line;
							}
						} else {
							throw new LoadException("HTTPError " + Integer.toString(statusCode));
						}
					} catch (MalformedURLException e) {
						throw new LoadException("invalid URL");
					} catch (IOException e) {
						throw new LoadException("network Error");
					}
				} else {
					throw new LoadException("no network");
				}
				return text;
			}
	public static String getSettingsErrors(MainActivity mainActivity) {
		ArrayList<String> errorItems = new ArrayList<String>();
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
		if(sharedPrefs.getString("pref_serverurl", "").equals("")){
			errorItems.add("Server-URL");
		}
		if(sharedPrefs.getString("pref_username", "").equals("")){
			errorItems.add("Username");
		}
		if(sharedPrefs.getString("pref_devicename", "").equals("")){
			errorItems.add("Devicename");
		}
		return buildErrorMessage(errorItems);
	}
	private static String buildErrorMessage(ArrayList<String> errorItems){
		String errorString="No ";
		for(int i=0;i<errorItems.size(); i++){
			if(i==0)
				errorString += errorItems.get(i);
			else
				errorString += ", "+errorItems.get(i);
		}
		errorString += " set.";
		return errorString;
	}

	public Util() {
		super();
	}

}