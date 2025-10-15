package ru.practicum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
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
    @DisplayName("Проверка активации раздела 'Булки' по умолчанию")
    public void testBunsSectionActiveByDefault() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.waitForPageLoad();


        assertEquals("Должен быть активен раздел Булки по умолчанию", "Булки", mainPage.getActiveSectionText());
    }

    @Test
    @DisplayName("Проверка активации раздела 'Соусы'")
    public void testSaucesSectionActive() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.waitForPageLoad();


        mainPage.clickSaucesSection();


        mainPage.waitForSectionChange("Соусы");

        assertEquals("Должен быть активен раздел Соусы", "Соусы", mainPage.getActiveSectionText());
    }

    @Test
    @DisplayName("Проверка активации раздела 'Начинки'")
    public void testFillingsSectionActive() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.waitForPageLoad();


        mainPage.clickFillingsSection();


        mainPage.waitForSectionChange("Начинки");

        assertEquals("Должен быть активен раздел Начинки", "Начинки", mainPage.getActiveSectionText());
    }

    @Test
    @DisplayName("Проверка переключения между разделами")
    public void testSectionSwitching() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.waitForPageLoad();


        assertEquals("Булки", mainPage.getActiveSectionText());

        mainPage.clickSaucesSection();
        mainPage.waitForSectionChange("Соусы");
        assertEquals("Соусы", mainPage.getActiveSectionText());


        mainPage.clickFillingsSection();
        mainPage.waitForSectionChange("Начинки");
        assertEquals("Начинки", mainPage.getActiveSectionText());


        mainPage.clickBunsSection();
        mainPage.waitForSectionChange("Булки");
        assertEquals("Булки", mainPage.getActiveSectionText());
    }
}