package voat;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;

import selenium.utils.Automato;

public class LinkCollector extends Automato{
	
	private static final String SITE_TABLE = "//*[@id='siteTable']";
	private static final String LINK_POSTFIX = "/div[2]/p[1]/a";
	private static final String NSFW_FLAG = "/html/body/div[2]/div/form/div/button[2]";
	private ArrayList<String> titles = new ArrayList<String>();
	private ArrayList<String> urls = new ArrayList<String>();

	public LinkCollector() {
		super("https://www.reddit.com/r/undelete/new");
	}
	
	public void collectReddit(){
		boolean active = true;
		int i =1;
		while(active){
			if(exists(SITE_TABLE + "/div["+i+"]" + LINK_POSTFIX)){
				String title = (getWebElement(SITE_TABLE + "/div["+i+"]" + LINK_POSTFIX).getText());
				String link = (getLinkFromPost(getWebElement(SITE_TABLE + "/div["+i+"]" + LINK_POSTFIX)));
				titles.add(title);
				urls.add(link);
				i+=2;
			}
			else{
				active = false;
			}
		}
	}
	
	public String getLinkFromPost(WebElement post){
		Automato fetch = new Automato(post.getAttribute("href"));
		//check for NSFW check
		if(!fetch.exists(SITE_TABLE +"/div[1]" + LINK_POSTFIX)){
			fetch.click(NSFW_FLAG);
		}
		String link = fetch.getWebElement(SITE_TABLE +"/div[1]" + LINK_POSTFIX).getAttribute("href");
		fetch.quitDriver();
		return (link);
	}
	
	public ArrayList<String> getTitles() {
		return titles;
	}

	public void setTitles(ArrayList<String> titles) {
		this.titles = titles;
	}

	public ArrayList<String> getUrls() {
		return urls;
	}

	public void setUrls(ArrayList<String> urls) {
	}
}
