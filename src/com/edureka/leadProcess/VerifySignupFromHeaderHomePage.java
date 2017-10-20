package com.edureka.leadProcess;

import org.testng.annotations.Test;

import com.edureka.pages.DashboardPage;
import com.edureka.pages.SignInModalPage;
import com.edureka.pages.UserHomePage;
import com.edureka.util.DriverTestCase;

public class VerifySignupFromHeaderHomePage extends DriverTestCase{
    private DashboardPage dashboardPage;
    private UserHomePage userHomePage;
    private SignInModalPage signInModalPage;
    
    static String email;
    static String password;
    static String campaignSource;
    static String campaignName;
    static String campaignMedium;
    static String event_Type;

    @Test
    public void test_001VerifySignupFromHeaderHomePage() throws Exception {

        try {
            // Navigate to app url
            addLog("Navigate to the Edureka application url");
            dashboardPage = applicationSetup();

            // Verify Edureka Dashboard Page
            addLog("Verify Edureka Dashboard Page");
            dashboardPage=  dashboardPage.verifyDashboard();

            // Click on Signin Navigation Header
            addLog("Click on SignIn Navigation header");
            signInModalPage = dashboardPage.clickSignInHeader();

            // Verify Login is selected as default
            addLog("Verify Login is selected as default");
            signInModalPage = signInModalPage.verifyLoginIsDefault();

            // click on Sign up link
            addLog("click on Sign up link");
            signInModalPage=signInModalPage.clickSignUp();

            // Sign up user
            String edurekaDomain = propertyReader.readApplicationFile("EdurekaDomain");
            addLog("Sign up user");
            userHomePage= signInModalPage.signUp(UserHomePage.class, edurekaDomain);
            String email = signInModalPage.RuntimeUserEmail();
            String username = signInModalPage.RuntimeUserName();
            String phoneNum = signInModalPage.RuntimeUserPhNum();
            System.out.println(email);
                         

            // Verify Data in User Table
            addLog("Verify Data in User Table");
            userHomePage= userHomePage.dataVerificationInUserTable("1",email,username,phoneNum);

            // Verify Data in User Lead Table
            String course__Id = propertyReader.readTestData("HomePage_Signup_Course_ID");
            String webSiteAction = propertyReader.readTestData("HomePage_Signup_WebSite_Action");
            String country= propertyReader.readTestData("CountryIndia");
            campaignSource= propertyReader.readTestData("CampaignSource");
            campaignName= propertyReader.readTestData("CampaignName");
            campaignMedium= propertyReader.readTestData("CampaignMedium");
            addLog("Verify Data in User Lead Table");
            userHomePage= userHomePage.dataVerificationInUser_LeadsTable(course__Id,webSiteAction,country,campaignSource, campaignName, campaignMedium,email);

            // Verify Data in User Course table
            String getUserID = signInModalPage.getUserID(email);
            String isPaidValue= propertyReader.readTestData("HomePage_Signup_Is_Paid_Value");
            addLog("Verify Data in User Course table");
            userHomePage= userHomePage.dataVerificationInUser_CoursedTable(course__Id,isPaidValue,course__Id,getUserID);

            // Verify Data in User Event Table
            String campaign_Values= propertyReader.readTestData("UTM_campaign");
            event_Type=propertyReader.readTestData("EventType");
            addLog("Verify Data in User Event Table");
            userHomePage= userHomePage.dataVerificationInUser_EventTable(course__Id,webSiteAction,campaign_Values, event_Type,getUserID);

            // Veriy Data in Ambassadors table
        //    String level_id = propertyReader.readTestData("HomePage_Signup_level_id");
            addLog("Veriy Data in Ambassadors table");
            userHomePage= userHomePage.dataVerificationInUser_AmbassadorsTable(getUserID);

         // Verify Data in Completed Queue Jobs table
            /*String courseStatus = propertyReader.readTestData("Status");
            String courseProperty =propertyReader.readTestData("Priority");*/
            userHomePage = userHomePage.dataVerificationInCompleted_Queue_Jobs_Table(course__Id,email);

            
/*            // Click on Profile Dropdown
            String userName = propertyReader.readRunTimeData("UserName");
            addLog("Click on Profile Dropdown");
            userHomePage=userHomePage.clickOnProfileDropdown(userName);            
            
            // Logout from the application
            addLog("Logout from the application.");
            dashboardPage = userHomePage.logout();            
*/
        }
        catch (final Error e) {
            captureScreenshot("test_001VerifySignupFromHeaderHomePage");
            throw e;
        } catch (final Exception e) {
            captureScreenshot("test_001VerifySignupFromHeaderHomePage");
            throw e;
        }
    }
}