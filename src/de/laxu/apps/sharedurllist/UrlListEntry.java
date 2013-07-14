package de.laxu.apps.sharedurllist;

class UrlListEntry{
	private String link;
	private String created;
	public UrlListEntry(String link, String created){
		this.link = link;
		this.created = created;
	}
	public String getUrl(){
		return this.link;
	}
	public String getCreated(){
		return this.created;
	}
}