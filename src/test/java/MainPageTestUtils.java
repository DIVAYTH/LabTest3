import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.awaitility.core.ConditionTimeoutException;
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
public class MainPageTestUtils implements TestUtils {
    private final MainPage mainPage = new MainPage();
    private static String login;
    private static String passwordTrue;
    private static String passwordFalse;

    @BeforeAll
    public static void getCredentials() throws IOException {
        var path = Objects
                .requireNonNull(Thread.currentThread().getContextClassLoader().getResource("test.properties"))
                .getPath();
        var appProps = new Properties();
        appProps.load(new FileInputStream(path));
        login = appProps.getProperty("login");
        passwordTrue = appProps.getProperty("passwordTrue");
        passwordFalse = appProps.getProperty("passwordFalse");
    }

    private void login(String password) {
        waitVisibleElement(mainPage.login);
        mainPage.login.click();
        waitVisibleElement(WebDriverRunner.getWebDriver().findElement(By.cssSelector("iframe")));
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

    private void setUp(String browserName) throws InterruptedException {
        Configuration.browser = browserName;
        timeOut();
        open("https://otvet.mail.ru/");
    }

    /**
     * ???????????????? 1 - ???????????????????????? ?????????? ???????????????????????????? ?? ?????????????????????? ??????????????<br>
     * 1.???????????????????????? ???????????????? ???????????? ??????????<br>
     * 2.???????????????????????? ???????????? ???????????????????? ?????????? ?????? ???????????? ?? ????????????<br>
     * 3.???????????????????????? ?????????????? ????????????????????????<br>
     * 4.???? ?????????????? ???????????????? ???????????????????? ???????????? ?????? ???????????????? ?? ??????????????
     *
     * @param browserName ???????????????? ????????????????
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void authorizationTrue(String browserName) throws InterruptedException {
        setUp(browserName);
        login(passwordTrue);
        waitVisibleElement(mainPage.profile);
        assertTrue(mainPage.profile.exists());
        timeOut();

    }

    /**
     * ???????????????? 2 - ???????????????????????? ?????????? ???????????????????????? ?? ?????????????????????????? ??????????????<br>
     * 1.???????????????????????? ???????????????? ???????????? ??????????<br>
     * 2.???????????????????????? ???????????? ???????????????????????? ?????????? ?????? ???????????? ?? ????????????
     * 3.???????????????????????? ?????????????????? ???? ???????????????? ?????????????????? ?????????????????????? ?? ???????????????????? ?? ???????????????????????? ????????????
     *
     * @param browserName ???????????????? ????????????????
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void authorizationFalse(String browserName) throws InterruptedException {
        setUp(browserName);
        login(passwordFalse);
        waitVisibleElement(mainPage.error);
        assertEquals("???????????????? ????????????, ???????????????????? ?????? ??????", mainPage.error.getText());
        timeOut();
    }

    /**
     * ???????????????? 3 - ???????????????????????????? ???????????????????????? ??????????<br>
     * 1.???????????????????????? ???????????????? ???? ?????????????????? ?? ????????????<br>
     * 2.???????????????????????? ???????? ?????????????????? ?? ?? ?????????????????????????? ???????? ?????????? ???????????????? ???????????? ??????????
     * 3.???????????????????????? ???????? ?????????????????? ?? ?????????????? ???? ??????????????
     * 4.???????????? ?????????? ?????????? ????????????????
     *
     * @param browserName ???????????????? ????????????????
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void logout(String browserName) throws InterruptedException {
        setUp(browserName);
        login(passwordTrue);
        waitVisibleElement(mainPage.logoutAcc);
        mainPage.logoutAcc.click();
        waitVisibleElement(mainPage.logout);
        mainPage.logout.click();
        waitVisibleElement(mainPage.login);
        assertTrue(mainPage.login.exists());
        timeOut();
    }

    /**
     * ???????????????? 4 - ???????????????????????? ?????????? ???????????????? ???????????? ?????????????????? ?? ?????????????? ???????????????????????? ??????????????????<br>
     * 1.???????????????????????? ???????????????? ?? ?????????????? ???????? ?????????????? ??????????????????<br>
     * 2.???????????????????????? ???????? ?????????????????? ?? ?????????? ???????????? ??????????????????
     * 3.???????????????????????? ???????????????? ???????????? ??????????????????
     * 4.???????????????????????? ???????? ?????????????????? ?? ???????????????? ?????? ?????????????? ?????????????????? ?? ???????????????? ????????????????????
     *
     * @param browserName ???????????????? ????????????????
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void checkCategories(String browserName) throws InterruptedException {
        setUp(browserName);
        login(passwordTrue);
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
     * ???????????????? 5 - ???????????????????????? ?????????? ???????????? ???????????? (???? ?? ???????? ???? ?????????????? ??????????))<br>
     * 1.???????????????????????? ???????????????? ?? ?????????????? ???????? ?????????????? ????????????????<br>
     * 2.???????????????????????? ???????? ?????????????????? ?? ?????????????????? ???? ???????????????? ???????????????????? ??????????????
     * 3.???????????????????????? ???????????? ???????? ?? ?????? ????????????
     * 4.???????????????????????? ???????? ?????????????????? ?? ???????????????? ?????????????????? ???????? ?? ?????????????????????? ???? ??????????????????????
     *
     * @param browserName ???????????????? ????????????????
     */
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    public void checkAsk(String browserName) throws InterruptedException {
        setUp(browserName);
        login(passwordTrue);
        mainPage.ask.click();
        waitVisibleElement(mainPage.blockAsk);
        String expectedURL = "https://otvet.mail.ru/ask";
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        waitVisibleElement(mainPage.themeQuestion);
        mainPage.themeQuestion.setValue("Java ?????????? ?? ????????????");
        WebElement webElement = WebDriverRunner.getWebDriver().findElement(By.xpath("//*[@id=\"question_additional\"]/div[2]/div/p"));
        waitVisibleElement(webElement);
        String script = "arguments[0].innerHTML='Java ???????????????????? ?????? ?????????? ?? ???????????? (???????????????? Selenium ?????????? ???? ????????????????)'";
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
     * ???????????????? 6 - ???????????????????????? ?????????? ???????????????? ???????????????????? ?? ??????????????<br>
     * 1.???????????????????????? ???????????????? ?? ?????????????? ???????? ?????????????? ????????????<br>
     * 2.???????????????????????? ???????? ?????????????????? ?? ?????????????????? ???? ???????????????? ???????????????????? ?? ??????????????
     *
     * @param browserName ???????????????? ????????????????
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
     * ???????????????? 7 - ???????????????????????? ?????????? ???????????????? ???????????????????? ?? ????????????????<br>
     * 1.???????????????????????? ???????????????? ?? ?????????????? ???????? ?????????????? ??????????????<br>
     * 2.???????????????????????? ???????? ?????????????????? ?? ?????????????????? ???? ???????????????? ???????????????????? ?? ????????????????
     *
     * @param browserName ???????????????? ????????????????
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
     * ???????????????? 8 - ???????????????????????? ?????????? ?????????? ?????????????? ???????????????????? ???????????????????????? ??????????<br>
     * 1.???????????????????????? ?????????????? ?????????? ?? ???????? ??????????<br>
     * 2.???????????????????????? ???????? ?????????????????? ?? ?????????????????? ???? ???????????????? ?? ?????????????????? ???????????????????? ?????????????????? ??????????<br>
     * 3.?? ???????? ???????????? ?? ?????????? ?????????? ???????????????????? ?????????????????? ??????????
     *
     * @param browserName ???????????????? ????????????????
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