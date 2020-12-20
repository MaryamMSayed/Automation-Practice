package nextlevel.liveproject.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class GoogleSearchResultsPage {
    private EventFiringWebDriver driver;

    // Constructor
    public GoogleSearchResultsPage(EventFiringWebDriver driver) {
	this.driver = driver;
    }

    // Elements
//    private By google_search_result = By.xpath("(//h3[@class='LC20lb'])[1]");

    // Methods
    private WebElement getGoogleSearchResultIndex(String resultIndex) {
	return driver.findElement(By.xpath("(//h3[@class='LC20lb'])" + resultIndex));
    }

    public void assertOnPageTitle(String searchData) {
	assertTrue(driver.getTitle().contains(searchData));

    }

    public void assertOnSearchResult(String searchData, String resultIndex) {
	assertEquals(getGoogleSearchResultIndex(resultIndex).getText(), searchData);

    }

    public void clickOnSearchResult(String resultIndex) {
	getGoogleSearchResultIndex(resultIndex).click();
    }

}
