package ru.sterkhov_kirill.NauJava.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginLogoutTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080";

    @BeforeAll
    static void setUpClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    static void tearDownClass() {
        if (driver != null) driver.quit();
    }

    @Test
    @Order(1)
    void shouldLoginSuccessfully() {
        driver.get(BASE_URL + "/login");

        driver.findElement(By.name("username")).sendKeys("test");
        driver.findElement(By.name("password")).sendKeys("test");

        driver.findElement(By.className("submit-input")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("h1"), "Book List"));

        Assertions.assertTrue(
                driver.findElement(By.tagName("h1")).getText().contains("Book List"));
    }

    @Test
    @Order(2)
    void shouldLogoutAndReturnToLoginPage() {
        driver.findElement(By.className("logout-btn")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("h1"), "Авторизация"));

        Assertions.assertTrue(
                driver.getCurrentUrl().contains("/login"));
    }
}
