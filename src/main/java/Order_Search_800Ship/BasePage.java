package Order_Search_800Ship;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {

	public boolean isElementPresent(String locatorKey, WebDriver driver) {
		try {
			driver.findElement(By.xpath(locatorKey));
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}
	
	public void login(String userName, String password, WebDriver driver, String textExpected, String expectedUrl){
		driver.findElement(By.id("UserName")).clear();
		driver.findElement(By.id("UserName")).sendKeys(userName);
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys(password);
		driver.findElement(
				By.xpath("/html/body/form/div/div[2]/div[2]/div/button"))
				.submit();

		assertEquals(textExpected,driver.findElement(By.xpath("/html/body/div[2]/header/h5/a")).getText());
				
		assertEquals(expectedUrl, driver.getCurrentUrl());
		
	}
	
	public void ivalidLogin(String userName, String password, WebDriver driver, String errorMessageExpectedInvalidLogin){
		driver.findElement(By.id("UserName")).clear();
		driver.findElement(By.id("UserName")).sendKeys(userName);
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys(password);
		driver.findElement(
				By.xpath("/html/body/form/div/div[2]/div[2]/div/button"))
				.submit();

		assertEquals(errorMessageExpectedInvalidLogin, driver.findElement(By.xpath("/html/body/form/div/div[2]/div[1]/div[2]/b")).getText());
		
	}

}
