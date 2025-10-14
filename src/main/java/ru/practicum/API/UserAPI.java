package ru.practicum.api;

import io.restassured.response.Response;
import ru.practicum.models.User;
import static io.restassured.RestAssured.given;

public class UserAPI {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";

    public static Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(String.format("{\"name\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}",
                        user.getName(), user.getEmail(), user.getPassword()))
                .post(BASE_URL + "/auth/register");
    }

    public static Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\"}",
                        user.getEmail(), user.getPassword()))
                .post(BASE_URL + "/auth/login");
    }

    public static void deleteUser(String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            given()
                    .header("Authorization", accessToken)
                    .delete(BASE_URL + "/auth/user");
        }
    }

    public static String getAccessToken(Response loginResponse) {
        return loginResponse.then().extract().path("accessToken");
    }

    public static String getAccessTokenFromRegistration(Response registrationResponse) {
        return registrationResponse.then().extract().path("accessToken");
    }
}