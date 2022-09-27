import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {
    public SelenideElement question = $x("/html/body/div[2]/div[3]/div/div[1]/div/div[1]/div/div/div/a[1]");

    public SelenideElement answers = $x("/html/body/div[2]/div[3]/div/div[1]/div/div[1]/div/div/div/a[2]");

    public SelenideElement followers = $x("/html/body/div[2]/div[3]/div/div[1]/div/div/div/div/div/a[4]");

}
