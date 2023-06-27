package Screens;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CreateGmailLetterScreen extends BaseScreen{
    private AppiumDriver driver;
    public CreateGmailLetterScreen(AppiumDriver driver){
        super(driver);
        this.driver = driver;
    }

    private final By CreateGmailLetter_SendTo_Field =  By.xpath("//android.widget.RelativeLayout[1]//android.widget.EditText");
    private final By CreateGmailLetter_Recipient_List =  By.id("peoplekit_listview_contact_name");
    private final By CreateGmailLetter_Subject_Field =  By.id("subject");
    private final By CreateGmailLetter_EmailText_Area =  By.xpath("//android.widget.RelativeLayout[2]//android.widget.EditText");
    private final By CreateGmailLetter_Send_Button =  By.id("send");

    @Step("Fill in fields in email and send it.")
    public void sendEmail(String emailAddress, String subject, String emailText ){
        findByWithWaitForSeconds(CreateGmailLetter_SendTo_Field).sendKeys(emailAddress);
        findByWithWaitForSeconds(CreateGmailLetter_Recipient_List).click();
        findByWithWaitForSeconds(CreateGmailLetter_Subject_Field).sendKeys(subject);
        findByWithWaitForSeconds(CreateGmailLetter_EmailText_Area).sendKeys(emailText);
        findByWithWaitForSeconds(CreateGmailLetter_Send_Button).click();
    }
}
