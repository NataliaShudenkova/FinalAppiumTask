package Screens.Layouts;

import Screens.BaseScreen;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PermissionLayout extends BaseScreen {
    private AppiumDriver driver;
    public PermissionLayout(AppiumDriver driver){
        super(driver);
        this.driver = driver;
    }

    private final By PermissionLayout_Allow_Button =  By.id("com.android.permissioncontroller:id/permission_allow_button");

    @Step("Click on 'Allow button' in Permission popup")
    public void clickAllowButton(){
        findByWithWaitForSeconds(PermissionLayout_Allow_Button).click();
    }

    public boolean isPermissionLayoutDisplayed(){
        var welcomeWindow = driver.findElements(PermissionLayout_Allow_Button);
        return welcomeWindow.isEmpty() ? false : true;
    }

}
