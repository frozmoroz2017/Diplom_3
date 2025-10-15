package ru.practicum.models;

import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public User() {
    }

    public static User createRandomUser() {
        String name = "TestUser" + RandomStringUtils.randomNumeric(5);
        String email = "test" + RandomStringUtils.randomNumeric(5) + "@example.com";
        String password = "password" + RandomStringUtils.randomNumeric(3);
        return new User(name, email, password);
    }

    public static User createUserWithShortPassword() {
        String name = "TestUser" + RandomStringUtils.randomNumeric(5);
        String email = "test" + RandomStringUtils.randomNumeric(5) + "@example.com";
        String password = "short";
        return new User(name, email, password);
    }

    public static User createUserWithVeryShortPassword() {
        String name = "TestUser" + RandomStringUtils.randomNumeric(5);
        String email = "test" + RandomStringUtils.randomNumeric(5) + "@example.com";
        String password = "123";
        return new User(name, email, password);
    }


    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }


    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}