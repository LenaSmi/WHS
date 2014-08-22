package Order_Search_800Ship;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Order_search_page {
	
	// WebDriver driver = new HtmlUnitDriver();
    WebDriver driver = new FirefoxDriver();
    String baseUrl = "http://demo.800ship.com:8950/login/login.cfm";
    String userName = "rbt";
    String password = "";
    String textExpected ="Order Search ";
    String invalidPassword = "lishan";
    String errorMessageExpectedInvalidLogin = "Error: Invalid user name or password! Please try again.";
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	//This test verifies that user can login with valid credentials.
	@Ignore
	@Test
	public void testLoginValid_0001() {
		driver.findElement(By.id("UserName")).clear();
		driver.findElement(By.id("UserName")).sendKeys(userName);
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys(password);
		driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/div/button")).submit();
		
		assertEquals(textExpected, driver.findElement(By.xpath("/html/body/div[2]/header/h5/a")).getText());
		
	}
	
	//This test verifies that user can't login with invalid credentials(valid user name and invalid password.
	@Ignore
	@Test
	public void testLoginInvalid_0002() {
		driver.findElement(By.id("UserName")).clear();
		driver.findElement(By.id("UserName")).sendKeys(userName);
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys(invalidPassword);
		driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/div/button")).submit();
			
		assertEquals(errorMessageExpectedInvalidLogin, driver.findElement(By.xpath("/html/body/form/div/div[2]/div[1]/div[2]/b")).getText());
			
	}
	
	
	
	//This test verifies that user can login with valid credentials and logout
	@Ignore
	@Test
	public void testLogout_0003(){
		driver.findElement(By.id("UserName")).clear();
		driver.findElement(By.id("UserName")).sendKeys(userName);
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys(password);
		driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/div/button")).submit();
		
		assertEquals(textExpected, driver.findElement(By.xpath("/html/body/div[2]/header/h5/a")).getText());
		
		driver.findElement(By.xpath("/html/body/header/div[2]/ul/li[3]/a/span")).click();
		
		
		
	}
	
	// This test verifies that all Check Boxes in all rows are checked when user checks the Check Box in the header of result 
	// table on Pending Orders page.
	@Ignore
	@Test
	public void testChangeAllCheckBoxValue_0004(){
		driver.findElement(By.id("UserName")).clear();
		driver.findElement(By.id("UserName")).sendKeys(userName);
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys(password);
		driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/div/button")).submit();
		
		assertEquals(textExpected, driver.findElement(By.xpath("/html/body/div[2]/header/h5/a")).getText());
				
		driver.findElement(By.name("Select_All")).click();
		
		List<WebElement> lists = driver.findElements(By.name("Recipient_Package"));
		    for(WebElement element: lists){
		    	if(element.getAttribute("type").equals("checkbox")){
		    	assertEquals(true, element.isSelected());
		    	}
		        
		    }
		    
		}
	
	 // This test verifies that user can expend Order Search form by clicking Order Search on Pending Orders page
	 //@Ignore
	@Test
	public void testExpendOrderSearchForm_0005(){
		driver.findElement(By.id("UserName")).clear();
		driver.findElement(By.id("UserName")).sendKeys(userName);
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys(password);
		driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/div/button")).submit();
					
		assertEquals(textExpected, driver.findElement(By.xpath("/html/body/div[2]/header/h5/a")).getText());
					
		driver.findElement(By.xpath("/html/body/div[2]/header/h5/a")).click();
			
		assertEquals(driver.findElement(By.xpath("/html/body/div[2]/div[1]/blockquote/"
				+ "form/div/div[4]/div[2]/button[1]")).isDisplayed(), true);
			    
	}
}
