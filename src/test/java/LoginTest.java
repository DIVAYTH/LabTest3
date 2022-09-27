import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest implements UtilsTest {
    private final MainPage mainPage = new MainPage();

    private void setUp(String browserName) {
        Configuration.browser = browserName;
        open("https://otvet.mail.ru/");
    }

    /**
     * Сценарий 1 - пользователь хочет авторизоваться с правильными кредами<br>
     * 1.Пользователь нажимает кнопку войти<br>
     * 2.Пользователь вводит правильные креды для логина и пароля
     * 3.Пользователь успешно авторизуется
     *
     * @param browserName название браузера
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void authorizationTrue(String browserName) throws IOException, InterruptedException {
        setUp(browserName);
        var path = Objects
                .requireNonNull(Thread.currentThread().getContextClassLoader().getResource("test.properties"))
                .getPath();
        var appProps = new Properties();
        appProps.load(new FileInputStream(path));
        String login = appProps.getProperty("login");
        String password = appProps.getProperty("passwordTrue");
        mainPage.login.click();
        findModalDialog();
        waitVisibleElement(mainPage.loginText);
        mainPage.loginText.setValue(login);
        waitVisibleElement(mainPage.password);
        mainPage.password.click();
        waitVisibleElement(mainPage.passwordText);
        mainPage.passwordText.setValue(password);
        waitVisibleElement(mainPage.enter);
        mainPage.enter.click();
        WebDriverRunner.getWebDriver().switchTo().parentFrame();
        waitVisibleElement(mainPage.profile);
        assertTrue(mainPage.profile.exists());
        timeOut();
    }

    /**
     * Сценарий 2 - пользователь хочет авторизоваца с неправильными кредами<br>
     * 1.Пользователь нажимает кнопку войти<br>
     * 2.Пользователь вводит неправильные креды для логина и пароля
     * 3.Пользователь переходит на страницу повторной авторизации с сообщением о невалидности кредов
     *
     * @param browserName название браузера
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void authorizationFalse(String browserName) throws IOException, InterruptedException {
        setUp(browserName);
        var path = Objects
                .requireNonNull(Thread.currentThread().getContextClassLoader().getResource("test.properties"))
                .getPath();
        var appProps = new Properties();
        appProps.load(new FileInputStream(path));
        String login = appProps.getProperty("login");
        String password = appProps.getProperty("passwordFalse");
        mainPage.login.click();
        findModalDialog();
        waitVisibleElement(mainPage.loginText);
        mainPage.loginText.setValue(login);
        waitVisibleElement(mainPage.password);
        mainPage.password.click();
        waitVisibleElement(mainPage.passwordText);
        mainPage.passwordText.setValue(password);
        waitVisibleElement(mainPage.enter);
        mainPage.enter.click();
        WebDriverRunner.getWebDriver().switchTo().parentFrame();

        SelenideElement selenideElement = $x("/html/body/div[1]/div[2]/div/div[2]/div/div/div/div/form/div[2]/div/div[2]/div/div/div[2]/small/div");
        waitVisibleElement(selenideElement);
        assertEquals("Неверный пароль, попробуйте ещё раз", selenideElement.getText());
        timeOut();
    }

    private static void findModalDialog() {
        WebDriverRunner.getWebDriver().switchTo().frame(WebDriverRunner.getWebDriver().findElement(By.cssSelector("iframe")));
    }

    @AfterEach
    public void tearDown() {
        closeWindow();
    }
}
