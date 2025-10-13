package ru.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import ru.practicum.utils.EnvConfig;

public class MainPage {
    private final WebDriver driver;

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By bunsSection = By.xpath("//span[text()='Булки']/parent::div");
    private final By saucesSection = By.xpath("//span[text()='Соусы']/parent::div");
    private final By fillingsSection = By.xpath("//span[text()='Начинки']/parent::div");
    private final By activeSection = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span");
    private final By orderButton = By.xpath("//button[text()='Оформить заказ']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(EnvConfig.BASE_URL);
    }


    public By getBunsSectionLocator() { return bunsSection; }
    public By getSaucesSectionLocator() { return saucesSection; }
    public By getFillingsSectionLocator() { return fillingsSection; }
    public By getOrderButtonLocator() { return orderButton; }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    public String getActiveSectionText() {
        return driver.findElement(activeSection).getText();
    }

    public boolean isOrderButtonDisplayed() {
        return driver.findElements(orderButton).size() > 0;
    }

    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
    }


    public void waitForOrderButton() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(orderButton));
    }
}