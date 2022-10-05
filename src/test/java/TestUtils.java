import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.with;

public interface TestUtils {
    default void waitVisibleElement(WebElement we) {
        with().pollInSameThread().pollDelay(100, TimeUnit.MILLISECONDS).await().atMost
                (10, SECONDS).until(we::isDisplayed);
    }

    default void timeOut() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }
}
