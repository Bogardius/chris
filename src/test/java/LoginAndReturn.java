import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LoginAndReturn {

    public WebDriver driver;
    @Test(priority = 1)
        public void login () throws InterruptedException {
        //Provide the Chrome driver path to send the selenium requests to browser
        WebDriverManager.chromedriver().setup();
        //Launch browser
        WebDriver driver = new ChromeDriver();
        //maximize the browser
        driver.manage().window().maximize();
        //Implicit wait, wait for at least some time (10 sec) to identify an element,
        // if can't find the element with in 10 sec, throw exception
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("Launching Chrome browser..");
        driver.get("https://www.bol.com");
        driver.findElement(By.id("js-first-screen-accept-all-button")).click();
        System.out.println("Accepted all coockies");
        // verifying if we are logged in or not
        System.out.println("going to verify if we are logged out");
        WebElement inloggen = driver.findElement(By.linkText("Inloggen"));
        boolean flag = false;
        if (inloggen.isDisplayed()) {
            flag = true;
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

                System.out.println("Navigate to Klantenservice");
                WebElement klantenservice = driver.findElement(By.linkText("Klantenservice"));
                klantenservice.click();
                System.out.println("Navigate to Retouren&Annuleren");
                WebElement retouren = driver.findElement(By.linkText("Retouren & annuleren"));
                retouren.click();
                System.out.println("Start Retour");
                WebElement startretour = driver.findElement(By.linkText("Start een retour"));
                startretour.click();
                System.out.println("Check if we are on the right page, expected URL: https://www.bol.com/nl/account/login.html?redirectUrl=/nl/account/retourneren/aanmelden equal to:" + driver.getCurrentUrl());

                WebElement username = driver.findElement(By.id("login_email"));
                WebElement password = driver.findElement(By.id("login_password"));
                WebElement login = driver.findElement(By.name("submit"));
                String actualUrl2 = driver.getCurrentUrl();
                String expectedUrl2 = "https://www.bol.com/nl/account/login.html?redirectUrl=/nl/account/retourneren/aanmelden";
                Assert.assertEquals(expectedUrl2, actualUrl2);

            }

        }
