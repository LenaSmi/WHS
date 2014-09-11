package Order_Search_800Ship;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


@FixMethodOrder
public class PendingOrdersPageTest {
	
	//private WebDriver driver;
	WebDriver driver = new FirefoxDriver();
	//private static ChromeDriverService service;
	//private static InternetExplorerDriverService service;
	
		  	
	private static String baseUrl = "http://demo.800ship.com:8950/login/login.cfm";
	private String expectedUrl = "http://demo.800ship.com:8950/wsn_v2/index.cfm?pnd=1&fuseaction=shipping11.home";
	private String userName = "rbt";
	private String password = "lishan*";
	private String textExpected = "Order Search ";
	static final int ROWSONPAGE = 10;
	private BasePage elem = new BasePage();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
	private Date date = new Date();
	private String newDate = dateFormat.format(date);
	private File scrFile;
	
	
	
	@Before
	public void setUp() throws IOException{
					
//		service = new ChromeDriverService.Builder().usingDriverExecutable(new File("C:/Chromedriver/chromedriver.exe"))
//        .usingAnyFreePort().build();
//        service.start();
//        driver = new RemoteWebDriver(service.getUrl(),
//                DesiredCapabilities.chrome());
		
//		service = new InternetExplorerDriverService.Builder().usingDriverExecutable
//				(new File("C:/IEDriverServer/IEDriverServer.exe")).usingAnyFreePort().build();
//        service.start();
//        driver = new RemoteWebDriver(service.getUrl(),
//              DesiredCapabilities.internetExplorer());
		
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		//service.stop();
	}

	// This test verifies that are 10 records in table are displayed when user
	// open the page
	//@Ignore
	@Test
	public void test_2_1_00001(){
		
	    elem.login(userName, password, driver, textExpected, expectedUrl);

		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[1]"));
		

		assertSame(lists.size(), ROWSONPAGE);

	}

	// This test verifies that all Check Boxes in all rows are checked when user
	// checks the Check Box in the header of result
	// table on Pending Orders page.
	//@Ignore
	@Test
	public void test_2_1_00002() throws IOException {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		driver.findElement(By.name("Select_All")).click();
		
		//take a screenshot
		scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//copy screenshot to c:\tmp\
		FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenshot" + newDate + ".png"));

		List<WebElement> lists = driver.findElements(By
				.name("Recipient_Package"));
		for (WebElement element : lists) {
			if (element.getAttribute("type").equals("checkbox")) {
				assertEquals(true, element.isSelected());
			}

		}

	}

