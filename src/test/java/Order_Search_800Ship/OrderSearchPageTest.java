package Order_Search_800Ship;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

@FixMethodOrder
public class OrderSearchPageTest {
	
	// WebDriver driver = new HtmlUnitDriver();
    private WebDriver driver = new FirefoxDriver();
    private String baseUrl = "http://demo.800ship.com:8950/login/login.cfm";
    private String userName = "rbt";
    private String password = "";
    private String textExpected ="Order Search ";
    private String invalidPassword = "lishan";
    private String errorMessageExpectedInvalidLogin = "Error: Invalid user name or password! Please try again.";
    private BasePage elem = new BasePage();
    
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

	
	@Ignore
	@Test
	public void testLoginValid_0001() {
		
		
	}
		
	
		
	
	
	 // This test verifies that user can expend/roll up Order Search form(top and bottom) by clicking Order Search
	 // on Pending Orders page
	@Ignore
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
		
		driver.findElement(By.xpath("/html/body/div[2]/header/h5/a")).click();
		
		assertEquals(driver.findElement(By.xpath("/html/body/div[2]/div[1]/blockquote/"
				+ "form/div/div[4]/div[2]/button[1]")).isDisplayed(), false);
		
		driver.findElement(By.xpath("/html/body/div[3]/header/h5/a")).click();
		
		assertEquals(driver.findElement(By.id("bot_btnShowOrders")).isDisplayed(), true);
		
		driver.findElement(By.xpath("/html/body/div[3]/header/h5/a")).click();
		
		assertEquals(driver.findElement(By.id("bot_btnShowOrders")).isDisplayed(), false);
	}
	
	    // This test verifies that user can expend/roll up Actions(top and bottom) by clicking link Actions
		// on Pending Orders page
		//@Ignore
		@Test
		public void testExpendActions_0006(){
			driver.findElement(By.id("UserName")).clear();
			driver.findElement(By.id("UserName")).sendKeys(userName);
			driver.findElement(By.id("pwd")).clear();
			driver.findElement(By.id("pwd")).sendKeys(password);
			driver.findElement(By.xpath("/html/body/form/div/div[2]/div[2]/div/button")).submit();
						
			assertEquals(textExpected, driver.findElement(By.xpath("/html/body/div[2]/header/h5/a")).getText());
						
			driver.findElement(By.xpath("/html/body/div[2]/form/div/div[1]/ul/li[1]/a")).click();
				
			assertEquals(driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[2]/button[2]")).isDisplayed(), true);
			
			driver.findElement(By.xpath("/html/body/div[2]/form/div/div[1]/ul/li[1]/a")).click();
			
			assertEquals(driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[2]/button[2]")).isDisplayed(), false);
			
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/ul/li[1]/a")).click();
			
			assertEquals(driver.findElement(By.xpath("/html/body/div[3]/div[3]/form/div[2]/button[2]")).isDisplayed(), true);
			
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/ul/li[1]/a")).click();
			
			assertEquals(driver.findElement(By.xpath("/html/body/div[3]/div[3]/form/div[2]/button[2]")).isDisplayed(), false);
		}
		
					
}
