package Screens;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class GmailIncomeScreen extends BaseScreen{
    private AppiumDriver driver;
    public  GmailIncomeScreen(AppiumDriver driver){
        super(driver);
        this.driver = driver;
    }

    private final By IncomeScreen_OnboardingAnimation_Popup =  By.id("onboarding_animation");
    private final By IncomeScreen_GotIt_Button =  By.id("next_button");
    private final By IncomeScreen_Compose_Button =  By.id("compose_button");
    private final By IncomeScreen_Delete_Button =  By.id("delete");
    private final By IncomeScreen_Sender_Label =  By.id("senders");
    private final By IncomeScreen_Time_Label =  By.id("date");
    private final By IncomeScreen_Snippet_Label =  By.id("snippet");

    @Step("Click on 'Got it' button at Onboarding Animation popup.")
    public void clickGotItButton(){
        findByWithWaitForSeconds(IncomeScreen_OnboardingAnimation_Popup);
        findByWithWaitForSeconds(IncomeScreen_GotIt_Button).click();
    }

    @Step("Click on 'Compose' button at Gmail Income Screen.")
    public void clickComposeButton(){
        findByWithWaitForSeconds(IncomeScreen_Compose_Button).click();
    }

    @Step("Delete Letter.")
    public void deleteLetter(String subjectName){
        longTapping(IncomeScreen_Subject_Label(subjectName));
        findByWithWaitForSeconds(IncomeScreen_Delete_Button).click();
    }

    public String getSnippetText(){
        return findByWithWaitForSeconds(IncomeScreen_Snippet_Label).getText();
    }

    public String getTimeText(){
        return findByWithWaitForSeconds(IncomeScreen_Time_Label).getText();
    }

    public String getSenderText(){
        return findByWithWaitForSeconds(IncomeScreen_Sender_Label).getText();
    }
}