	// This test verifies that some Check Box in row is checked/unchecked when
	// user checked/unchecked it
	//@Ignore
	@Test
	public void test_2_1_00003() throws IOException {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody/tr[6]/td[2]/input"))
				.click();
		
		//take a screenshot
		scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//copy screenshot to c:\tmp\
		FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenshot" + newDate + ".png"));

		assertEquals(true, driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table"
						+ "/tbody/tr[6]/td[2]/input")).isSelected());

		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody/tr[6]/td[2]/input"))
				.click();
		
		//take a screenshot
		scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//copy screenshot to c:\tmp\
		FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenshot" + newDate + ".png"));

		assertEquals(false, driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table"
						+ "/tbody/tr[6]/td[2]/input")).isSelected());

	}

	// Test verifies that result is sorting by Package column in ASC/DESC order
	// when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00004() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver.findElements(By
				.xpath("/html/body/div[2]/div[3]/form[2]/div/div"
						+ "/table/tbody//*/td[3]/a"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath(".//*[@id='CollapseTable_wrapper']/div[3]/table/thead/tr/th[3]")).click();
		
		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver.findElements(By
				.xpath("/html/body/div[2]/div[3]/form[2]/div/div"
						+ "/table/tbody//*/td[3]/a"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {

			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		
		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[3]"))
				.click();

		List<WebElement> lists2 = driver.findElements(By
				.xpath("/html/body/div[2]/div[3]/form[2]/div/div"
						+ "/table/tbody//*/td[3]/a"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		
	}

	// Test verifies that result is sorting by Recipient column in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00005() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[4]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[4]"))
				.click(); 

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[4]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {

			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[4]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[4]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

	}

	// Test verifies that result is sorting by Status column in ASC/DESC order
	// when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00006() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[5]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[5]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[5]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		// System.out.println("List value after sort ACS: " +
		// webList1.toString());
		// System.out.println("List value after sort ACS: " +
		// webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[5]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[5]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		// System.out.println("List value after sort DESC: " +
		// webList2.toString());
		// System.out.println("List value after sort DESC: " +
		// webList.toString());
	}

	// Test verifies that result is sorting by Allocated column in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00007() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[6]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[6]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[6]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		// System.out.println("List value after sort ACS: " +
		// webList1.toString());
		// System.out.println("List value after sort ACS: " +
		// webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[6]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[6]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		// System.out.println("List value after sort DESC: " +
		// webList2.toString());
		// System.out.println("List value after sort DESC: " +
		// webList.toString());
	}

	// Test verifies that result is sorting by Packed column in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00008() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[7]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[7]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[7]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		//System.out.println("List value after sort ACS: " + webList1.toString());
		//System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[7]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[7]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		//System.out.println("List value after sort DESC: " + webList2.toString());
		//System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by Seller column in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00009() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[8]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[8]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[8]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		//System.out.println("List value after sort ACS: " + webList1.toString());
		//System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[8]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[8]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		//System.out.println("List value after sort DESC: " + webList2.toString());
		//System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by ST column in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00010() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[9]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[9]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[9]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		//System.out.println("List value after sort ACS: " + webList1.toString());
		//System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[9]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[9]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		//System.out.println("List value after sort DESC: " + webList2.toString());
		//System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by Order# column in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00011() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[10]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[10]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[10]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		//System.out.println("List value after sort ACS: " + webList1.toString());
		//System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[10]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[10]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		//System.out.println("List value after sort DESC: " + webList2.toString());
		//System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by Order Date column in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00012() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[11]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[11]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[11]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		//System.out.println("List value after sort ACS: " + webList1.toString());
		//System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[11]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[11]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		//System.out.println("List value after sort DESC: " + webList2.toString());
		//System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by RS Date column in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00013() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[12]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[12]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[12]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		//System.out.println("List value after sort ACS: " + webList1.toString());
		//System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[12]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[12]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		//System.out.println("List value after sort DESC: " + webList2.toString());
		//System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by column CC in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00014() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[13]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[13]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[13]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		//System.out.println("List value after sort ACS: " + webList1.toString());
		//System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[13]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[13]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		//System.out.println("List value after sort DESC: " + webList2.toString());
		//System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by column Order Type in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00015() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[14]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[14]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[14]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		//System.out.println("List value after sort ACS: " + webList1.toString());
		//System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[14]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[14]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		//System.out.println("List value after sort DESC: " + webList2.toString());
		//System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by column Shipment Type in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00016() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[15]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[15]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[15]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		//System.out.println("List value after sort ACS: " + webList1.toString());
		//System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[15]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[15]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		//System.out.println("List value after sort DESC: " + webList2.toString());
		//System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by column Serv in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00017() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[16]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[16]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[16]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

		//System.out.println("List value after sort ACS: " + webList1.toString());
		//System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[16]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[16]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

		//System.out.println("List value after sort DESC: " + webList2.toString());
		//System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by column Addr in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00018() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[17]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[17]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[17]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

//		System.out.println("List value after sort ACS: " + webList1.toString());
//		System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[17]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[17]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

//		System.out
//				.println("List value after sort DESC: " + webList2.toString());
//		System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by column Mfst Loc in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00019() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[18]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[18]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[18]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

//		System.out.println("List value after sort ACS: " + webList1.toString());
//		System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[18]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[18]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

//		System.out
//				.println("List value after sort DESC: " + webList2.toString());
//		System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by column Depl Loc in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00020() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[19]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[19]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[19]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

//		System.out.println("List value after sort ACS: " + webList1.toString());
//		System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[19]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[19]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

//		System.out
//				.println("List value after sort DESC: " + webList2.toString());
//		System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by column Track # in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00021() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[20]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[20]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[20]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

//		System.out.println("List value after sort ACS: " + webList1.toString());
//		System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[20]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[20]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

//		System.out
//				.println("List value after sort DESC: " + webList2.toString());
//		System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by column Spec Instr in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00022() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[21]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[21]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[21]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

//		System.out.println("List value after sort ACS: " + webList1.toString());
//		System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[21]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[21]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

//		System.out
//				.println("List value after sort DESC: " + webList2.toString());
//		System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that result is sorting by column Delv Instr in ASC/DESC
	// order when user clicks the header of column.
	//@Ignore
	@Test
	public void test_2_1_00023() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		// find all elements in column and add them to List<WebElement>
		List<WebElement> lists = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[22]"));

		List<String> webList = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists) {
			webList.add(element.getText().toLowerCase());
		}

		// sort ArrayList
		Collections.sort(webList);
		// Click on name of column to sort column in ASC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[22]"))
				.click();

		// find all elements in sorted column and add them to List<WebElement>
		List<WebElement> lists1 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[22]"));

		List<String> webList1 = new ArrayList<String>();
		// Transfer List<WebElement> into ArrayList
		for (WebElement element : lists1) {
			webList1.add(element.getText().toLowerCase());
		}
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList1.get(i)));
		}

