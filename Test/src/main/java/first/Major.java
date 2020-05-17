package first;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Major {

	static WebDriver driver;
	
	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		Major.driver = driver;
	}

	public static void main(String[] args) throws InterruptedException {
		
		//In Future is required to set up on firefox or safari - this method can be moved to 
		//operation class to set the driver based on needs

		Operations op = new Operations();
		//Scripted this code in my mac mini so no .exe file 
		//changed the location with System.getProperty("user.dir") so it can work in any machine
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+op.readProp("ChromeDriver"));
		setDriver(new ChromeDriver());
		
		getDriver().get(op.readProp("URL"));
		
		Activities maj = new Activities();
		maj.subOne();
		getDriver().quit();
	}
	
	
}
