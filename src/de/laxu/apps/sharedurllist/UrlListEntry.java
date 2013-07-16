package de.laxu.apps.sharedurllist;

public class UrlListEntry{
	private int id;
	private String link;
	private String created;
	public UrlListEntry(int id, String link, String created){
		this.id = id;
		this.link = link;
		this.created = created;
	}
	public int getId(){
		return this.id;
	}
	public String getUrl(){
		return this.link;
	}
	public String getCreated(){
		return this.created;
	}
}