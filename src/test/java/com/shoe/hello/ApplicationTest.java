package com.shoe.hello;

import com.google.common.base.Function;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApplicationTest {
    public static final int CLIENT_PORT = 9000;
    public static final int SERVER_PORT = 8080;
    public static final String BASE_URL = "http://localhost:" + CLIENT_PORT + "/index.html";
    public static final String ID_CLASS = "hello-world-id";
    public static final String MESSAGE_CLASS = "hello-world-message";
    public static final String EMPTY = "^$";

    private ChromeDriver driver;

    @BeforeClass
    public static void initSpring() {
        final ConfigurableApplicationContext client = SpringApplication.start(CLIENT_PORT);
        final String clientPort = client.getEnvironment().getProperty("local.server.port");
        final ConfigurableApplicationContext server = SpringApplication.start(SERVER_PORT);
        final String serverPort = server.getEnvironment().getProperty("local.server.port");
        assertEquals(CLIENT_PORT, Integer.parseInt(clientPort));
        assertEquals(SERVER_PORT, Integer.parseInt(serverPort));
    }

    @Before
    public void setup() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        String pathToChromeDriver = "libs/chromedriver";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        final PrintStream originalSystemErr = System.err;
        final PrintStream ignoredOut = new PrintStream(new ByteArrayOutputStream());
        System.setErr(ignoredOut);
        driver = new ChromeDriver(chromeOptions);
        System.setErr(originalSystemErr);
        navigateTo(BASE_URL);
    }

    @After
    public void cleanup() {
        driver.quit();
    }

    @Test
    public void fieldsInitiallyBlank() {
        elementWithIdMatches(ID_CLASS, EMPTY);
        elementWithIdMatches(MESSAGE_CLASS, EMPTY);
    }

    @Test
    public void sayingHelloGetAllowed() {
        clickOn("sayHelloGet");

        elementWithIdMatches(ID_CLASS, "^Times Called: \\d+$");
        elementWithIdMatches(MESSAGE_CLASS, "^Message: Hello, World!$");
    }

    @Test
    public void sayingHelloPostFails() {
        clickOn("sayHelloPost");
        elementWithIdMatches(ID_CLASS, "^Times Called: error$");
        elementWithIdMatches(MESSAGE_CLASS, "^Message: Request Failed$");
    }

    private void elementWithIdMatches(String cssClass, String pattern) {
        final String value = textByCssClass(cssClass);
        assertTrue(value, value.matches(pattern));
    }

    private String textByCssClass(String className) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.of(30, ChronoUnit.SECONDS))
                .pollingEvery(Duration.of(10, ChronoUnit.MILLIS))
                .ignoring(Exception.class);

        final Function<WebDriver, WebElement> findByClassName = input -> input.findElement(By.className(className));
        return wait.until(findByClassName).getText();
    }

    private void clickOn(String buttonId) {
        driver.findElement(By.id(buttonId)).click();
        awaitAjaxExecution();
    }

    private void awaitAjaxExecution() {
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript("return jQuery.active == 0");
        });
    }

    public void navigateTo(String page) {
        driver.navigate().to(page);
        Wait<WebDriver> wait = new WebDriverWait(driver, 30);
        wait.until(driver -> String
                .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                .equals("complete"));
    }

}
