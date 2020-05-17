package first;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Activities extends Operations {

	//Since only the tile changes and no page change setting up things in a single page
	
	@FindBy(xpath = "//form[@class='hero__form']//child::input")
	 WebElement input;
	@FindBy(xpath = "//form[@class='hero__form']//child::button")
	WebElement goBTN;
	@FindBy(xpath = "//button[text()= 'Next']")
	WebElement nextBTN;
	@FindBy(xpath  = "//div[contains(@class,'rootWithErrorV')]/child::div/child::div")
	List<WebElement> items;
	@FindBy(xpath  = "//div[contains(@class,'rootBase')]/child::div/child::div")
	WebElement items1;
	@FindBy(xpath = "//div[text()='Tap']")
	private WebElement tap;
	@FindBy(xpath = "//div[text()='Replace']")
	private WebElement replace;
	@FindBy(xpath = "//div[text()='Leak in a pipe']")
	private WebElement leak;
	@FindBy(xpath = "//button[text()='Skip']")
	private WebElement skip;
	@FindBy(xpath = "//div[text()=\"I'm flexible\"]")
	private WebElement flexible;
	@FindAll({@FindBy (xpath = "//div[text()=\"Where would you like us to notify you about new quotes received on your request?\"]"),
			@FindBy(xpath = "//input[@placeholder='Email address']")})
	private WebElement notify;
	
	@FindBy(xpath = "//div[contains(@class,'title___')]")
	private WebElement questions;
	
	@FindBy(xpath = "//div[text()='Optional']")
	private WebElement optional;
	
	@FindBy(xpath = "//div[text()='Optional']/following::div[contains(@class,'inputContainer')]")
	private WebElement inputField;
	
	@FindBy(xpath = "//div[text()='On a specific date']")
	private WebElement specificDate;
	
	@FindBy(xpath = "//textarea")
	private WebElement textarea;
	
	@FindBy(xpath = "//button/i[contains(@class,'nextIcon')]")
	private WebElement nextIcon;
	
	@FindBy(xpath = "//select[@data-test='step_time']")
	private WebElement select;
	
	public void decorator() {
		
		this.driver = getDriver();
		PageFactory.initElements(driver, this);
	}
	
	public void subOne() {
	try {
		decorator();
		sendKeys(input,"Chennai");
		hardWait(500);
		clickelement(goBTN);
		waitForElement(nextBTN,10);
		clickelement(nextBTN);
		dynamicQuestions();
		if(waitForElement(notify,100)) {
				System.out.println("Done with assessment");}
	}catch(InterruptedException i) {
		i.printStackTrace();
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		getDriver().quit();
	}
	}
	
	
	// Sinces answers are given respect to questions answers are selected
	// incase if someother questions comes it selects the first item in the list
	// clicks next button 
	// if skip options is available then goes for SKIP first
	public void dynamicQuestions() {
		try {
			while(waitForElement(questions, 5) && !waitForElement(notify, 5)) {
			String question = TextReturn(questions);
			if(question != null )	
			{
				switch(question) {
			case "The problems are to do with which of the following things?":
				clickelement(tap);
				clickelement(nextBTN);
				break;
			case "What do you need done?":
				clickelement(replace);
				clickelement(nextBTN);
				break;
			case "What problem(s) do you have?":
				clickelement(leak);
				clickelement(nextBTN);
				break;
			case "Is there anything else that the Plumber needs to know?":
				clickelement(inputField);
				sendKeys(textarea, "Sample Anything else the Plumber should know?");
				clickelement(nextBTN);
			case "When do you require plumbing?":
				clickelement(specificDate);
				clickelement(nextBTN);
				dateTimePicker();
				break;
			default:
				if(waitForElement(skip, 10)) {
					clickelement(skip);
				}else {
					if(!waitForElement(notify, 5)) {
					clickelement(items1);
					clickelement(nextBTN);
					}
				}
					break;
			}	
			}
			}
			}catch(Exception e) {
				e.printStackTrace();
				getDriver().quit();
			}
		}
	
	//Data picker is used to select the date dynamically
	//if date is 30 or 31 then goes to next month and selects the date
	//concat the xpath with month and date
	public void dateTimePicker() throws InterruptedException {
		 	DateFormat dateFormat = new SimpleDateFormat("MM/dd");
	
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(new Date());
		    cal.add(Calendar.DATE, 2);
		    String newDate = dateFormat.format(cal.getTime());
		
		    int m = Integer.parseInt(newDate.substring(0, 2));
		    
		    String[] month = {"January","February","March","April","May","June","July",
		            "August","September","October","November","December"};

		    String mon = month[m-1];
		    String date = newDate.substring(3, newDate.length());
		    
		    if(newDate.contains("30") || newDate.contains("31")) {
		    	clickelement(nextIcon);	
		    }
		    else {
		    	//System.out.println("//div[contains(@class,'CalendarMonth CalendarMonth')]//button[contains(@class,'CalendarDay_button') and contains(@aria-label,'May 19,')]");
		    	getDriver().findElement(By.xpath("//div[contains(@class,'CalendarMonth CalendarMonth')]//button[contains(@class,'CalendarDay_button') and contains(@aria-label,'"+mon+ " "+date+",')]")).click();
		    	clickelement(nextBTN);
		    	Select drop = new Select(select);
		    	drop.selectByVisibleText("01:30");
		    	clickelement(nextBTN);
		    }
	}
	
}
