package Screens;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class GmailListScreen extends BaseScreen{
    private AppiumDriver driver;
    public  GmailListScreen(AppiumDriver driver){
        super(driver);
        this.driver = driver;
    }

    private final By GmailList_TakeMeToGmail_Button =  By.id("action_done");
    private final By GmailList_Section =  By.id("setup_addresses_list");

    @Step("Click on 'TakeMeToGmail' button in Gmail List Screen.")
    public void clickTakeMeToGmailButton(){
        findByWithWaitForSeconds(GmailList_Section);
        findByWithWaitForSeconds(GmailList_TakeMeToGmail_Button).click();
    }
}
