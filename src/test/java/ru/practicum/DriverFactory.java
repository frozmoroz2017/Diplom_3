package ru.practicum;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.practicum.utils.BrowserType;

import java.time.Duration;
import java.io.InputStream;
import java.util.Properties;

public class DriverFactory {
    public static WebDriver initDriver() {
        WebDriver driver;


        String browser = getBrowserFromProperties();

        BrowserType browserType = BrowserType.valueOf(browser.toUpperCase());

        System.out.println("=== Запускаем браузер: " + browserType + " ===");

        switch (browserType) {
            case CHROME:
                driver = startChrome();
                break;
            case YANDEX:
                driver = startYandex();
                break;
            default:
                throw new IllegalArgumentException("Browser undefined: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    private static String getBrowserFromProperties() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = DriverFactory.class.getClassLoader()
                    .getResourceAsStream("browser.properties");

            if (inputStream != null) {
                properties.load(inputStream);
                String browserFromFile = properties.getProperty("testBrowser");
                System.out.println("Браузер из properties файла: " + browserFromFile);
                inputStream.close();
                return browserFromFile != null ? browserFromFile : "chrome";
            } else {
                System.out.println("Файл browser.properties не найден в classpath, используем Chrome по умолчанию");
                return "chrome";
            }
        } catch (Exception e) {
            System.out.println("Ошибка при чтении browser.properties, используем Chrome по умолчанию");
            return "chrome";
        }
    }

    private static WebDriver startChrome() {
        System.out.println("Инициализация Chrome браузера");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }

    private static WebDriver startYandex() {
        System.out.println("Инициализация Yandex браузера");

        System.setProperty("webdriver.chrome.driver", "src/test/resources/yandexdriver.exe");

        ChromeOptions options = new ChromeOptions();

        options.setBinary("C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe");

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        return new ChromeDriver(options);
    }
}