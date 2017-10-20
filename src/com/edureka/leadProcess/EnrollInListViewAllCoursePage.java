package com.edureka.leadProcess;

import org.testng.annotations.Test;

import com.edureka.pages.AllCoursesPage;
import com.edureka.pages.DashboardPage;
import com.edureka.pages.OrderSummaryPage;
import com.edureka.pages.SelectedCoursePage;
import com.edureka.pages.SignInModalPage;
import com.edureka.pages.UserHomePage;
import com.edureka.util.DriverTestCase;

public class EnrollInListViewAllCoursePage extends DriverTestCase{
    private DashboardPage dashboardPage;
    private SelectedCoursePage selectedCoursePage;
    private SignInModalPage signInModalPage;
    private AllCoursesPage allCoursesPage;
    private UserHomePage userHomePage;
    private OrderSummaryPage orderSummaryPage;

    static String coursename;
    static String course__Id ;
    static String webSiteAction;
    static String course_Group;
    static String isPaidValue;
    static String campaign_Values;
    static String level_id;
    static String event;
    static String country;
    static String campaignSource;
    static String campaignName;
    static String campaignMedium;
    static String event_Type;

    @Test
    public void test_026EnrollInListViewAllCoursePage() throws Exception {

        try {

            // Navigate to app url
            addLog("Navigate to the Edureka application url");
            dashboardPage = applicationSetupForLead();

            // Click on Sign in button
            addLog("Click on Sign in button");
            signInModalPage = dashboardPage.clickSignInHeader();
            
            addLog("click on Sign up link");
            signInModalPage=signInModalPage.clickSignUp();
            
            String edurekaDomain = propertyReader.readApplicationFile("EdurekaDomain");
            addLog("Sign up user");
            userHomePage= signInModalPage.signUp(UserHomePage.class, edurekaDomain);
            String username = signInModalPage.RuntimeUserName();
            String email = signInModalPage.RuntimeUserEmail();
            String password = signInModalPage.RuntimeUserPassword();
            String phoneNum = signInModalPage.RuntimeUserPhNum();
            
            // Verify User Home Page
            addLog("Verify User Home Page");
            userHomePage=userHomePage.verifyRandUserLoggedIn(username);
            
            addLog("Click on Profile Dropdown");
            userHomePage=userHomePage.clickOnProfileDropdown();  
               
            // Logout Application
            addLog("Logout Application");
            dashboardPage=userHomePage.logout();
            
            addLog("Click on Sign in button");
            signInModalPage = dashboardPage.clickSignInHeader();
         // Sign in user

            /*email = propertyReader.readRunTimeData("Test_Email_Id");
            password = propertyReader.readRunTimeData("Test_Password");
            */
            addLog("Sign in user");
            signInModalPage= signInModalPage.enterEmailAndPassword(email, password);
            
            // Click on Start Learning button
            addLog("Click on Start Learning button");
            userHomePage = signInModalPage.clickStartLearningButton(UserHomePage.class);

            // Verify User Page
            addLog("Verify User Page");
            userHomePage=userHomePage.verifyRandUserLoggedIn(username);

            // Click on Course Tab
            String allCourse = propertyReader.readTestData("AllCourse");
            addLog("Click on Course Tab");
            allCoursesPage= userHomePage.selectCourseOption(AllCoursesPage.class, allCourse);

            // Verify All Courses page
            addLog("Verify All Courses page");
            allCoursesPage= allCoursesPage.verifyAllCoursesPage();

            // Click on List view Icon
            addLog("Click on List view Icon");
            allCoursesPage=allCoursesPage.clickOnListViewIcon();

            // Select Course From List View
            coursename= propertyReader.readTestData("ApacheSpark&Scala");
            addLog("Select Course From List View");
            selectedCoursePage= allCoursesPage.selectCourseFromListView(coursename);

            // Verify Selected Course Page Is Open
            addLog("Verify Selected Course Page Is Open");
            selectedCoursePage= selectedCoursePage.verifySelectedCoursePage(coursename);

            // Click on Add To Wishlist Icon
            addLog("Click on Add To Wishlist Icon");
            orderSummaryPage= selectedCoursePage.clickOnEnrollButton(OrderSummaryPage.class);

            // Verify Order Summary Page
            addLog("Verify Order Summary Page");
            orderSummaryPage =orderSummaryPage.verifyPage();

            // Verify Data in user table
            addLog("Verify Data in user table");
            orderSummaryPage = orderSummaryPage.dataVerificationInUserTable("1",email,username,phoneNum);
             
            String getUserID = selectedCoursePage.getUserID(email);
            
            // Verify Data in User Lead Table
            course__Id= propertyReader.readTestData("Course_ID_ApacheSpark&Scala");
            webSiteAction = propertyReader.readTestData("Event_Enroll");
            country= propertyReader.readTestData("CountryIndia");
            campaignSource= propertyReader.readTestData("LeadCampaignSource");
            campaignName= propertyReader.readTestData("LeadCampaignName");
            campaignMedium= propertyReader.readTestData("LeadCampaignMedium");
            addLog("Verify Data in User Lead Table");
            orderSummaryPage= orderSummaryPage.dataVerificationInUser_LeadsTable(course__Id,webSiteAction, country,campaignSource,campaignName,campaignMedium,email);

            // Verify Data in User Course table
            course_Group = propertyReader.readTestData("Course_Group_ApacheSpark&Scala");
            isPaidValue= propertyReader.readTestData("HomePage_Signup_Is_Paid_Value");
            addLog("Verify Data in User Course table");
            orderSummaryPage= orderSummaryPage.dataVerificationInUser_CoursedTable(course__Id,isPaidValue,course_Group,getUserID);

            // Verify Data in User Event Table
            String event_Context = propertyReader.readTestData("Event_Enroll");
            campaign_Values= propertyReader.readTestData("LeadCampaignName");
            event_Type=propertyReader.readTestData("Enorll_Event_Type");
            addLog("Verify Data in User Event Table");
            orderSummaryPage= orderSummaryPage.dataVerificationInUser_EventTable(course__Id,event_Context,campaign_Values, event_Type,getUserID);
              
            // Veriy Data in Ambassadors table
            level_id = propertyReader.readTestData("HomePage_Signup_level_id");
            addLog("Veriy Data in Ambassadors table");
            orderSummaryPage= orderSummaryPage.dataVerificationInUser_AmbassadorsTable(getUserID);

        }

        catch (final Error e) {
            captureScreenshot("test_026EnrollInListViewAllCoursePage");
            throw e;
        } 
        catch (final Exception e) {
            captureScreenshot("test_026EnrollInListViewAllCoursePage");
            throw e;
        }
    }
    @Test(dependsOnMethods={"test_026EnrollInListViewAllCoursePage"})
    public void test_027VerifyEachAndEveryElementOnOrderSummaryPage() throws Exception {

        try {
/*            // Verify Currency, Discount, Service tax, Total on Order Summary page
            String currency= propertyReader.readTestData("INRCurrency");
            addLog("Verify Currency, Discount, Service tax, Total on Order Summary page");
            orderSummaryPage=orderSummaryPage.verifyTotalAmount(currency);

            // Verify TimeZone
            addLog("Verify TimeZone");
            orderSummaryPage=orderSummaryPage.veriyTimeZone();

            // Verify Payment modes
            addLog("Verify Payment modes");
            orderSummaryPage=orderSummaryPage.verifyPaymentModes();
*/        }

        catch (final Error e) {
            captureScreenshot("test_027VerifyEachAndEveryElementOnOrderSummaryPage");
            throw e;
        } 
        catch (final Exception e) {
            captureScreenshot("test_027VerifyEachAndEveryElementOnOrderSummaryPage");
            throw e;
        }
    }
}