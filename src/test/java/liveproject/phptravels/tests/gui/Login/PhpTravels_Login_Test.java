package liveproject.phptravels.tests.gui.Login;

import java.io.File;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import liveproject.phptravels.apis.APIs;
import liveproject.phptravels.gui.pages.PhpTravels_Home_Page;
import utils.Logger;
import utils.BrowserActions;
import utils.BrowserFactory;
import utils.PropertiesReader;
import utils.Spreadsheet;
import utils.BrowserFactory.BrowserType;

@Epic("Live Project")
@Feature("PHPTravels Login")
public class PhpTravels_Login_Test {
    WebDriver driver;
    Spreadsheet spreadSheet;
    APIs apis;
    String phptravelsHomePageURL = PropertiesReader.getProperty("liveproject.properties", "phptravels.home.url");
    Date date = new Date();
    
    String firstName, lastName, mobileNumber, email, password;
    String currentTime = date.getTime() + "";

    @BeforeClass
    public void setUp() {
	spreadSheet = new Spreadsheet(new File("src/test/resources/TestData/LiveProject_PhpTravels_Login_TestData.xlsx"));
	spreadSheet.switchToSheet("testsheet2");
	apis = new APIs();
    }
    
    @BeforeMethod
    public void beforeMethod() {
	driver = BrowserFactory.openBrowser(BrowserType.FROM_PROPERTIES, true);
	BrowserActions.navigateToUrl(driver, phptravelsHomePageURL);
    }

    @Test(description = "Valid User Login")
    @Description("When I login with an already signed up user, Then I should login successfully")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("focus-case-1539798")
    @Issue("bug-tracker#1")
    public void testingValidUserLogin() {
	firstName = spreadSheet.getCellData("FirstName", 2);
	lastName = spreadSheet.getCellData("LastName", 2);
	mobileNumber = spreadSheet.getCellData("Mobile Number", 2);
	email = spreadSheet.getCellData("Email", 2) + currentTime + "@test.com";
	Logger.logMessage("The mail registering is: " + email);
	password = spreadSheet.getCellData("Password", 2);
//register using api
	apis.userSignUp(firstName, lastName, mobileNumber, email, password);
	
	String hiMessage = new PhpTravels_Home_Page(driver)
		.navigateToLoginPage()
		.userLogin(email, password)
		.getHiMessage();
	Assert.assertEquals(hiMessage,  "Hi, " + firstName + " " + lastName);
    }
    
    @Test(description = "Invalid User Login")
    @Description("When I enter a not signed up user , Then I should get an error message ")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("focus-case-1539798")
    @Issue("bug-tracker#1")
    public void testingInvalidUserLogin() {
	email = spreadSheet.getCellData("Email", 3) + "@test.com";
	password = spreadSheet.getCellData("Password", 3);

	String alertMessage = new PhpTravels_Home_Page(driver)
		.navigateToLoginPage()
		.invalidUserLogin(email, password)
		.getAlertMessage();
	Assert.assertEquals(alertMessage, spreadSheet.getCellData("Expected Alert Message", 3));
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
	if (result.getStatus() == ITestResult.FAILURE) {
	    Logger.screenshotOnfailureGui(driver);
	}
	BrowserActions.closeAllWindows(driver);
    }
}
