package youtubeDownloader;

import java.util.ArrayList;

import org.openqa.selenium.Keys;

import selenium.utils.Automato;

public class Converter extends Automato{

	public static final String INPUT_FORM_ID = "id=search";
	public static final String SUBMIT_XPATH = "xpath=//*[@id='search-wrapper']/input[2]";
	public static final String SONG_TITLE_INPUT_XPATH = "xpath=//*[@id='refreshedtitle']";
	public static final String SONG_ARTIST_INPUT_XPATH = "xpath=//*[@id='refreshedartist']";
	public static final String DOWNLOAD_BUTTON_XPATH = "xpath=//*[@id='downloadbtn']";
	
	public Converter(){                 
		super("http://www.thatmp3.com/");
	}
	
	public boolean convertAll(ArrayList<Video> videoList){
		for(Video video : videoList){
			System.out.println("Converting " + (videoList.indexOf(video) +1) + " of " + videoList.size());
			convert(video);
		}
		return true;
	}
	
	public boolean convert(Video video){
		
		inputLink(video.getLink());
		
		submitForConversion();
		
		formatFileInfo(video);
		
		initiateDownload();
		
		//wait for stupid anti-adblock to expire. Those trashcans...
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		waitForDownload();
		
		initiateDownload();
		System.out.println("SONG: '" + video.songName + "' DL'd!!");
		
		return true;
	}
	
	private boolean inputLink(String link){
		return sendKeys(INPUT_FORM_ID, link); 
	}
	
	private boolean submitForConversion(){
		return click(SUBMIT_XPATH);
	}
	
	private boolean formatFileInfo(Video video){
		clearInputField(SONG_TITLE_INPUT_XPATH);
		sendKeys(SONG_TITLE_INPUT_XPATH, video.songName);
		
		clearInputField(SONG_ARTIST_INPUT_XPATH);
		sendKeys(SONG_ARTIST_INPUT_XPATH, video.artist);
		return true;
	}
	private boolean clearInputField(String locator){
		click(locator);
		int clearLength = getWebElement(locator).getAttribute("value").length();
		for(int i=0; i<clearLength; i++){
			sendKeys(locator, Keys.ARROW_RIGHT);
		}
		for(int i=0; i<clearLength; i++){
			sendKeys(locator, Keys.BACK_SPACE);
		}
		return true;
	}
	
	private boolean initiateDownload(){
		return click(DOWNLOAD_BUTTON_XPATH);
	}
	
	private boolean waitForDownload(){
		while(!getWebElement(DOWNLOAD_BUTTON_XPATH).getAttribute("class").contains("afterdownload")){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
}
