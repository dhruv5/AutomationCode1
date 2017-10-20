package com.edureka.leadProcess;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.edureka.pages.DashboardPage;
import com.edureka.pages.SelectedCoursePage;
import com.edureka.pages.SignInModalPage;
import com.edureka.pages.UserHomePage;
import com.edureka.util.DriverTestCase;

public class NewTest extends DriverTestCase {
    private DashboardPage dashboardPage;
    private SelectedCoursePage selectedCoursePage;
    private SignInModalPage signInModalPage;
    private UserHomePage userHomePage;

    static String username;
    static String phoneNum;
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
    static String email;
    static String password;
   
	@Test
  public void test_022WatchSampleClassRecordingOnAnyCourseLandingPage() throws Exception {

	        try {

	        	// Navigate to app url
	            addLog("Navigate to the Edureka application url");
	            dashboardPage = applicationSetupForLead();

	            // Verify Edureka Dashboard Page
	            addLog("Verify Edureka Dashboard Page");
	            dashboardPage=  dashboardPage.verifyDashboard();

	            // Click on Sign in button
	            addLog("Click on Sign in button");
	            signInModalPage = dashboardPage.clickSignInHeader();

	         // Verify Login is selected as default
	            addLog("Verify Login is selected as default");
	            signInModalPage = signInModalPage.verifyLoginIsDefault();

	            // click on Sign up link
	            addLog("click on Sign up link");
	            signInModalPage=signInModalPage.clickSignUp();

	            // Sign up with RUNTIME Generated User
	            String edurekaDomain = propertyReader.readApplicationFile("EdurekaDomain");
	            addLog("Sign up user");
	            userHomePage= signInModalPage.signUp(UserHomePage.class, edurekaDomain);
	            username = signInModalPage.RuntimeUserName();
	            email  = signInModalPage.RuntimeUserEmail();
	            password = signInModalPage.RuntimeUserPassword();
	            phoneNum = signInModalPage.RuntimeUserPhNum();
	            System.out.println("User Email Address is : " + email);
	            System.out.println("Password is : " + password);
	            
	            // Verify User Home Page for RUNTIME Generated User
	            addLog("Verify User Home Page");
	            userHomePage= userHomePage.verifyRandUserLoggedIn(username);


	            //Select a course and Verify if the course is selected
	            addLog("Select Course");
	            selectedCoursePage=dashboardPage.selectCourse_Trending();
	            String courseName = dashboardPage.getCourseName_TrendingCourse();
	            System.out.println("Course which is selected: " +courseName);
	            String slug = dashboardPage.getSlug();


	            // Click on Play Button of Video
	            addLog("Click on Play Button of Video");
	            selectedCoursePage=selectedCoursePage.playClassRecording(SelectedCoursePage.class);

	            // Verify Data in User Table
	            addLog("Verify Data in User Table");
	            selectedCoursePage= selectedCoursePage.dataVerificationInUserTable("1",email,username,phoneNum);
	            
	            // Verify Data in User Lead Table
	         // Verify Data in User Lead Table
	            // Write a query for this using the Runtime Generated User Data.
	            String course_id = selectedCoursePage.getCourseID(slug);
	            System.out.println(course_id);
	            webSiteAction = "Watch Sample Class Recording";
	            country= propertyReader.readTestData("CountryIndia");
	            campaignSource= propertyReader.readTestData("LeadCampaignSource");
	            campaignName= propertyReader.readTestData("LeadCampaignName");
	            campaignMedium= propertyReader.readTestData("LeadCampaignMedium");
	            addLog("Verify Data in User Lead Table");
	            // Parameter in UserEmail {USERS} to get ID 
	            // Pass that ID {User_Leads} to get 
	            selectedCoursePage= selectedCoursePage.dataVerificationInUser_LeadsTable(course_id,webSiteAction,country,campaignSource, campaignName, campaignMedium,email);

	            // Verify Data in User Course table
	            addLog("Verify Data in User Course table");
	            String course_group = selectedCoursePage.getCourseGroup(slug);
	            String getUserID = selectedCoursePage.getUserID(email);
	            System.out.println("User ID : "+ getUserID);
	            isPaidValue= propertyReader.readTestData("HomePage_Signup_Is_Paid_Value");
	            addLog("Verify Data in User Course table"); 
	            selectedCoursePage= selectedCoursePage.dataVerificationInUser_CoursedTable(course_id,isPaidValue,course_group,getUserID);
	            
	            // Veriy Data in Ambassadors table
	            addLog("Veriy Data in Ambassadors table");
	            selectedCoursePage= selectedCoursePage.dataVerificationInUser_AmbassadorsTable(getUserID);
	        
	            // Verify Data in Completed Queue Jobs table
	/*            String courseStatus = propertyReader.readTestData("Status");
	            String courseProperty = propertyReader.readTestData("Priority");
	*/          selectedCoursePage = selectedCoursePage.dataVerificationInCompleted_Queue_Jobs_Table(course_id,email);  

	        }

	        catch (final Error e) {
	            captureScreenshot("test_022WatchSampleClassRecordingOnAnyCourseLandingPage");
	            throw e;
	        } 
	        catch (final Exception e) {
	            captureScreenshot("test_022WatchSampleClassRecordingOnAnyCourseLandingPage");
	            throw e;
	        }
	    }

  }



