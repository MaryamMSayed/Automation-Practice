package liveproject.phptravels.tests.Booking;

import java.io.File;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import liveproject.phptravels.apis.PhpTravels_APIs;
import liveproject.phptravels.gui.pages.PhpTravels_BoatsDetails_Page;
import utils.Logger;
import utils.BrowserActions;
import utils.BrowserFactory;
import utils.PropertiesReader;
import utils.Spreadsheet;
import utils.BrowserFactory.BrowserType;
import utils.BrowserFactory.ExecutionType;

@Epic("PHPTRAVELS")
@Feature("GUI")
public class Gui_BookBoats_Test {
    WebDriver driver;
    Spreadsheet spreadSheet;
    PhpTravels_APIs apis;
    String phptravelsHomePageURL = PropertiesReader.getProperty("liveproject.properties", "phptravels.baseuri");

    Date date = new Date();
    String firstName, lastName, mobileNumber, email, password;
    String currentTime = date.getTime() + "";

    @BeforeClass
    public void setUp() {
//	spreadSheet = new Spreadsheet(
//		new File("src/test/resources/TestData/LiveProject_PhpTravels_BookBoats_TestData.xlsx"));
//	spreadSheet.switchToSheet("GUI");
	apis = new PhpTravels_APIs();
	driver = BrowserFactory.openBrowser(BrowserType.FROM_PROPERTIES, ExecutionType.FROM_PROPERTIES);
	BrowserActions.navigateToUrl(driver, phptravelsHomePageURL
		+ "/boats/sri-lanka/colombo/Speedboat-Bravo-410---2016-refit-2016-?date=01/01/2025&adults=2");
    }

    @Test(description = "Validating the booking function of the Boats")
    @Description("")
    @Story("Booking")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("focus-case-1539798")
    @Issue("bug-tracker#1")
    public void testingBoatsSearch() {
	firstName = "mah";
	lastName = "mah";
	mobileNumber = "12321321321";
	email = "mah" + currentTime + "@test.com";
	password = "12345678";
//sign up using api
	apis.userSignUp(firstName, lastName, mobileNumber, email, password);
	
	new PhpTravels_BoatsDetails_Page(driver)
		.dismissCookieBar()
		.clickOnBookNow();
	Assert.assertEquals("", "");

    }

    @AfterMethod
    public void AfterMethod(ITestResult result) {
	if (result.getStatus() == ITestResult.FAILURE) {
	    Logger.attachScreenshotInCaseOfFailure(driver);
	}
    }

    @AfterClass
    public void closingBrowser() {
	BrowserActions.closeAllOpenedBrowserWindows(driver);
    }
}
