import Screens.*;
import Screens.Layouts.GmailNavigationDrawer;
import Screens.Layouts.PermissionLayout;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.*;
import org.junit.*;
import org.junit.rules.TestWatcher;
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
    private String emailAddress = "natalia.test0328@gmail.com";
    protected String emailSubject = "Automation Letter";
    protected String emailSubjectForDeleting = "Letter For Deleting";
    private String subjectName;
    private String emailText = "Hello!";


    @Before
    @Step("Set up")
    public void pageSetUp() throws MalformedURLException {
        driver = driverSetUp();
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
    @Description("Send Email")
    @Feature("Gmail")
    @Story("Gmail_SendEmail")
    @TmsLink("123456")
    public void SendEmailTest(){
        subjectName = emailSubject;

        if(permission.isPermissionLayoutDisplayed()){
            permission.clickAllowButton();
            welcomeGmailScreen.clickGotItButton();
            gmailListScreen.clickTakeMeToGmailButton();
        }

        gmailIncomeScreen.clickGotItButton();
        gmailIncomeScreen.clickComposeButton();
        createGmailLetterScreen.sendEmail(emailAddress, emailSubject, emailText);
        var expectedTime = LocalTime.now(ZoneId.of("Poland")).format(DateTimeFormatter.ofPattern("h:mm a"));
        gmailIncomeScreen.openGmailNavigationDrawer();
        gmailNavigationDrawer.openPrimaryFolder();
        Assert.assertTrue("Subject Text is not found.",gmailIncomeScreen.findLetterBySubjectText(emailSubject) != null);
        Assert.assertTrue("Email Text is not expected.",gmailIncomeScreen.getSnippetText().equals(emailText));
        Assert.assertTrue("Email Sender is not expected.",gmailIncomeScreen.getSenderText().equals("me"));
        var actualTime = gmailIncomeScreen.getTimeText();
        Assert.assertTrue("Actual Time is wrong.",expectedTime.equals(actualTime));
    }

    @Test
    @Description("Send Email")
    @Feature("Gmail")
    @Story("Gmail_DeleteEmail")
    @TmsLink("123457")
    public void DeleteEmailTest() throws InterruptedException{
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
        Assert.assertTrue("Letter is not deleted.",gmailIncomeScreen.IncomeScreen_Subject_Label(emailSubjectForDeleting) == null);

        gmailIncomeScreen.openGmailNavigationDrawer();
        gmailNavigationDrawer.openTrashFolder();
        gmailTrashScreen.findLetterBySubjectText(emailSubjectForDeleting);
        Assert.assertTrue("Letter is not in Trash Folder.", gmailTrashScreen.IncomeScreen_Subject_Label(emailSubjectForDeleting) != null);

    }

    @After
    @Step("Tear down")
    public void driverTearDown(){
        var baseScreen = new BaseScreen(driver);
        baseScreen.openGmailNavigationDrawer();
        gmailNavigationDrawer.openPrimaryFolder();

        if(gmailIncomeScreen.IncomeScreen_Subject_Label(subjectName) != null){
            gmailIncomeScreen.deleteLetter(subjectName);
        }

        baseScreen.openGmailNavigationDrawer();
        gmailNavigationDrawer.openTrashFolder();
        gmailTrashScreen.clearTrashFolder();
        driver.quit();
    }


}
