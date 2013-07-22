package de.laxu.apps.sharedurllist;

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
import android.preference.PreferenceManager;

public abstract class Util  {

	public static String loadFromURL(MainActivity mainActivity, String url)
			throws LoadException {
				String text="";
				ConnectivityManager cm = (ConnectivityManager) mainActivity
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni != null && ni.isConnected()) {
					if(url == null || url.equals("")){
						throw new LoadException("no URL given.");
					}
					try {
						HttpClient client = new DefaultHttpClient();
						HttpResponse response = client.execute(new HttpGet(url));
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
	public static boolean hasSettingsErrors(MainActivity mainActivity){
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
		String serverURL = sharedPrefs.getString("pref_serverurl", "");
		String username = sharedPrefs.getString("pref_username", "");
		String devicename = sharedPrefs.getString("pref_devicename", "");
		
		return (
			(serverURL.startsWith("https://") || serverURL.startsWith("http://"))
			&& username.equals("")
			&& devicename.equals("")
		);
	}
	public static String getSettingsErrors(MainActivity mainActivity) {
		ArrayList<String> errorItems = new ArrayList<String>();
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
		String errorString = "";
		String serverURL = sharedPrefs.getString("pref_serverurl", "");
		String username = sharedPrefs.getString("pref_username", "");
		String devicename = sharedPrefs.getString("pref_devicename", "");
		
		if(serverURL.equals("")){
			errorItems.add("Server-URL");
		}
		if(username.equals("")){
			errorItems.add("Username");
		}
		if(devicename.equals("")){
			errorItems.add("device name");
		}
		if(errorItems.size() >0)
			errorString += "No " + buildCommaList(errorItems) +" set.";
		
		if(!serverURL.startsWith("https://") && !serverURL.startsWith("http://")){
			if(!errorString.equals(""))
				errorString += "\n";
			errorString += "Server-URL does not start with \"https://\" or \"http://\".";
		}
		return errorString;
	}
	private static String buildCommaList(ArrayList<String> errorItems){
		String commaString="";
		for(int i=0;i<errorItems.size(); i++){
			if(i==0){ // first
				commaString += errorItems.get(i);
			}else if(i==errorItems.size()-1){ // last
				commaString += " and " + errorItems.get(i);
			}else{
				commaString += ", "+errorItems.get(i);
			}
		}
		return commaString;
	}

	public Util() {
		super();
	}

}