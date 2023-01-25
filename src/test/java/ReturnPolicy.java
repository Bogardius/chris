import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ReturnPolicy {

    public WebDriver driver;
    @Test(priority = 1)
    public void returnpolicy () throws InterruptedException {
        //Provide the Chrome driver path to send the selenium requests to browser
        WebDriverManager.chromedriver().setup();
        System.out.println("Launching Chrome browser..");
        WebDriver driver = new ChromeDriver();
        System.out.println("Maximize Chrome browser..");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.bol.com");
        driver.findElement(By.id("js-first-screen-accept-all-button")).click();
        System.out.println("Accepted all coockies");
        System.out.println("going to verify if we are logged out");
        WebElement inloggen = driver.findElement(By.linkText("Inloggen"));
            if (inloggen.isDisplayed()) {
                System.out.println("Not logged in already");
            }
            else {
                System.out.println("We are logged in, so we need to logout first");
                driver.navigate().to("https://www.bol.com/nl/rnwy/account/overzicht");
                driver.navigate().to("https://www.bol.com/nl/rnwy/logout.html");
                String actualUrl = driver.getCurrentUrl();
                String expectedUrl = "https://www.bol.com/nl/account/login.html?redirectUrl=/nl/rnwy/account/overzicht";
                Assert.assertEquals(actualUrl, expectedUrl);
                System.out.println("Now we are logged out, current URL is" + driver.getCurrentUrl());
            }
            String actualUrl = driver.getCurrentUrl();
            String expectedUrl = "https://www.bol.com/nl/nl/";
            Assert.assertEquals(actualUrl, expectedUrl);

            System.out.println("Start the test to check if we need to login first before start a return");
            System.out.println("Navigate to Klantenservice");
            WebElement klantenservice = driver.findElement(By.linkText("Klantenservice"));
            klantenservice.click();

            System.out.println("Navigate to Garantie&Reparatie");
            WebElement garantie = driver.findElement(By.linkText("Garantie & reparatie"));
            garantie.click();

            System.out.println("Check if there is: Artikel buiten retourtermijn");
            WebElement retourtermijn=driver.findElement(By.xpath("//strong[contains(text(),'Artikel buiten')]"));
            String actualretour = retourtermijn.getText();
            Assert.assertTrue(actualretour.contains("Artikel buiten retourtermijn"));
            System.out.println("Expected retourtermijn is correct: Artikel buiten retourtermijn same as:"+ retourtermijn.getText());

            System.out.println("Naar bestellingen");
            WebElement bestellingen = driver.findElement(By.linkText("Naar je bestellingen"));
            bestellingen.click();

            WebElement loginscreen = driver.findElement(By.linkText("Inloggen"));
            String actualtext = loginscreen.getText();
            Assert.assertTrue(actualtext.contains("Inloggen"));
            System.out.println("Check if we see the login :" + loginscreen.getText());

            System.out.println("Close the browser");
            driver.quit();
        }

    }
