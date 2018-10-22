package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ProductPage {
    private WebDriver driver;
    private String productName;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.productName = getProductName();
    }

    public String getProductName() {
        WebElement product = new WebDriverWait(this.driver, 30).until(
                ExpectedConditions.presenceOfElementLocated(By.id("listing-page-cart"))
        );

        return product.findElement(By.tagName("h1")).getText();
    }

    public void addToCart() {
        Select selectDialog = hasVariations();
        if (selectDialog != null) {
            selectDialog.selectByIndex(1);

            new WebDriverWait(this.driver, 30, 1000).until(
                    (ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).
                            executeScript("return jQuery.active").equals(0l)
            );
        }

        WebElement addToCart = new WebDriverWait(this.driver, 30).until(
                ExpectedConditions.presenceOfElementLocated(By.className("add-to-cart-form"))
        );
        addToCart.submit();
    }

    public Select hasVariations() {
        Select variationDialog = null;
        try {
            variationDialog = new Select(this.driver.findElement(By.id("inventory-variation-select-0")));
        } catch (NotFoundException e) {}
        return variationDialog;
    }

}
