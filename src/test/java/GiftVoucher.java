import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class GiftVoucher {

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
        System.out.println("Open the 'Winkelwagen'");
        driver.navigate().to("https://www.bol.com/nl/nl/basket");
        String text = driver.findElement(By.xpath("//div[contains(@class,'h-txt-center')]/h1")).getText();
        Assert.assertTrue(text.contains("Je winkelwagentje is leeg"));
        System.out.println("Winkelwagen is empty:" + text);

        //Click on the link Cadeaukaart
        WebElement garantie = driver.findElement(By.linkText("Cadeaukaart"));
        garantie.click();
        WebElement kadokaart = driver.findElement(By.xpath("//a[@href='/nl/nl/p/bol-com-cadeaubon-25-euro-voor-jou/9200000113167726/']"));
        kadokaart.click();
        System.out.println("Navigate directly, couldnt manage to get the dropdown working");
       /* driver.navigate().to("https://www.bol.com/nl/nl/p/bol-com-cadeaubon-25-euro-voor-jou/9200000113167726/?s2a=&bltgh=uBoCBLVQAUR3RQmdYopEHg.2_36_37.39.FeatureListItem#productTitle");*/
        WebElement dropdown = driver.findElement(By.xpath("//div[@class='feature-list__text' and @data-test='label']"));
        dropdown.click();
        WebElement winkelwagen = driver.findElement(By.linkText("In winkelwagen"));
        winkelwagen.click();
        System.out.println("Winkelwagen content");

        System.out.println("Click on Verder naar bestellen");
        WebElement verderbestellen = driver.findElement(By.linkText("Verder naar bestellen"));
        verderbestellen.click();

        System.out.println("Check if we have the right card, 25 euro voor jou");
        WebElement welkekaart = driver.findElement(By.xpath("//a[@href='/nl/nl/p/bol-com-cadeaubon-25-euro-voor-jou/9200000113167726/']"));

        System.out.println("Kaart is OK, lets delete it again");
        WebElement delete = driver.findElement(By.xpath("//button[@type='button' and @aria-label='delete-item']"));
        delete.click();

        String text2 = driver.findElement(By.xpath("//div[contains(@class,'h-txt-center')]/h1")).getText();
        Assert.assertTrue(text.contains("Je winkelwagentje is leeg"));
        System.out.println("Winkelwagen is empty:" + text2);


    }

}
