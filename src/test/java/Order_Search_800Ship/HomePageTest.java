package Order_Search_800Ship;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder
public class HomePageTest {
	
	// WebDriver driver = new HtmlUnitDriver();
	private WebDriver driver = new FirefoxDriver();
    private String baseUrl = "http://demo.800ship.com:8950/login/login.cfm";
    private String userName = "rbt";
    private String password = "lishan*";
    private String textExpected ="Order Search ";
    private String invalidPassword = "lishan";
    private String errorMessageExpectedInvalidLogin = "Error: Invalid user name or password! Please try again.";
    private BasePage elem = new BasePage();
    private String expectedUrl = "http://demo.800ship.com:8950/wsn_v2/index.cfm?pnd=1&fuseaction=shipping11.home";

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
	public void test_1_000001() {
		elem.login(userName, password, driver, textExpected, expectedUrl);
			
	}
	
	//This test verifies that user can't login with invalid credentials(valid user name and invalid password.
	@Ignore
	@Test
	public void test_1_000002() {
		elem.ivalidLogin(userName, invalidPassword, driver, errorMessageExpectedInvalidLogin);
				
	}
	
	//This test verifies that user can login with valid credentials and logout
	//@Ignore
	@Test
	public void test_1_000003(){
		elem.login(userName, password, driver, textExpected, expectedUrl);
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/header/div[2]/ul/li[3]/a/span")));
		element.click();
		
		assertEquals(baseUrl, driver.getCurrentUrl());
	}
}
