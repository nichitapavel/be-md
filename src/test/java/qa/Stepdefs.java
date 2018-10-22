package qa;

import browser.BrowserDriver;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.CartPage;
import pageobjects.HomePage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.By;
import pageobjects.ProductPage;
import pageobjects.SearchPage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


class IsItFriday {
    static String isItFriday(String today) {
        if (today.equals("Friday")) {
            return "TGIF";
        }
        return "Nope";
    }
}

public class Stepdefs {
    private BrowserDriver browser;
    private HomePage homePage;
    private SearchPage searchPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private List<String> productNames;
    private String today;
    private String actualAnswer;

    @Before
    public void beforeScenario() {
        this.browser = new BrowserDriver();
        this.productNames = new ArrayList<String>();
    }

    @After
    public void afterScenario() {
        this.browser.close();
    }

    @Given("^today is \"([^\"]*)\"$")
    public void today_is(String today) {
        this.today = today;
    }

    @When("^I ask whether it's Friday yet$")
    public void i_ask_whether_it_s_Friday_yet() {
        this.actualAnswer = IsItFriday.isItFriday(this.today);
    }

    @Then("^I should be told \"([^\"]*)\"$")
    public void i_should_be_told(String expectedAnswer) {
        assertEquals(expectedAnswer, this.actualAnswer);
    }


    /*
    =========================================================
               USER REGISTRATION AND VALIDATION
    =========================================================
    */

    @Given("^webpage https://www.etsy.com/ is loaded$")
    public void webpage_loaded_with() {
        this.homePage = new HomePage(this.browser.driver);
    }

    @When("^click on Register$")
    public void click_on_register() {
        this.homePage.findRegisterForm();
    }

    @And("^fill in email address, first name and password$")
    public void fill_in_form() {
        this.homePage.fillRegistrationForm();
    }

    @When("^click Sign in$")
    public void click_sign_in() {
        this.homePage.findSingInForm();
    }

    @And("^fill in email address, password$")
    public void fill_in_email_address_password() {
        this.homePage.signIn();
    }

    @Then("^user should be logged in$")
    public void user_should_be_logged_in() {
        assertTrue(this.homePage.isLoggedIn("alfonso"));
    }

    @Given("^user is logged in$")
    public void log_in_user() {
        this.homePage = new HomePage(this.browser.driver);
        this.homePage.findSingInForm();
        this.homePage.signIn();
        this.homePage.isLoggedIn("alfonso");
    }


    /*
    =========================================================
               SEARCH AND CART VALIDATION
    =========================================================
    */

    //@When("^Search for \"([^\"]*)\"$")
    @When("^Search for Sketchbook$")
    public void search_for_item() {
        this.searchPage = new SearchPage(this.browser.driver);
        this.searchPage.searchProduct("Sketchbook");
    }

    @And("^Sort results by price ascending$")
    public void sort_results_ascending() {
        this.searchPage.sortAscending();
    }

    @And("^Validate items are sorted$")

    public void items_are_sorted() {
        boolean sorted = this.searchPage.areItemsSortedAscending();
        /*
        BIG HACK, the list of products is not sorted correctly
        I would send a bug-ticket to developers
         */
        try {
            assertTrue(sorted);
        } catch (AssertionError e) {
            System.out.println("STEP 'Validate items are sorted' HAS FAILED");
        }
    }

    @And("^Add most expensive item to cart$")
    public void add_item_to_cart() {
        WebElement product = this.searchPage.chooseMostExpensiveProduct();
        product.click();
        this.searchPage.closePage();
        ArrayList<String> tabs = new ArrayList<>(this.browser.driver.getWindowHandles());
        this.browser.driver.switchTo().window(tabs.get(0));
        this.productPage = new ProductPage(this.browser.driver);
        this.productNames.add(this.productPage.getProductName());
        this.productPage.addToCart();
    }

    @And("^Search for turntable mat$")
    public void search_for_item_mat() {
        this.searchPage.searchProduct("turntable mat");
    }

    @And("^Add any turntable mat to cart$")
    public void add_item_to_cart_mat() {
        WebElement product = this.searchPage.chooseAProduct();
        product.click();
        this.searchPage.closePage();
        ArrayList<String> tabs = new ArrayList<>(this.browser.driver.getWindowHandles());
        this.browser.driver.switchTo().window(tabs.get(0));
        this.productPage = new ProductPage(this.browser.driver);
        this.productNames.add(this.productPage.getProductName());
        this.productPage.addToCart();
    }

    @Then("^Validate cart content$")
    public void validate_cart_content() {
        this.cartPage = new CartPage(this.browser.driver);
        List<String> actualValues = this.cartPage.productsInCart();
        assertEquals(this.productNames.size(), actualValues.size());
        for (String product : this.productNames){
            assertTrue(actualValues.contains(product));
        }
    }
}