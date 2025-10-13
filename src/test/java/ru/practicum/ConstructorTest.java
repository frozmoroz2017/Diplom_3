package ru.practicum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import ru.practicum.pages.MainPage;
import io.qameta.allure.junit4.DisplayName;
import static org.junit.Assert.assertEquals;

public class ConstructorTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverFactory.initDriver();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Проверка активации раздела 'Булки'")
    public void testBunsSectionActive() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.waitForPageLoad();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(mainPage.getBunsSectionLocator()));

        assertEquals("Должен быть активен раздел Булки", "Булки", mainPage.getActiveSectionText());
    }

    @Test
    @DisplayName("Проверка активации раздела 'Соусы'")
    public void testSaucesSectionActive() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.waitForPageLoad();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(mainPage.getSaucesSectionLocator()));

        assertEquals("Должен быть активен раздел Соусы", "Соусы", mainPage.getActiveSectionText());
    }

    @Test
    @DisplayName("Проверка активации раздела 'Начинки'")
    public void testFillingsSectionActive() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.waitForPageLoad();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(mainPage.getFillingsSectionLocator()));

        assertEquals("Должен быть активен раздел Начинки", "Начинки", mainPage.getActiveSectionText());
    }
}