package youtubeDownloader;

public class FullRunner {

	public static void main(String[] args) {

		YoutubeRunner yt = new YoutubeRunner();
		Converter converter = new Converter();
		
		yt.login();

		converter.convertAll(yt.getAllVideosInFeed());

		yt.quitDriver();
		converter.quitDriver();

	}

}
