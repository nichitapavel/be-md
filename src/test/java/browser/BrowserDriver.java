package browser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;


public class BrowserDriver implements AutoCloseable {
    public WebDriver driver;

    public BrowserDriver() {
        String hub = System.getProperty("selenium-hub");
        ChromeOptions capabilities = new ChromeOptions()
                .addArguments("--start-maximized");

        try {
            this.driver = new RemoteWebDriver(new URL(hub), capabilities);
        } catch (MalformedURLException e) {
            System.out.println(e.getCause());
        }


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
