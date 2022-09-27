import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainPageTest implements UtilsTest {
    private final MainPage mainPage = new MainPage();
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
     * Сценарий 3 - пользователь хочет получить список категорий и выбрать определенную категорию<br>
     * 1.Пользователь выбирает в главном меню вкладку категории<br>
     * 2.Пользователь ждет прогрузки и видит список категорий
     * 3.Пользователь выбирает нужную категорию
     * 4.Пользователь ждет прогрузки и переходит на страницу с выбранной категорией
     *
     * @param browserName название браузера
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void checkCategories(String browserName) throws InterruptedException {
        setUp(browserName);
        mainPage.categories.click();
        waitVisibleElement(mainPage.blockCategories);
        waitVisibleElement(mainPage.computerGame);
        mainPage.computerGame.click();
        String expectedURL = "https://otvet.mail.ru/games/";
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(expectedURL, actualURL);
        timeOut();
    }

    /**
     * Сценарий 4 - пользователь хочет задать вопрос<br>
     * 1.Пользователь выбирает в главном меню вкладку спросить<br>
     * 2.Пользователь ждет прогрузки и переходит на страницу объявления вопроса
     *
     * @param browserName название браузера
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void checkAsk(String browserName) throws InterruptedException {
        setUp(browserName);
        login();
        mainPage.ask.click();
        waitVisibleElement(mainPage.blockAsk);
        String expectedURL = "https://otvet.mail.ru/ask";
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        waitVisibleElement(mainPage.themeQuestion);
        mainPage.themeQuestion.setValue("Java плюсы и минусы");
        WebElement webElement = WebDriverRunner.getWebDriver().findElement(By.xpath("//*[@id=\"question_additional\"]/div[2]/div/p"));
        waitVisibleElement(webElement);
        String script = "arguments[0].innerHTML='Java расскажите про плюсы и минусы'";
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript(script, webElement);
        waitVisibleElement(mainPage.publishQuestion);
        mainPage.publishQuestion.click();
        SelenideElement modal = $x("/html/body/div[2]/div[2]/div[26]/div/div/div[2]");
        waitVisibleElement(modal);
        assertAll(() -> {
            assertEquals(expectedURL, actualURL);
            assertTrue(modal.exists());
        });
        timeOut();
    }

    /**
     * Сценарий 5 - пользователь хочет получить информацию о лидерах<br>
     * 1.Пользователь выбирает в главном меню вкладку лидеры<br>
     * 2.Пользователь ждет прогрузки и переходит на страницу информацию о лидерах
     *
     * @param browserName название браузера
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void checkLeaders(String browserName) throws InterruptedException {
        setUp(browserName);
        mainPage.leaders.click();
        waitVisibleElement(mainPage.blockLeaders);
        String expectedURL = "https://otvet.mail.ru/top";
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertAll(() -> {
            assertEquals(expectedURL, actualURL);
            assertTrue(mainPage.blockLeaders.exists());
        });
        timeOut();
    }

    /**
     * Сценарий 6 - пользователь хочет получить информацию о бизнессе<br>
     * 1.Пользователь выбирает в главном меню вкладку бизнесс<br>
     * 2.Пользователь ждет прогрузки и переходит на страницу информацию о бизнессе
     *
     * @param browserName название браузера
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void checkBusiness(String browserName) throws InterruptedException {
        setUp(browserName);
        mainPage.business.click();
        waitVisibleElement(mainPage.blockBusiness);
        String expectedURL = "https://otvet.mail.ru/for_companies";
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertAll(() -> {
            assertEquals(expectedURL, actualURL);
            assertTrue(mainPage.blockBusiness.exists());
        });
        timeOut();
    }

    /**
     * Сценарий 7 - пользователь хочет найти вопросы содержащее определенное слово<br>
     * 1.Пользователь вбивает слово и жмет найти<br>
     * 2.Пользователь ждет прогрузки и переходит на страницу с вопросами содержащии набранное слово<br>
     * 3.В поле поиска в новом блоке появляется набранное слово
     *
     * @param browserName название браузера
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void checkSearch(String browserName) throws InterruptedException {
        setUp(browserName);
        mainPage.searchText.setValue("java");
        mainPage.search.click();
        String expected = "java";
        String expectedURL = "https://otvet.mail.ru/search/java/";
        SelenideElement selenideElement = $x("//*[@id=\"search_value\"]");
        waitVisibleElement(selenideElement);
        String actual = selenideElement.getValue();
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertAll(() -> {
            assertEquals(expected, actual);
            assertEquals(expectedURL, actualURL);
        });
        timeOut();
    }

    @AfterEach
    public void tearDown() {
        closeWindow();
    }
}