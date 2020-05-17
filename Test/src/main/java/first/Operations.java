package first;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


// User performed operations and file reading goes here

public class Operations extends Major{
	FileInputStream fis = null;
    Properties prop = null;
    //dynamically get the file
    String fileName = System.getProperty("user.dir")+"/config.properties";
    
    public String readProp(String key) {
    	String output = null;
    try {
       fis = new FileInputStream(fileName);
       prop = new Properties();
       prop.load(fis);
       
       output = (String) prop.get(key);
       
    } catch(FileNotFoundException fnfe) {
       fnfe.printStackTrace();
    } catch(IOException ioe) {
       ioe.printStackTrace();
    } finally {
       try {
		fis.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }
	return output;
    }
    
    // During selecting the area of service - we need a wait for the drop down to come and then click
    // go button else - it affects the operations in the next page - since no explicit wait used a sleep
    // for 0.5 milli seconds
    public void hardWait(int Waittime) {
		try {
			Thread.sleep(Waittime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
    // Returns text of the question to get the right choices to make
	public String TextReturn(WebElement element) {
		
		String returnText = null;
		try {
			if(waitForElement(element,10)) {
				returnText = element.getText();
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return returnText;
		
	}
	
	// User defined sendkeys - waits for certains time - no explicit wait so handled by a custom method
	// waitForElement -- to wait and sendkeys
	public void sendKeys(WebElement element, String keys) throws InterruptedException {
		try {
		if(waitForElement(element,10)) {
			element.clear();
			element.sendKeys(keys);
		}
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}
	}
	
	
	// Clicking element
	public void clickelement(WebElement element) throws InterruptedException {
		try {
		if(waitForElement(element,10)) {
			element.click();
		}
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}
	}
	
	//No explicit wait - so writing this method to handle the wait
	//As per requirement the wait is handled here - user defines the time to wait
	public boolean waitForElement(WebElement element, int time) throws InterruptedException {
			boolean status = false;
			int i = 0;
			while (i < time) {
				try {
					try {
						if (element.isEnabled()) {
							status = true;
							break;
						}
					} catch (Exception e1) {
						if (element.isDisplayed()) {
							status = true;
							break;
						}
					}
				} catch (Exception e) {
					Thread.sleep(500);
					status = false;
				}
				i++;
			}
			return status;
	}
    
}

