package flikrDownloader;

import java.util.ArrayList;

import selenium.utils.Automato;

public class FlikrRunner extends Automato {

	public static void main(String[] args) {
		FlikrRunner fRun = new FlikrRunner();
		fRun.downloadAll();
	}

	private static final String IMAGE_XPATH = "xpath=/html/body/div[1]/div/div[2]/div/div[1]/img[2]";
	private static final String NEXT_XPATH = "xpath=/html/body/div[1]/div/div[2]/div/a[1]";

	private ArrayList<String> links = new ArrayList<String>();

	public FlikrRunner() {
		super("https://www.flickr.com/photos/54557225@N05/21269879058/in/album-72157658622513046/");
	}

	public void downloadAll(){
		getAllLinks();
		downloadAllLinks();
	}

	public void getAllLinks(){
		for(int i=0; i<400; i++){
			if(!getLinkOnPage()) break;
			navToNextPage();
		}
	}

	public boolean getLinkOnPage(){
		String link = getWebElement(IMAGE_XPATH).getAttribute("src");
		//if(links.contains(link)) return false;
		links.add(link);
		System.out.println(link);
		return true;
	}

	public void navToNextPage(){
		click(NEXT_XPATH);
		goToSleep(2);
	}

	public void downloadAllLinks(){
		for(String link : links){
			get(link);
			goToSleep(1);
			thirdRightClickOption("xpath=/html/body/img");
		}
	}
}
