package selenium.utils;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Main Superclass for generic functions of Selenium.
 * TODO: List Text from WebElements list (Might go in separate class)
 * TODO: Fully Test
 * TODO: Write Javadocs
 * @author Stefan Kraus
 *
 */
public class Automato {

	WebDriver driver;

	public Automato(){
		new Automato("www.google.com");
	}
	public Automato(String startingUrl){
		setupBrowser(startingUrl);
	}

	/**
	 * Helper for Constructor
	 * @param startingUrl
	 * @return
	 */
	private boolean setupBrowser(String startingUrl){
		try{
			FirefoxProfile profile = new FirefoxProfile(new File("lib/FF Profile"));
			driver = new FirefoxDriver(profile);
			driver.get(startingUrl);
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return true;
	}

	/**
	 * TESTED
	 * FIXME: Code looks bad, might want to redo how this and setupBrowser are done.
	 * @param Url
	 * @return
	 */
	public boolean get(String Url){
		try{
			driver.get(Url);
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//START OF CLICK BLOCK
	
	public boolean click(String locator){
		return click(getByElement(locator));
	}
	
	public boolean click(By by){
		try{
			WebElement element = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(by));
			element.click();
			return true;
		} catch (StaleElementReferenceException e){
			System.out.println("StaleElementReference ON: " + by.toString());
			return false;
		} catch (TimeoutException e){
			System.out.println("Timeout ON: " + by.toString());
			return false;
		} catch (Exception e){
			System.out.println("Exception ON: " + by.toString());
			return false;
		}
	}
	
	/**
	 * TESTED
	 */
	public boolean clickIfExists(String locator){
		return clickIfExists(getByElement(locator));
	}
	public boolean clickIfExists(By element){
		if(exists(element)){
			try{
				getWebElement(element).click();
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

	//END OF CLICK BLOCK

	//START OF GET BY ELEMENT BLOCK

	/**
	 * TESTED
	 * @param locator
	 * @return
	 */
	public boolean exists(String locator){
		return exists(getByElement(locator));
	}

	public boolean exists(By element){
		return !getWebElement(element).equals(null);
	}

	public By getByElement(String locator){
		By element = null;
		if(locator.startsWith("id=")){
			element = By.id(locator.replaceAll("id=", "").trim().replaceAll("['\"]", ""));
		}
		else if(locator.startsWith("class=")){
			element = By.className(locator.replaceAll("class=", "").trim().replaceAll("['\"]", ""));
		}
		else if(locator.startsWith("name=")){
			element = By.name(locator.replaceAll("value=", "").trim().replaceAll("['\"]", ""));
		}
		else if(locator.startsWith("xpath=")){
			element = By.xpath(locator.replaceAll("xpath=", "").trim().replaceAll("[\"]", ""));
		}
		else{
			System.out.println("MALFORMED BY ELEMENT");
		}

		return element;
	}

	//END OF GET BY ELEMENT BLOCK

	//START OF GET WEBELEMENTS BLOCK

	/**
	 * Calls getByElements, then returns the 0th element if existent. 
	 * @param locator
	 * @return the WebElement if found, null if not
	 */
	public WebElement getWebElement(String locator){
		return getWebElement(getByElement(locator));
	}

	public WebElement getWebElement(By element){
		try{
			waitUntilPresent(element);
			return getWebElements(element).get(0);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Handler for getting elements with a String locator.
	 * Locator should be of the form [identifier] = [value] with no spaces, special characters, and properly escaped.
	 * Supports id, class, value, and xpath identifiers.
	 * @param locator
	 * @return the WebElement if found, null if not
	 */
	public List<WebElement> getWebElements(String locator){
		return getWebElements(getByElement(locator));
	}

	public List<WebElement> getWebElements(By element){
		List<WebElement> elements = null;
		try{
			waitUntilPresent(element);
			elements = driver.findElements(element);
		} catch (Exception e){
			e.printStackTrace();
		}
		return elements;
	}

	//END OF GET WEBELEMENTS BLOCK

	//START OF KEYSEND BLOCK

	public boolean sendKeys(String locator, String keys){
		return sendKeys(getByElement(locator), keys);
	}

	public boolean sendKeys(By element, String keys){
		try{
			waitUntilPresent(element);
			return sendKeys(getWebElement(element), keys);
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}


	public boolean sendKeys(WebElement element, String keys){
		try{
			element.sendKeys(keys);
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// KEYS SUB BLOCK
	
	
	public boolean sendKeys(String locator, Keys keys){
		return sendKeys(getByElement(locator), keys);
	}


	public boolean sendKeys(By element, Keys keys){
		try{
			waitUntilPresent(element);
			return sendKeys(getWebElement(element), keys);
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean sendKeys(WebElement element, Keys keys){
		try{
			element.sendKeys(keys);
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//END OF KEYSEND BLOCK

	//START OF EXPLICIT WAIT BLOCK

	/**
	 * TESTED
	 * @param locator
	 * @return
	 */
	public boolean waitUntilPresent(String locator){
		return waitUntilPresent(getByElement(locator));
	}

	public boolean waitUntilPresent(By element){
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		return false;
	}

	public boolean waitUntilAllPresent(By element){
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
		return false;

	}
	//END OF EXPLICIT WAIT BLOCK
	/**
	 * TESTED
	 * @return
	 */
	public boolean quitDriver(){
		try{
			driver.quit();
		} catch (Exception e){
			return false;
		}
		return true;
	}
	
	//I know hard waits are bad, but the website is bad.
	public void goToSleep(int seconds){
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
