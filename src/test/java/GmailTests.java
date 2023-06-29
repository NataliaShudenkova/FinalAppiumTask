import Screens.*;
import Screens.Layouts.GmailNavigationDrawer;
import Screens.Layouts.PermissionLayout;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.qameta.allure.*;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.By;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class GmailTests extends BaseConfigurations {
    private AndroidDriver driver;
    private PermissionLayout permission;
    private WelcomeGmailScreen welcomeGmailScreen;
    private GmailListScreen gmailListScreen;
    private GmailIncomeScreen gmailIncomeScreen;
    private CreateGmailLetterScreen createGmailLetterScreen;
    private GmailNavigationDrawer gmailNavigationDrawer;
    private GmailTrashScreen gmailTrashScreen;
    private BaseScreen baseScreen;
    private final String emailAddress = "natalia.test0328@gmail.com";
    protected String emailSubject = "Automation Letter";
    protected String emailSubjectForDeleting = "Letter For Deleting";
    private String subjectName;
    private final String emailText = "Hello!";


    @Before
    @Step("Set up")
    public void pageSetUp() throws MalformedURLException {
        driver = driverSetUp();
        baseScreen = new BaseScreen(driver);
        permission = new PermissionLayout(driver);
        welcomeGmailScreen = new WelcomeGmailScreen(driver);
        gmailListScreen = new GmailListScreen(driver);
        gmailIncomeScreen = new GmailIncomeScreen(driver);
        createGmailLetterScreen = new CreateGmailLetterScreen(driver);
        gmailNavigationDrawer = new GmailNavigationDrawer(driver);
        gmailTrashScreen = new GmailTrashScreen(driver);
    }

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        public void failed(Throwable e, org.junit.runner.Description description) {
            try {
                getBytesAnnotationWithArgs();
                takeScreenshotToAttachOnAllureReport(driver);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            super.failed(e, description);
        }
    };

    @Test
    @Description("Send Email. WiFi = Off/On")
    @Feature("Gmail")
    @Story("Gmail_SendEmail_WiFi")
    @TmsLink("123456")
    public void SendEmailTest(){
        subjectName = emailSubject;

        if(permission.isPermissionLayoutDisplayed()){
            permission.clickAllowButton();
            welcomeGmailScreen.clickGotItButton();
            gmailListScreen.clickTakeMeToGmailButton();
        }

        gmailIncomeScreen.clickGotItButton();
        //turn off internet
        driver.toggleWifi();
        driver.toggleData();
        gmailIncomeScreen.clickComposeButton();
        createGmailLetterScreen.sendEmail(emailAddress, emailSubject, emailText);
        var expectedTime = LocalTime.now(ZoneId.of("Poland")).format(DateTimeFormatter.ofPattern("h:mm a"));
        gmailIncomeScreen.openGmailNavigationDrawer();
        gmailNavigationDrawer.openPrimaryFolder();

        //verify: 'Queued' displayed
        Assert.assertTrue(String.format("Sender text '%s' is displayed without 'Queued' status.",gmailIncomeScreen.getSenderText()),gmailIncomeScreen.getSenderText().contains("Queued"));

        //turn on Internet
        driver.toggleWifi();
        driver.toggleData();
        gmailIncomeScreen.openGmailNavigationDrawer();
        gmailNavigationDrawer.openPrimaryFolder();
        Assert.assertNotNull("Subject Text is not found.",gmailIncomeScreen.findLetterBySubjectText(emailSubject));
        Assert.assertEquals(String.format("Email Text is not expected. Actual: '%s'", gmailIncomeScreen.getSnippetText()),gmailIncomeScreen.getSnippetText(),emailText);
        Assert.assertEquals("Email Sender is not expected.",gmailIncomeScreen.getSenderText(),"me");
        var actualTime = gmailIncomeScreen.getTimeText();
        Assert.assertEquals("Actual Time is wrong.",expectedTime, actualTime);
    }

    @Test
    @Description("Delete Email")
    @Feature("Gmail")
    @Story("Gmail_DeleteEmail")
    @TmsLink("123457")
    public void DeleteEmailTest(){
        subjectName = emailSubjectForDeleting;
        if(permission.isPermissionLayoutDisplayed()){
            permission.clickAllowButton();
            welcomeGmailScreen.clickGotItButton();
            gmailListScreen.clickTakeMeToGmailButton();
        }

        gmailIncomeScreen.clickGotItButton();
        gmailIncomeScreen.clickComposeButton();
        createGmailLetterScreen.sendEmail(emailAddress, emailSubjectForDeleting, emailText);
        gmailIncomeScreen.openGmailNavigationDrawer();
        gmailNavigationDrawer.openPrimaryFolder();
        gmailIncomeScreen.deleteLetter(emailSubjectForDeleting);
        Assert.assertNull("Letter is not deleted.",gmailIncomeScreen.IncomeScreen_Subject_Label(emailSubjectForDeleting));

        gmailIncomeScreen.openGmailNavigationDrawer();
        gmailNavigationDrawer.openTrashFolder();
        gmailTrashScreen.findLetterBySubjectText(emailSubjectForDeleting);
        Assert.assertNotNull("Letter is not in Trash Folder.", gmailTrashScreen.IncomeScreen_Subject_Label(emailSubjectForDeleting));

    }

    @After
    @Step("Tear down")
    public void driverTearDown(){
        ConnectionState state = driver.getConnection();

        if (!state.isWiFiEnabled()){
            driver.toggleWifi();
            driver.toggleData();
        }

        baseScreen.openGmailNavigationDrawer();
        gmailNavigationDrawer.openPrimaryFolder();

        if(gmailIncomeScreen.IncomeScreen_Subject_Label(subjectName) != null){
            gmailIncomeScreen.deleteLetter(subjectName);
        }

        baseScreen.openGmailNavigationDrawer();
        gmailNavigationDrawer.openTrashFolder();
        gmailTrashScreen.clearTrashFolder();
    }
}
