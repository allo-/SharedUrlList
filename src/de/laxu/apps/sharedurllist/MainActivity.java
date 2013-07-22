package de.laxu.apps.sharedurllist;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import de.laxu.apps.sharedurllist.listeners.OnAddUrlMenuItemClickListener;
import de.laxu.apps.sharedurllist.listeners.OnCopyMenuItemClickListener;
import de.laxu.apps.sharedurllist.listeners.OnDeleteMenuItemClickListener;
import de.laxu.apps.sharedurllist.listeners.OnRefreshMenuItemClickListener;
import de.laxu.apps.sharedurllist.listeners.OnRequestTokenMenuItemClickListener;
import de.laxu.apps.sharedurllist.listeners.OnSettingsButtonClickListener;
import de.laxu.apps.sharedurllist.listeners.OnSettingsMenuItemClickListener;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	public String urllists_json = "";
	
	static HashMap<String, ArrayList<UrlListEntry>> hostURLList;
	static ArrayList<String> hostnames;
	static SparseArray<UrlTab> urlTabs;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		hostnames=new ArrayList<String>();
		hostURLList = new HashMap<String, ArrayList<UrlListEntry>>();
		urlTabs = new SparseArray<UrlTab>();
		
		if(savedInstanceState != null && savedInstanceState.containsKey("urllists_json")){
			try {
				this.urllists_json = (String) savedInstanceState.get("urllists_json");
				String error = LoadUrlListTask.parse_urllists(this.urllists_json);
				if(error != null){
					this.errorMessage(error);
				}
			} catch (JSONException e) {
				// TODO how to restart with a fresh SectionPager?
			}
		}
		
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		Button settingsButton = (Button) findViewById(R.id.settingsButton);
		settingsButton.setOnClickListener(new OnSettingsButtonClickListener(this));
		if(this.urllists_json == null || this.urllists_json.equals("")){
			updateLists();
		}
		if(savedInstanceState != null && this.mViewPager.getChildCount() >0){
			this.mViewPager.setCurrentItem(savedInstanceState.getInt("viewPagerItem", 0));
		}
	}
	
	@Override
	public void onStart(){
		super.onStart();
		String errors = Util.getSettingsErrors(this);
		if(Util.hasSettingsErrors(this))
			errorMessageWithSettingsButton(errors);
		else
			hideMessage();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("urllists_json", this.urllists_json);
		outState.putInt("viewPagerItem", this.mViewPager.getCurrentItem());
	};

	public void updateLists(){
		hostURLList = new HashMap<String, ArrayList<UrlListEntry>>();
		if(!Util.hasSettingsErrors(this)){
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
			if(sharedPrefs.getString("pref_token", "").equals("")){
				requestToken();
				//we will proceed with updating the list from the RequestTokenTask by calling updateLists again.
			}else{
				new LoadUrlListTask(this).execute();
			}
		}
	}
	public void requestToken(){
		if(!Util.hasSettingsErrors(this)){
			(new EnterPasswordFragment()).show(getFragmentManager(), "AddUrlDialog");
		}
		
	}
	public void errorMessage(String errormessage){
		TextView messageTextView =  (TextView) findViewById(R.id.messageTextView);
		messageTextView.setText(errormessage);
		messageTextView.setVisibility(View.VISIBLE);
		messageTextView.setTextColor(Color.RED);
	}
	public void errorMessageWithSettingsButton(String errormessage){
		Button settings_button = (Button) findViewById(R.id.settingsButton);
		settings_button.setVisibility(View.VISIBLE);
		errorMessage(errormessage);
	}
	public void infoMessage(String infomessage){
		TextView messageTextView =  (TextView) findViewById(R.id.messageTextView);
		messageTextView.setText(infomessage);
		messageTextView.setVisibility(View.VISIBLE);
		messageTextView.setTextColor(Color.BLACK);
	}
	public void hideMessage(){
		TextView messageTextView =  (TextView) findViewById(R.id.messageTextView);
		messageTextView.setVisibility(View.GONE);
		Button settings_button = (Button) findViewById(R.id.settingsButton);
		settings_button.setVisibility(View.GONE);
		// reset to defaults
		messageTextView.setTextColor(Color.BLACK);
		messageTextView.setText("");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		boolean settings_ok = !Util.hasSettingsErrors(this);
		
		MenuItem settingsMenu = menu.findItem(R.id.settingsmenu);
		settingsMenu.setOnMenuItemClickListener(new OnSettingsMenuItemClickListener(this));
		
		MenuItem refreshMenuItem = menu.findItem(R.id.refresh_menuitem);
		refreshMenuItem.setEnabled(settings_ok);
		refreshMenuItem.setOnMenuItemClickListener(new OnRefreshMenuItemClickListener(this));
		
		MenuItem requestTokenMenuItem = menu.findItem(R.id.requesttoken_menuitem);
		requestTokenMenuItem.setEnabled(settings_ok);
		requestTokenMenuItem.setOnMenuItemClickListener(new OnRequestTokenMenuItemClickListener(this));
		
		MenuItem addUrlMenuItem = menu.findItem(R.id.addurl_menuitem);
		addUrlMenuItem.setEnabled(settings_ok);
		addUrlMenuItem.setOnMenuItemClickListener(new OnAddUrlMenuItemClickListener(this));
		return true;
	}
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public UrlTab getItem(int position) {
			Integer key = Integer.valueOf(position);
			UrlTab fragment = new UrlTab();
			urlTabs.put(key, fragment);
			Bundle args = new Bundle();
			args.putInt("position", position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return hostnames.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return hostnames.get(position);
		}
	}

	public static class UrlTab extends ListFragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public UrlArrayAdapter arrayAdapter;
		@Override
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);
			arrayAdapter = new UrlArrayAdapter(getActivity(), this, R.layout.urllist_entry, new ArrayList<UrlListEntry>());
			setListAdapter(arrayAdapter);
			int position = ((Integer) this.getArguments().get("position")).intValue();
			String hostname = hostnames.get(position);
			assert hostURLList != null;
			assert hostname != null;
			assert arrayAdapter != null;
			ArrayList<UrlListEntry> list = hostURLList.get(hostname);
			if(list != null){
				arrayAdapter.addAll(list);
				arrayAdapter.notifyDataSetChanged();
			}
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return super.onCreateView(inflater, container, savedInstanceState);
		}
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
		    ContextMenuInfo menuInfo) {
			if (v.getId()==R.id.urlist_entry_layout) {
				MenuItem copy = menu.add("copy");
				ClipboardManager clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
				copy.setOnMenuItemClickListener(new OnCopyMenuItemClickListener(v, clipboard));
				MenuItem delete = menu.add("delete");
				delete.setOnMenuItemClickListener(new OnDeleteMenuItemClickListener(v, (MainActivity) getActivity()));
			}
		}
	}
}
class UrlArrayAdapter extends ArrayAdapter<UrlListEntry>{
	Context context;
	ArrayList<UrlListEntry> arrayList;
	ListFragment fragment;
	public UrlArrayAdapter(Context context, ListFragment fragment, int layoutId, ArrayList<UrlListEntry> arrayList){
		super(context, layoutId, arrayList);
		this.arrayList = arrayList;
		this.context = context;
		this.fragment = fragment;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view;
		if(convertView != null){
			view = convertView;
		}else{
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.urllist_entry, parent, false);
			UrlListEntry entry = arrayList.get(position);
			String url = entry.getUrl();
			String created = entry.getCreated();
			TextView linkView = (TextView) view.findViewById(R.id.urlListEntryLink);
			linkView.setText(url);
			((TextView) view.findViewById(R.id.urlListEntryCreatedDate)).setText(created);
			linkView.setTag(entry);
			fragment.registerForContextMenu(view);
		}
		return view;
	}
}