//		System.out.println("List value after sort ACS: " + webList1.toString());
//		System.out.println("List value after sort ACS: " + webList.toString());

		// Click on name of column to sort column in DESC order
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/div[3]/table/thead/tr/th[22]"))
				.click();

		List<WebElement> lists2 = driver
				.findElements(By
						.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody//*/td[22]"));
		List<String> webList2 = new ArrayList<String>();
		for (WebElement element : lists2) {
			webList2.add(element.getText().toLowerCase());
		}
		// Reverse ArrayList
		Collections.reverse(webList);
		// assert that elements in sorted column are in the same order like in
		// sorted list
		for (int i = 0; i < webList.size(); i++) {
			assertTrue(webList.get(i).equals(webList2.get(i)));
		}

//		System.out
//				.println("List value after sort DESC: " + webList2.toString());
//		System.out.println("List value after sort DESC: " + webList.toString());
	}

	// Test verifies that when use checked/unchecked check box in right corner
	// over a result table, package details expend/roll up
	//@Ignore
	@Test
	public void test_2_1_00024() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		driver.findElement(By.id("Show_Package_Detail_top")).click();

		assertTrue(
				"element is not displayed",
				driver.findElement(
						By.xpath("/html/body/div[2]/div[3]/form[2]/div/div/table/tbody/"
								+ "tr[2]/td/table/tbody/tr[1]/td[1]/b"))
						.isDisplayed());

		driver.findElement(By.id("Show_Package_Detail_top")).click();

		//BasePage elem1 = new BasePage();

		assertFalse("element is displayed", elem.isElementPresent(
				"/html/body/div[2]/div[3]/form[2]/div/div/table/tbody/"
						+ "tr[2]/td/table/tbody/tr[1]/td[1]/b", driver));

	}

	// Test verifies that when user checked/unchecked check box Show Package
	// details on the top, the check box Show Package on the bottom is
	// checked/unchecked and vice versa
	//@Ignore
	@Test
	public void test_2_1_00025() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		driver.findElement(By.id("Show_Package_Detail_top")).click();

		assertTrue("check box is not checked",
				driver.findElement(By.id("Show_Package_Detail_bot"))
						.isSelected());

		driver.findElement(By.id("Show_Package_Detail_top")).click();

		assertFalse("check box is checked",
				driver.findElement(By.id("Show_Package_Detail_bot"))
						.isSelected());

		driver.findElement(By.id("Show_Package_Detail_bot")).click();

		assertTrue("check box is not checked",
				driver.findElement(By.id("Show_Package_Detail_top"))
						.isSelected());

		driver.findElement(By.id("Show_Package_Detail_bot")).click();

		assertFalse("check box is checked",
				driver.findElement(By.id("Show_Package_Detail_top"))
						.isSelected());

	}

	// This test verifies that user can expend/roll up Order Search form(top and
	// bottom) by clicking Order Search on Pending Orders page
	//@Ignore
	@Test
	public void test_2_1_00026() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		driver.findElement(By.xpath("/html/body/div[2]/header/h5/a")).click();

		assertEquals(driver.findElement(
				By.xpath("/html/body/div[2]/div[1]/blockquote/"
						+ "form/div/div[4]/div[2]/button[1]")).isDisplayed(),
				true);

		driver.findElement(By.xpath("/html/body/div[2]/header/h5/a")).click();

		assertEquals(driver.findElement(
				By.xpath("/html/body/div[2]/div[1]/blockquote/"
						+ "form/div/div[4]/div[2]/button[1]")).isDisplayed(),
				false);

		driver.findElement(By.xpath("/html/body/div[3]/header/h5/a")).click();

		assertEquals(driver.findElement(By.id("bot_btnShowOrders"))
				.isDisplayed(), true);

		driver.findElement(By.xpath("/html/body/div[3]/header/h5/a")).click();

		assertEquals(driver.findElement(By.id("bot_btnShowOrders"))
				.isDisplayed(), false);
	}

	// This test verifies that user can expend/roll up Actions(top and bottom)
	// by clicking link Actions on Pending Orders page
	//@Ignore
	@Test
	public void test_2_1_00027() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		driver.findElement(
				By.xpath("/html/body/div[2]/form/div/div[1]/ul/li[1]/a"))
				.click();

		assertEquals(
				driver.findElement(
						By.xpath("/html/body/div[2]/div[2]/form/div[2]/button[2]"))
						.isDisplayed(), true);

		driver.findElement(
				By.xpath("/html/body/div[2]/form/div/div[1]/ul/li[1]/a"))
				.click();

		assertEquals(
				driver.findElement(
						By.xpath("/html/body/div[2]/div[2]/form/div[2]/button[2]"))
						.isDisplayed(), false);

		driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/ul/li[1]/a"))
				.click();

		assertEquals(
				driver.findElement(
						By.xpath("/html/body/div[3]/div[3]/form/div[2]/button[2]"))
						.isDisplayed(), true);

		driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/ul/li[1]/a"))
				.click();

		assertEquals(
				driver.findElement(
						By.xpath("/html/body/div[3]/div[3]/form/div[2]/button[2]"))
						.isDisplayed(), false);
	}

	// This test verifies that every page has 10 rows except of last page, which
	// has rest of rows
	//@Ignore
	@Test
	public void test_2_1_00028() {
		elem.login(userName, password, driver, textExpected, expectedUrl);

		if (driver
				.findElement(
						By.xpath(".//*[@id='CollapseTable_paginate']/span/a[2]"))
				.isDisplayed() == true) {
			driver.findElement(
					By.xpath(".//*[@id='CollapseTable_paginate']/span/a[2]"))
					.click();
			List<WebElement> listsOfRows = driver.findElements(By
					.xpath("/html/body/div[2]/div[3]/"
							+ "form[2]/div/div/table/tbody//*/td[1]"));
			int numberOfRows = listsOfRows.size();

			assertSame("number is not same", numberOfRows, ROWSONPAGE);
			assertEquals(driver.findElement(
					By.xpath("/html/body/div[2]/div[3]/form[2]/"
							+ "div/div/table/tbody/tr[1]/td[1]")).getText(),
					String.valueOf(ROWSONPAGE + 1));
			assertEquals(driver.findElement(
					By.xpath("/html/body/div[2]/div[3]/form[2]/"
							+ "div/div/table/tbody/tr[10]/td[1]")).getText(),
					String.valueOf(ROWSONPAGE * 2));

		}
	}

	// This test verifies that Next link redirects to the next page
	//@Ignore
	@Test
	public void test_2_1_00029() throws IOException{
		elem.login(userName, password, driver, textExpected, expectedUrl);

		if (driver.findElement(By.id("CollapseTable_next")).isDisplayed() == true) {
			driver.findElement(By.id("CollapseTable_next")).click();
			assertSame("that is not Next page", ROWSONPAGE + 1,
					Integer.parseInt(driver.findElement(
							By.xpath(".//*[@id='CollapseTable']/tbody/tr[1]/td[1]")).getText()));
		}
	}

	// This test verifies that Previous link redirects to the Previous page
	//@Ignore
	@Test
	public void test_2_1_00030() throws IOException {
		elem.login(userName, password, driver, textExpected, expectedUrl);
		
		if (driver.findElement(By.id("CollapseTable_next")).isDisplayed() == true) {
			driver.findElement(By.id("CollapseTable_next")).click();
			
			//take a screenshot
			scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			//copy screenshot to c:\tmp\
			FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenshot" + newDate + ".png"));
			
			assertSame("that is not Next page", ROWSONPAGE + 1,
					Integer.parseInt(driver.findElement(
							By.xpath(".//*[@id='CollapseTable']/tbody/tr[1]/td[1]")).getText()));
			driver.findElement(By.id("CollapseTable_previous")).click();
			assertSame("that is not Previous page", ROWSONPAGE,
					Integer.parseInt(driver.findElement(
							By.xpath(".//*[@id='CollapseTable']/tbody/tr[10]/td[1]"))
							.getText()));
									
		}
			
	}

}
