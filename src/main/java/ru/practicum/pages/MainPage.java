package ru.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import ru.practicum.utils.EnvConfig;
import io.qameta.allure.Step;

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

    @Step("Открытие главной страницы")
    public void open() {
        driver.get(EnvConfig.BASE_URL);
    }

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Клик по кнопке 'Личный Кабинет'")
    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Получение текста активного раздела")
    public String getActiveSectionText() {
        return driver.findElement(activeSection).getText();
    }

    @Step("Проверка отображения кнопки 'Оформить заказ'")
    public boolean isOrderButtonDisplayed() {
        return driver.findElements(orderButton).size() > 0;
    }

    @Step("Клик по разделу 'Булки'")
    public void clickBunsSection() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(bunsSection));
        driver.findElement(bunsSection).click();
    }

    @Step("Клик по разделу 'Соусы'")
    public void clickSaucesSection() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(saucesSection));
        driver.findElement(saucesSection).click();
    }

    @Step("Клик по разделу 'Начинки'")
    public void clickFillingsSection() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(fillingsSection));
        driver.findElement(fillingsSection).click();
    }

    @Step("Ожидание загрузки главной страницы")
    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
    }

    @Step("Ожидание отображения кнопки 'Оформить заказ'")
    public void waitForOrderButton() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(orderButton));
    }

    @Step("Ожидание смены активного раздела на: {expectedSection}")
    public void waitForSectionChange(String expectedSection) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.textToBe(activeSection, expectedSection));
    }
}