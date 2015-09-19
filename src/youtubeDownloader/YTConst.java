package youtubeDownloader;

public class YTConst {
	
	//Following credentials are for a fake gmail account with false information. Use for testing only.
	public static final String USER_EMAIL = "JRustlin";
	public static final String USER_PASS = "testingOnly1221";

	//Youtube Page XPaths
	public static final String SIGN_IN_BUTTON_XPATH = "xpath=//@div[class='signin-container']/button";
	public static final String MAIN_VIDEO_PANE_XPATH = "xpath=//*[@id='browse-items-primary']";
	public static final String VIDEO_LIST_PARTIAL_XPATH = MAIN_VIDEO_PANE_XPATH+ "/ol/li";
	public static final String VIDEO_LINK_POSTFIX__PARTIAL_XPATH ="/ol/li/div/div[1]/div[2]/ul/li/div/div/div/div[2]/h3/a";
	public static final String LOAD_MORE_BUTTON_XPATH = MAIN_VIDEO_PANE_XPATH + "/button";
}
