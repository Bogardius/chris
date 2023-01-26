import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;

import java.util.concurrent.TimeUnit;

public class GiftVoucher {

    public WebDriver driver;
    @Test(priority = 1)
    public void giftvoucher () throws InterruptedException {
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

            System.out.println("Start the test to order a voucher");
            System.out.println("Open the 'Winkelwagen'");
            driver.navigate().to("https://www.bol.com/nl/nl/basket");
            String text = driver.findElement(By.xpath("//div[contains(@class,'h-txt-center')]/h1")).getText();
            Assert.assertTrue(text.contains("Je winkelwagentje is leeg"));
            System.out.println("Winkelwagen is empty:" + text);

            //Click on the link Cadeaukaart
            WebElement garantie = driver.findElement(By.linkText("Cadeaukaart"));
            garantie.click();
            WebElement kadokaart = driver.findElement(By.xpath("//a[@href='/nl/nl/p/bol-com-cadeaubon-25-euro-bedankt/9200000114912813/']"));
            kadokaart.click();
            System.out.println("Going to select the correct dropdown and click on Voor jou");
            WebElement dropdown = driver.findElement(By.xpath("//div[@data-test='feature-group-dropdown' and contains(@data-bltgi,'2_36_37.39')]"));
            dropdown.click();
            WebElement clickon = driver.findElement(By.cssSelector("a[href='/nl/nl/p/bol-com-cadeaubon-25-euro-voor-jou/9200000113167726/?s2a=#productTitle'"));
            clickon.isDisplayed();
            clickon.click();
            System.out.println("Add the card to the winkelwagen");
            WebElement winkelwagen = driver.findElement(By.linkText("In winkelwagen"));
            winkelwagen.click();
            System.out.println("Winkelwagen content");

            System.out.println("Click on Verder naar bestellen");
            WebElement verderbestellen = driver.findElement(By.linkText("Verder naar bestellen"));
            verderbestellen.click();

            System.out.println("Check if we have the right card, 25 euro voor jou");
            WebElement welkekaart = driver.findElement(By.xpath("//a[@class='product-details__title']"));
            String kaartnaam = welkekaart.getText();
            System.out.println("Text is:"+welkekaart.getText());
            Assert.assertTrue(kaartnaam.contains("bol.com cadeaubon - 25 euro - Voor jou"));
            System.out.println("Card is OK, lets delete it again");
            WebElement delete = driver.findElement(By.xpath("//button[@type='button' and @aria-label='delete-item']"));
            delete.click();

            String text2 = driver.findElement(By.xpath("//div[contains(@class,'h-txt-center')]/h1")).getText();
            Assert.assertTrue(text2.contains("Je winkelwagentje is leeg"));
            System.out.println("Winkelwagen is empty:" + text2);

            System.out.println("Close the browser");
            driver.quit();
    }

}
