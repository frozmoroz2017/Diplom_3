package ru.practicum.api;

import io.restassured.response.Response;
import ru.practicum.models.User;
import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

public class UserAPI {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";

    @Step("Создание пользователя: {user.name}")
    public static Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BASE_URL + "/auth/register");
    }

    @Step("Логин пользователя: {user.email}")
    public static Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BASE_URL + "/auth/login");
    }

    @Step("Удаление пользователя")
    public static void deleteUser(String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            given()
                    .header("Authorization", accessToken)
                    .delete(BASE_URL + "/auth/user");
        }
    }

    @Step("Получение access token из ответа логина")
    public static String getAccessToken(Response loginResponse) {
        return loginResponse.then().extract().path("accessToken");
    }

    @Step("Получение access token из ответа регистрации")
    public static String getAccessTokenFromRegistration(Response registrationResponse) {
        return registrationResponse.then().extract().path("accessToken");
    }
}