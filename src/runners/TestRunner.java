package runners;

import org.openqa.selenium.WebElement;
import java.util.List;

import selenium.utils.Automato;

public class TestRunner {

	public static void main(String[] args) {
		Automato autist = new Automato("http://nutritiondata.self.com/");
		
		if(autist.exists("id='selflogo'")){
			System.out.println("Self Logo Exists");
		}
		
		if(autist.clickIfExists("id='selflogo'")){
			System.out.println("Self Logo Clicked");
		}
		
		if(autist.exists(SelfAutomato.SEARCH_BAR_XPATH_LOCATOR)){
			System.out.println("Search Bar Found");
		}
		
		if(autist.sendKeys(SelfAutomato.SEARCH_BAR_XPATH_LOCATOR, "Chicken Breast")){
			System.out.println("Keys Sent");
		}
		
		if(autist.clickIfExists("class=searchNow")){
			System.out.println("Search Started");
		}
		
		if(autist.exists(SelfAutomato.FOOD_SEARCH_RESULTS_LOCATOR)){
			System.out.println("Search Results Found:");
		}
		
		//TODO: Create Method with this functionality
		List<WebElement> weList = autist.getWebElements(SelfAutomato.FOOD_SEARCH_RESULTS_LOCATOR);
		for(WebElement we : weList){
			System.out.println(we.getText());
		}
		
		autist.quitDriver();
	}

}
