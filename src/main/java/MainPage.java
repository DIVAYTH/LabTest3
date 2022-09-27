import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    public SelenideElement login = $x("/html/body/div[1]/div[3]/div/div[2]/button");
    public SelenideElement loginText = $x("/html/body/div[1]/div[2]/div/div/div/div[2]/div/div/form/div[2]/div/div[1]/div/div/div/div/div/div[1]/div/input");
    public SelenideElement password = $x("/html/body/div[1]/div[2]/div/div/div/div[2]/div/div/form/div[2]/div/div[3]/div/div/div[1]/button/span");
    public SelenideElement passwordText = $x("/html/body/div[1]/div[2]/div/div/div/div[2]/div/div/form/div[2]/div/div[2]/div/div/div/div/div/input");

    public SelenideElement enter = $x("/html/body/div[1]/div[2]/div/div/div/div[2]/div/div/form/div[2]/div/div[3]/div/div/div[1]/div/button/span");

    public SelenideElement profile = $x("/html/body/div[1]/div[4]/div[2]/div[1]/div/div[2]/div/span/span[2]/span[5]/a");

    public SelenideElement categories = $x("/html/body/div[1]/div[4]/div[2]/div[1]/div/div[2]/div/span/span[1]/span[1]/span[1]/span[4]");
    public SelenideElement computerGame = $x("/html/body/div[1]/div[4]/div[2]/div[1]/div/div[2]/div/span/span[1]/span[1]/span[2]/span[2]/span[1]/a");
    public SelenideElement blockCategories = $x("/html/body/div[1]/div[4]/div[2]/div[1]/div/div[2]/div/span/span[1]/span[1]/span[2]");
    public SelenideElement leaders = $x("/html/body/div[1]/div[4]/div[2]/div[1]/div/div[2]/div/span/span[1]/span[3]/a/span[3]/span");
    public SelenideElement blockLeaders = $x("/html/body/div[2]/div[2]/div/div[2]/div");

    public SelenideElement ask = $x("/html/body/div[1]/div[4]/div[2]/div[1]/div/div[2]/div/span/span[1]/span[2]/a");
    public SelenideElement themeQuestion = $x("//*[@id=\"question_text\"]");

    public SelenideElement publishQuestion = $x("/html/body/div[2]/div[3]/div/div[2]/div/div/div[1]/div/div/form/div/div[5]/a/div");
    public SelenideElement blockAsk = $x("/html/body/div[2]/div[3]/div/div[2]/div");

    public SelenideElement business = $x("/html/body/div[1]/div[4]/div[2]/div[1]/div/div[2]/div/span/span[1]/span[4]/a/span[3]/span");
    public SelenideElement searchText = $x("/html/body/div[1]/div[4]/div[2]/div[1]/div/div[2]/div/span/span[2]/span[3]/span/form/span[1]/span[1]/input");
    public SelenideElement blockBusiness = $x("/html/body/div[2]/div[2]");

    public SelenideElement search = $x("/html/body/div[1]/div[4]/div[2]/div[1]/div/div[2]/div/span/span[2]/span[3]/span/form/span[2]/button");
}
