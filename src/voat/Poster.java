package voat;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import selenium.utils.Automato;

public class Poster extends Automato {
	
	private static final String USER = "SeleniumBot";
	private static final String PASS = "ForTesting!23$";
	private static final String LOGIN_LINK = "//*[@id='header-account']/div/span/a[1]";
	private static final String USER_IN = "//*[@id='UserName']";
	private static final String PASS_IN = "//*[@id='Password']";
	private static final String SUBMIT ="//*[@id='loginForm']/form/div[5]/div/input";
	
	private static final String SHARE_LINK = "//*[@id='container']/div[1]/div[2]/a";
	private static final String URL_IN = "//*[@id='Content']";
	private static final String TITLE_IN = "//*[@id='LinkDescription']";
	private static final String SUBMIT_POST = "//*[@id='container']/div/form/div[2]/div[8]/div/input";
	private static final String CAPTCHA = "//*[@id='recaptcha-anchor-label']";
	private static final String POST_LIST_PREFIX = "//*[@id='container']/div[2]/div[1]/div[";
	private static final String POST_LIST_POSTFIX = "]/div[3]/p[1]/a";
	
	private static ArrayList<String> lastPosts;

	public Poster() {
		super("http://voat.co/v/dfr");
		lastPosts = new ArrayList<String>();
	}

	public static void main(String[] args) {
		LinkCollector lc = new LinkCollector();
		lc.collectReddit();
		lc.quitDriver();
		
		Poster bot = new Poster();
		bot.login();
		bot.getLastPosts();
		
		onePerLine(lc.getTitles());
		onePerLine(lastPosts);
		
		for(int i=0; i< lc.getTitles().size(); i++){
			if(lastPosts.contains(lc.getTitles().get(i))){
				continue;
			}
			bot.shareLink(lc.getTitles().get(i), lc.getUrls().get(i));
		}
		//bot.quitDriver();
		return;
	}

	private void login(){
		click(LOGIN_LINK);
		sendKeys(USER_IN, USER);
		sendKeys(PASS_IN, PASS);
		click(SUBMIT);
	}
	
	private void getLastPosts(){
		get("https://voat.co/v/dfr/new");
		for(int i=2; i<22; i++){ 
			lastPosts.add(getWebElement(POST_LIST_PREFIX + i + POST_LIST_POSTFIX).getAttribute("title"));
		}
	}
	
	private void shareLink(String title, String link){
		click(SHARE_LINK);
		sendKeys(URL_IN, link);
		sendKeys(TITLE_IN, title);
		if(exists(CAPTCHA)){
			alertCaptcha();
		}
		click(SUBMIT_POST);
		goToSleep(60);
		get("http://voat.co/v/dfr");
	}
	
	private void alertCaptcha(){
		Scanner sc = new Scanner(System.in);
		System.out.println("CAPTCHA DETECTED, COMPLETE AND PRESS ANY KEY TO CONTINUE");
		if(sc.hasNext()){
			sc.close();
			return;
		}
	}
	
	private static void onePerLine(ArrayList<String> arr){
		for(String s : arr){
			System.out.println(s);
		}
	}
}
