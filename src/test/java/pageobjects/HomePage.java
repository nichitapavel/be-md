package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void findRegisterForm() {
        this.driver.findElement(By.id("register")).click();
    }

    public void fillRegistrationForm(String email, String user, String password) {
        WebElement form = new WebDriverWait(this.driver, 30).until(
                ExpectedConditions.presenceOfElementLocated(By.id("join-neu-form"))
        );
        form.click();
        form.findElement(By.id("join_neu_email_field"))
                .sendKeys(email);
        form.findElement(By.id("join_neu_first_name_field"))
                .sendKeys(user);
        form.findElement(By.id("join_neu_password_field"))
                .sendKeys(password);
        form.submit();
    }

    public void findSingInForm() {
        this.driver.findElement(By.id("sign-in")).click();
    }

    public void signIn(String email, String password) {
        WebElement form = new WebDriverWait(this.driver, 30).until(
                ExpectedConditions.presenceOfElementLocated(By.id("join-neu-form"))
        );
        form.findElement(By.id("join_neu_email_field"))
                .sendKeys(email);
        form.findElement(By.id("join_neu_password_field"))
                .sendKeys(password);
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
