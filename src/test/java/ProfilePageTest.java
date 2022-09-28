import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfilePageTest implements UtilsTest {
    private final MainPage mainPage = new MainPage();
    private final ProfilePage profilePage = new ProfilePage();
    private static String login;
    private static String password;

    @BeforeAll
    public static void getCredentials() throws IOException {
        var path = Objects
                .requireNonNull(Thread.currentThread().getContextClassLoader().getResource("test.properties"))
                .getPath();
        var appProps = new Properties();
        appProps.load(new FileInputStream(path));
        login = appProps.getProperty("login");
        password = appProps.getProperty("passwordTrue");
    }

    private void login() {
        mainPage.login.click();
        WebDriverRunner.getWebDriver().switchTo().frame(WebDriverRunner.getWebDriver().findElement(By.cssSelector("iframe")));
        waitVisibleElement(mainPage.loginText);
        mainPage.loginText.setValue(login);
        waitVisibleElement(mainPage.password);
        mainPage.password.click();
        waitVisibleElement(mainPage.passwordText);
        mainPage.passwordText.setValue(password);
        waitVisibleElement(mainPage.enter);
        mainPage.enter.click();
        WebDriverRunner.getWebDriver().switchTo().parentFrame();
    }

    private void setUp(String browserName) {
        Configuration.browser = browserName;
        open("https://otvet.mail.ru/");
    }

    /**
     * Сценарий 9 - авторизованный пользователь хочет посмотреть список своих вопросов<br>
     * 1.Пользователь авторизуется<br>
     * 2.Пользователь жмет на кнопку профиля<br>
     * 3.Пользователь ждет прогрузки и попадает в свой профиль<br>
     * 4.Пользователь выбирает вопросы и получает список всех его вопросов
     *
     * @param browserName название браузера
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void checkQuestion(String browserName) throws InterruptedException {
        setUp(browserName);
        login();
        mainPage.profile.click();
        waitVisibleElement(profilePage.question);
        profilePage.question.click();
        String expectedURL = "https://otvet.mail.ru/profile/id68072248/questions/all/";
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(expectedURL, actualURL);
        timeOut();
    }

    /**
     * Сценарий 10 - авторизованный пользователь хочет посмотреть список ответов на свои вопросы<br>
     * 1.Пользователь авторизуется<br>
     * 2.Пользователь жмет на кнопку профиля<br>
     * 3.Пользователь ждет прогрузки и попадает в свой профиль<br>
     * 4.Пользователь выбирает ответы и получает список всех ответов на его вопросы
     *
     * @param browserName название браузера
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void checkAnswers(String browserName) throws InterruptedException {
        setUp(browserName);
        login();
        mainPage.profile.click();
        waitVisibleElement(profilePage.answers);
        profilePage.answers.click();
        String expectedURL = "https://otvet.mail.ru/profile/id68072248/answers/all/";
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(expectedURL, actualURL);
        timeOut();
    }

    /**
     * Сценарий 11 - авторизованный пользователь хочет посмотреть информацию о своих подписчиков<br>
     * 1.Пользователь авторизуется<br>
     * 2.Пользователь жмет на кнопку профиля<br>
     * 3.Пользователь ждет прогрузки и попадает в свой профиль<br>
     * 4.Пользователь выбирает подписчики и получает информацию о своих подписчиков
     *
     * @param browserName название браузера
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void checkFollowers(String browserName) throws InterruptedException {
        setUp(browserName);
        login();
        mainPage.profile.click();
        waitVisibleElement(profilePage.followers);
        profilePage.followers.click();
        String expectedURL = "https://otvet.mail.ru/profile/id68072248/followers/users/";
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(expectedURL, actualURL);
        timeOut();
    }

    @AfterEach
    public void tearDown() {
        closeWindow();
    }
}