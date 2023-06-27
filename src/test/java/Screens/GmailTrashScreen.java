package Screens;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class GmailTrashScreen extends BaseScreen{
    private AppiumDriver driver;
    public  GmailTrashScreen (AppiumDriver driver){
        super(driver);
        this.driver = driver;
    }

    private final By GmailTrash_EmptyTrashNow_Button =  By.id("empty_trash_spam_action");
    private final By GmailTrash_ConfirmEmpty_Button =  By.xpath("//*[@text = 'Empty']");
    private final By GmailTrash_NothingInTrash_Label =  By.id("empty_text");

    @Step("Clear Trash Folder")
    public void clearTrashFolder(){
        if(!tryFindElement(GmailTrash_NothingInTrash_Label)){
            findByWithWaitForSeconds(GmailTrash_EmptyTrashNow_Button).click();
            findByWithWaitForSeconds(GmailTrash_ConfirmEmpty_Button).click();
        }
    }

}
