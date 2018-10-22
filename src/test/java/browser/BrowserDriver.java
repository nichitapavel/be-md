package browser;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class BrowserDriver implements AutoCloseable {
    public WebDriver driver;

    public BrowserDriver() {
        /*
        try {
            this.driver = new RemoteWebDriver(new URL("http://192.168.0.154:4444/wd/hub"), cap);
        } catch (MalformedURLException e) {
            System.out.println(e.getCause());
        }
        */

        /* Chrome */
        String exePath = "C:/Users/pavel/Desktop/selenium/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);
        ChromeOptions capabilities = new ChromeOptions()
                .addArguments("--start-maximized");
        this.driver = new ChromeDriver(capabilities);

        /* Firefox
        String exePath = "C:/Users/pavel/Desktop/selenium/geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", exePath);
        FirefoxOptions capabilities = new FirefoxOptions();
        this.driver = new FirefoxDriver();
        this.driver.manage().window().maximize();
        */

        //Cookie acceptCookies = new Cookie("p", "eyJnZHByX3RwIjoxLCJnZHByX3AiOjF9", "https://etsy.com",
        //null, null, true, true);
        //this.driver.manage().addCookie(acceptCookies);

        this.driver.get("https://www.etsy.com/");
        Set<Cookie> cookies = this.driver.manage().getCookies();

        boolean cookieExists = false;
        for (Cookie cook  : cookies)
            if (cook.getName().equals("p"))
                cookieExists = true;

        if (!cookieExists)
            this.driver.manage().addCookie(
                    new Cookie("p", "eyJnZHByX3RwIjoxLCJnZHByX3AiOjF9", ".etsy.com",
                            null, null, false, true)
            );
        this.driver.navigate().refresh();

        this.driver.findElement(By.className("banner-inner")).findElement(By.tagName("button")).click();
    }

    @Override
    public void close() {
        this.driver.quit();
        this.driver = null;
    }
}
