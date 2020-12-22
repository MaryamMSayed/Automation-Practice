package engine;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {
    private static WebDriver driver;

    public enum BrowserType {
	MOZILLA_FIREFOX("Mozilla Firefox"), GOOGLE_CHROME("Google Chrome"), FROM_PROPERTIES("");

	private String value;

	BrowserType(String type) {
	    this.value = type;
	}

	protected String getValue() {
	    return value;
	}
    }

    public static WebDriver openBrowser(BrowserType browserType) {

	switch (browserType) {
	case GOOGLE_CHROME:
	    System.out.println("Opening Browser: " + browserType.value);
	    WebDriverManager.chromedriver().setup();
	    driver = new ChromeDriver();
	    driver.manage().window().maximize();
//	    private ChromeOptions getOptions() {
//		ChromeOptions options = new ChromeOptions();
////		options.addArguments("disable--infobars");
//		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
////		options.setHeadless(true);
//		return options;
//	    }
	    break;

	case MOZILLA_FIREFOX:
	    System.out.println("Opening Browser: " + browserType.value);
	    WebDriverManager.firefoxdriver().setup();
	    driver = new FirefoxDriver();
	    break;

	case FROM_PROPERTIES:

	}

	return driver;
    }
}