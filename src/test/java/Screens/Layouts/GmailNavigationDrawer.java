package Screens.Layouts;

import Screens.BaseScreen;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class GmailNavigationDrawer extends BaseScreen {
    private AppiumDriver driver;
    public GmailNavigationDrawer(AppiumDriver driver){
        super(driver);
        this.driver = driver;
    }

    private final By NavDrawer_Trash =  By.xpath("//*[@text = 'Trash']");
    private final By NavDrawer_Primary =  By.xpath("//*[@text = 'Primary']");

    @Step("Open Trash Folder")
    public void openTrashFolder(){
        findByWithWaitForSeconds(NavDrawer_Trash).click();
    }

    @Step("Open Primary Folder")
    public void openPrimaryFolder(){
        findByWithWaitForSeconds(NavDrawer_Primary).click();
    }

}
