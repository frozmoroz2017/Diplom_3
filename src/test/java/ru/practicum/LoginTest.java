package ru.practicum;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.models.User;
import ru.practicum.pages.LoginPage;
import ru.practicum.pages.MainPage;
import ru.practicum.pages.RegistrationPage;
import ru.practicum.pages.PasswordRecoveryPage;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class LoginTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private PasswordRecoveryPage passwordRecoveryPage;
    private User testUser;

    @Before
    public void setUp() {
        driver = DriverFactory.initDriver();

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);

        // Создаем тестового пользователя и регистрируем его
        testUser = User.createRandomUser();
        registerTestUser(testUser);
    }

    @Step("Регистрация тестового пользователя: {user.name}")
    private void registerTestUser(User user) {
        registrationPage.open();
        registrationPage.waitForPageLoad();
        registrationPage.register(user.getName(), user.getEmail(), user.getPassword());

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/login"));
    }

    @Step("Выполнение входа со страницы")
    private void performLoginFromPage(Runnable pageAction) {
        pageAction.run();
        loginPage.waitForPageLoad();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(mainPage.getOrderButtonLocator()));

        assertTrue("Кнопка 'Оформить заказ' должна отображаться после успешного входа",
                mainPage.isOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Успешный вход через главную страницу")
    public void testSuccessfulLoginFromMainPage() {
        performLoginFromPage(() -> {
            mainPage.open();
            mainPage.waitForPageLoad();
            mainPage.clickLoginButton();
        });
    }

    @Test
    @DisplayName("Успешный вход через личный кабинет")
    public void testSuccessfulLoginFromPersonalAccount() {
        performLoginFromPage(() -> {
            mainPage.open();
            mainPage.waitForPageLoad();
            mainPage.clickPersonalAccountButton();
        });
    }

    @Test
    @DisplayName("Успешный вход со страницы регистрации")
    public void testSuccessfulLoginFromRegistrationPage() {
        performLoginFromPage(() -> {
            registrationPage.open();
            registrationPage.waitForPageLoad();
            registrationPage.clickLoginLink();
        });
    }

    @Test
    @DisplayName("Успешный вход со страницы восстановления пароля")
    public void testSuccessfulLoginFromPasswordRecoveryPage() {
        performLoginFromPage(() -> {
            passwordRecoveryPage.open();
            passwordRecoveryPage.waitForPageLoad();
            passwordRecoveryPage.clickLoginLink();
        });
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}