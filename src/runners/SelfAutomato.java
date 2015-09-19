package runners;

import selenium.utils.Automato;

/**
 * TODO: Fill with specialized methods and data (Currently in TestRunner)
 * @author Stefan Kraus
 *
 */
public class SelfAutomato extends Automato {

	public static final String SEARCH_BAR_XPATH_LOCATOR = "xpath='/html/body/div[4]/table/tbody/tr/td/form/table/tbody/tr/td[1]/input'";
	public static final String FOOD_SEARCH_RESULTS_LOCATOR = "class='list'";
	
	
	public SelfAutomato(String name) {
		super(name);
	}

}
