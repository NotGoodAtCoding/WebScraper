package youtubeDownloader;

import java.util.ArrayList;

import org.openqa.selenium.Keys;

import selenium.utils.Automato;

public class YoutubeRunner extends Automato{

	public YoutubeRunner() {
		super("https://www.youtube.com/feed/subscriptions");
	}
	
	public boolean login(){
		sendKeys("id=Email",YTConst.USER_EMAIL);
		sendKeys("id=Email", Keys.RETURN);

		sendKeys("id=Passwd", YTConst.USER_PASS);
		sendKeys("id=Passwd", Keys.RETURN);
		
		return true;
	}
	
	private String videoLinkXPathBuilder(int index){
		String built = YTConst.VIDEO_LIST_PARTIAL_XPATH;
		built += ("[" + index + "]");
		built += YTConst.VIDEO_LINK_POSTFIX__PARTIAL_XPATH;
	
		return built;
	}
	
	public String vidTitleAtIndex(int index){
		return getWebElement(videoLinkXPathBuilder(index)).getAttribute("title");
	}
	public String vidLinkAtIndex(int index){
		return getWebElement(videoLinkXPathBuilder(index)).getAttribute("href");
	}
	
	public ArrayList<Video> getAllVideosInFeed(){
		ArrayList<Video> videos = new ArrayList<Video>();
		loadMore();
		int numberOfVideos = getNumberOfVideosOnPage();
		
		for(int i=1; i<numberOfVideos; i++){
			String title = vidTitleAtIndex(i);
			String link = vidLinkAtIndex(i);

			videos.add(new Video(title, link));
		}
		return videos;
	}
	
	private int getNumberOfVideosOnPage(){
		return getWebElements(YTConst.VIDEO_LIST_PARTIAL_XPATH).size();
	}
	
	private boolean loadMore(){
		for(int i=0; i<10;i++){
			if(!click(YTConst.LOAD_MORE_BUTTON_XPATH)) break;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		}
		return true;
	}
}
