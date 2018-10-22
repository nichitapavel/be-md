package pageobjects;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class CartPage {
    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.driver.get("https://www.etsy.com/cart");
    }

    public List<String> productsInCart() {
        List<String> productsName = new ArrayList<>();
        WebElement productsSection = this.driver.findElement(By.id("multi-shop-cart-list"));
        List<WebElement> productsInCart = productsSection.findElements(By.className("cart-listing"));
        for (WebElement product : productsInCart) {
            productsName.add(
                    product.findElement(By.tagName("p")).getText()
            );
        }

        return productsName;
    }

}
