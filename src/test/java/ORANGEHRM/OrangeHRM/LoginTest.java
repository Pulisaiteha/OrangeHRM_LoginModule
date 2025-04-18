package ORANGEHRM.OrangeHRM;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import ORANGEHRM.OrangeHRM.ExcelUtil;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"); 
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) {
        driver.findElement(By.name("username")).sendKeys(username);      
        driver.findElement(By.name("password")).sendKeys(password);      
        driver.findElement(By.xpath("//button[@type='submit']")).click();           

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Time at Work']"))); 

        Assert.assertTrue(dashboard.isDisplayed(), "Dashboard is not displayed!");
    }

    @DataProvider(name = "loginData")
    public Object[][] getData() {
    	String filePath = getClass().getClassLoader().getResource("LoginTestData.xlsx").getPath();
        return ExcelUtil.getTestData(filePath, "Sheet1");
    }
    

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
