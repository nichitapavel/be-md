package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class SearchPage {
    private WebDriver driver;
    private List<WebElement> productsList;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void sortAscending() {
        WebElement sortButton = new WebDriverWait(this.driver, 30).until(
                ExpectedConditions.elementToBeClickable(By.id("sortby"))
        );
        sortButton.findElement(By.tagName("button")).click();
        new WebDriverWait(this.driver, 30).until(
                ExpectedConditions.elementToBeClickable(By.linkText("Lowest Price"))
        ).click();
        this.productsList = this.driver.findElements(By.className("block-grid-item"));
        removeAdProducts();
    }

    public void searchProduct(String product) {
        this.driver.get("https://www.etsy.com/search");
        WebElement searchBox = this.driver.findElement(By.id("search-query"));
        searchBox.sendKeys(product);
        searchBox.submit();

        new WebDriverWait(this.driver, 30, 1000).until(
                (ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).
                        executeScript("return document.readyState").
                        equals("complete")
        );

        new WebDriverWait(this.driver, 30, 1000).until(
                (ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).
                        executeScript("return jQuery.active").
                        equals(0l)
        );

        this.productsList = this.driver.findElements(By.className("block-grid-item"));
        removeAdProducts();
    }

    public void removeAdProducts() {
        Iterator<WebElement> iterator = this.productsList.iterator();
        while (iterator.hasNext()) {
            WebElement item = iterator.next();
            if (!(item.findElements(By.className("ad-indicator")).size() == 0)) {
                iterator.remove();
            }
        }
    }

    public boolean areItemsSortedAscending() {
        float last_price = 0.0f;
        Iterator<WebElement> iterator = this.productsList.iterator();
        while (iterator.hasNext()) {
            WebElement item = iterator.next();
            try {
                float price = Float.parseFloat(
                        item.findElement(By.className("currency-value")).getText()
                );
                if (last_price < price) {
                    return false;
                }
            } catch (NullPointerException e) {
                System.out.println(e.getCause());
            } catch (NumberFormatException e) {
                System.out.println(e.getCause());
            }
        }
        return true;
    }

    public WebElement chooseMostExpensiveProduct() {
        WebElement mostExpensiveProduct = null;
        float last_price = 0.0f;
        Iterator<WebElement> iterator = this.productsList.iterator();
        while (iterator.hasNext()) {
            WebElement product = iterator.next();
            try {
                float price = Float.parseFloat(
                        product.findElement(By.className("currency-value")).getText()
                );
                if (last_price < price) {
                    last_price = price;
                    mostExpensiveProduct = product;
                }
            } catch (NullPointerException e) {
                System.out.println(e.getCause());
            } catch (NumberFormatException e) {
                System.out.println(e.getCause());
            }
        }
        return mostExpensiveProduct;
    }

    public WebElement chooseAProduct() {
        int randomNumber = ThreadLocalRandom.current().nextInt(0, this.productsList.size());
        return this.productsList.get(randomNumber);
    }

    public void closePage() {
        this.driver.close();
    }

}
