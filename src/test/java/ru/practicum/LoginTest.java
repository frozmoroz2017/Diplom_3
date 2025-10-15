package ru.practicum;

import org.openqa.selenium.WebDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.models.User;
import ru.practicum.pages.LoginPage;
import ru.practicum.pages.MainPage;
import ru.practicum.pages.RegistrationPage;
import ru.practicum.pages.PasswordRecoveryPage;
import ru.practicum.api.UserAPI;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import io.restassured.response.Response;

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

        testUser = User.createRandomUser();
        registerTestUserViaAPI(testUser);
    }

    @After
    @Step("Удаление тестового пользователя")
    public void tearDown() {

        if (testUser != null) {
            String accessToken = getAccessTokenForDeletion(testUser);
            if (accessToken != null) {
                UserAPI.deleteUser(accessToken);
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @Step("Получение access token для удаления пользователя")
    private String getAccessTokenForDeletion(User user) {
        try {
            Response loginResponse = UserAPI.loginUser(user);
            return UserAPI.getAccessToken(loginResponse);
        } catch (Exception e) {
            return null;
        }
    }

    @Step("Регистрация тестового пользователя через API: {user.name}")
    private void registerTestUserViaAPI(User user) {
        UserAPI.createUser(user);
    }

    @Step("Выполнение входа со страницы")
    private void performLoginFromPage(Runnable pageAction) {
        pageAction.run();
        loginPage.waitForPageLoad();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        mainPage.waitForOrderButton();
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
}