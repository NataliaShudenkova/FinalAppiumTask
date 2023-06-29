package Screens;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BaseScreen {
    AppiumDriver driver;

    public BaseScreen(AppiumDriver driver){
        this.driver = driver;
    }

    private String SubjectName;
    private final By NavigationDrawer_Button =  AppiumBy.accessibilityId("Open navigation drawer");


    private ExpectedCondition<WebElement> elementIsDisplayed(By by){
        return appiumDriver -> {
            List<WebElement> list;
            try{
                list = appiumDriver.findElements(by);
            }catch(StaleElementReferenceException exception){
                list = appiumDriver.findElements(by);
            }

            if (list.size() > 0 && list.get(0).isDisplayed()) {
                return list.get(0);
            } else return null;
        };
    }

    public WebElement findByWithWaitForSeconds(By by){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(13));
        return wait.until(elementIsDisplayed(by));
    }

    public boolean tryFindElement(By by){
        var elements = driver.findElements(by);
        if( elements.size() == 1 && elements.get(0).isDisplayed()){
            return true;
        }else{
            return false;
        }
    }

    public void longTapping(WebElement element){
        ((JavascriptExecutor)driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of("elementId",((RemoteWebElement)element).getId(),
                        "duration",1000));
    }

    public void openGmailNavigationDrawer(){
        findByWithWaitForSeconds(NavigationDrawer_Button).click();
    }

    public WebElement IncomeScreen_Subject_Label(String subjectName){
        return findLetterBySubjectText(subjectName);
    }

    public String setSubjectXpath(String subjectName){
        SubjectName = subjectName;
        return String.format("//*[contains(@text, '%s')]", subjectName);
    }

    @Step("Find letter by Subject text")
    public WebElement findLetterBySubjectText(String subjectName){
        SubjectName = subjectName;
        var xpath = By.xpath(setSubjectXpath(subjectName));
        try{
            return findByWithWaitForSeconds(xpath);
        }catch(TimeoutException ex){
            System.out.println(String.format("Letter with subject '%s' is not found", subjectName));
            return null;
        }

    }
}
