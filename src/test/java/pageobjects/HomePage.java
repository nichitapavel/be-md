package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void findRegisterForm() {
        this.driver.findElement(By.id("register")).click();
    }

    public void fillRegistrationForm() {
        WebElement form = new WebDriverWait(this.driver, 30).until(
                ExpectedConditions.presenceOfElementLocated(By.id("join-neu-form"))
        );
        form.click();
        form.findElement(By.id("join_neu_email_field"))
                .sendKeys("fd7103a0c0b4fd6@gmail.com");
        form.findElement(By.id("join_neu_first_name_field"))
                .sendKeys("alfonso");
        form.findElement(By.id("join_neu_password_field"))
                .sendKeys("fd7103a0c0b4fd6");
        form.submit();
    }

    public void findSingInForm() {
        this.driver.findElement(By.id("sign-in")).click();
    }

    public void signIn() {
        WebElement form = new WebDriverWait(this.driver, 30).until(
                ExpectedConditions.presenceOfElementLocated(By.id("join-neu-form"))
        );
        //form.click();
        form.findElement(By.id("join_neu_email_field"))
                .sendKeys("LpGB0tJJraJCSjlJ6Yk5OalElk@gmail.com");
        form.findElement(By.id("join_neu_password_field"))
                .sendKeys("LpGB0tJJraJCSjlJ6Yk5OalElk");
        form.submit();

    }

    public boolean isLoggedIn(String user) {
        Wait wait = new WebDriverWait(this.driver, 30);
        WebElement profile = (WebElement) wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("You")));
        profile.click();
        WebElement profileName = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.className("name")));

        return profileName.getText().equals(user);
    }

}
