package ru.practicum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.practicum.models.User;
import ru.practicum.pages.LoginPage;
import ru.practicum.pages.RegistrationPage;
import ru.practicum.api.UserAPI;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationTest {
    private WebDriver driver;
    private User testUser;
    private String accessToken;

    @Before
    public void setUp() {
        driver = DriverFactory.initDriver();
    }

    @After
    public void tearDown() {

        if (accessToken != null) {
            UserAPI.deleteUser(accessToken);
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    public void testSuccessfulRegistration() {
        testUser = User.createRandomUser();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        registrationPage.waitForPageLoad();

        performRegistration(registrationPage, testUser);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPageLoad();

        assertTrue("Должна открыться страница входа после успешной регистрации",
                driver.getCurrentUrl().contains("login"));


        Response loginResponse = UserAPI.loginUser(testUser);
        accessToken = UserAPI.getAccessToken(loginResponse);
    }

    @Step("Регистрация пользователя: {user.name}")
    private void performRegistration(RegistrationPage registrationPage, User user) {
        registrationPage.register(user.getName(), user.getEmail(), user.getPassword());
    }

    @Test
    @DisplayName("Регистрация с коротким паролем")
    public void testRegistrationWithShortPassword() {
        User user = User.createUserWithShortPassword();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        registrationPage.waitForPageLoad();

        performRegistration(registrationPage, user);

        String errorMessage = registrationPage.getErrorMessage();
        assertEquals("Некорректный пароль", errorMessage);
    }

    @Test
    @DisplayName("Регистрация с очень коротким паролем")
    public void testRegistrationWithVeryShortPassword() {
        User user = User.createUserWithVeryShortPassword();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        registrationPage.waitForPageLoad();

        performRegistration(registrationPage, user);

        String errorMessage = registrationPage.getErrorMessage();
        assertEquals("Некорректный пароль", errorMessage);
    }
}