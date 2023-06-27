package Screens;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class WelcomeGmailScreen extends BaseScreen{

    private AppiumDriver driver;
    public WelcomeGmailScreen(AppiumDriver driver){
        super(driver);
        this.driver = driver;
    }

    private final By WelcomeGmailScreen_GotIt_Button =  By.id("welcome_tour_got_it");

    @Step("Click on 'Got it' button at Welcome Gmail screen.")
    public void clickGotItButton(){
        findByWithWaitForSeconds(WelcomeGmailScreen_GotIt_Button).click();
    }

}
