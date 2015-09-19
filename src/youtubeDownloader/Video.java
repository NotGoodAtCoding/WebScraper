package youtubeDownloader;

public class Video {

	protected String title;
	protected String link;
	protected String artist;
	protected String songName;
	
	public Video(String title, String link){
		this.title = title;
		this.link = link;
		parseTitle();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public void parseTitle(){
		this.artist = title.substring(0, title.indexOf("-") -1);
		this.songName = title.substring(title.indexOf("-") +2).replaceAll("\"", "");
		
	}
}
