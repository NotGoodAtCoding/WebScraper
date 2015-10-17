package flikrDownloader;

import java.util.ArrayList;

import selenium.utils.Automato;

public class FlikrRunner extends Automato {

	public static void main(String[] args) {
		FlikrRunner fRun = new FlikrRunner();
		fRun.downloadAll();
	}

	private static ArrayList<String> links = new ArrayList<String>();

	public FlikrRunner() {
		super("https://www.flickr.com/photos/54557225@N05/21269879058/in/album-72157658622513046/");
	}

	public void downloadAll(){
		getAllLinks();
		downloadAllLinks();
	}

	public void getAllLinks(){
		for(int i=0; i<400; i++){
			if(!getLinkOnPage()) System.out.println("Link Fetch Failure");;
			if(!navToNextPage()) break;
		}
	}

	public boolean getLinkOnPage(){
		String link = getWebElement("xpath=//img[@class='main-photo']").getAttribute("src");
		
		if(links.contains(link)) return false;
		if(link.equals("")){
			System.out.println("Link on page not found");
			return true;
		}
		links.add(link);
		System.out.println(link);
		return true;
	}

	public boolean navToNextPage(){
		if(!click("xpath=//a[@class='navigate-target navigate-next']")) return false;
		return true;
	}

	public void downloadAllLinks(){
		for(String link : links){
			get(link);
			thirdRightClickOption("xpath=/html/body/img");
			goToSleep(5);
		}
	}
}
