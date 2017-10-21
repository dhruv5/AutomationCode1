package com.edureka.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.edureka.locators.LocatorReader;
import com.edureka.util.Database;
import com.edureka.util.DriverHelper;

public class OrderSummaryPage extends DriverHelper {

	String tableName = "users";
	String columnNameToBeFetched = "id";
	String columnNameToBeMatched = "email";
	String tableName3 = "pre_orders";
	String columnNameToBeFetched4 = "priceinr";
	String columnNameToBeFetched5 = "servicetaxinr";
	String columnNameToBeFetched6 = "discountinr";
	String columnNameToBeFetched7 = "totalinr";
	String columnNameToBeFetched8 = "servicetaxusd";

	private final LocatorReader oderSummaryLocator;
	private final DatabaseVerifications databaseVerification;

	public OrderSummaryPage(WebDriver driver) {
		super(driver);
		oderSummaryLocator = new LocatorReader("OrderSummary.xml");
		databaseVerification = new DatabaseVerifications(driver);
	}

	/**
	 *  Verify Order Summary page
	 * @return
	 */
	public OrderSummaryPage verifyPage(){
		String pageHeader =oderSummaryLocator.getLocator("PageHeader");
		WaitForElementPresent(pageHeader, getTimeOut());
		String pageName = getText(pageHeader);
		Assert.assertTrue(pageName.contains("Order Summary"));
		
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}
	
	public String getCourseName(){
		String course_name = driver.findElement(By.cssSelector(".ordcourdetailh4")).getText();
		System.out.println("CourseName : "+course_name);
		return course_name;
	}
	public String getCourseDuration(){
		String course_duration = driver.findElement(By.cssSelector(".borderbot.ordcourdetail>div:nth-child(3)>span:nth-child(1)")).getText();
		System.out.println("Course_Duration : "+course_duration);
		return course_duration;
	}	
	public String getCourseTotalCost(){
		WebElement a = driver.findElement(By.id("total_price"));
		_waitForJStoLoad();
		waitForWebElementPresent(a);
		String course_totalcost = a.getText();
		System.out.println("Course_Total : "+course_totalcost);
		return course_totalcost;
	}	
	public String getCourseTimeDuration(){
		String course_timeduration = driver.findElement(By.cssSelector(".borderbot.ordcourdetail>div:nth-child(3)>span:nth-child(3)")).getText();
		System.out.println("Course_TimeDuration : "+course_timeduration);
        return course_timeduration;
	}
	public String getCourseDate(){
		String course_date = driver.findElement(By.cssSelector(".ordersummdeatil.whitebg.clearfix>div:nth-child(2)>div>span:nth-child(2)>p")).getText();
		System.out.println("CourseDate : "+course_date);
		return course_date;
	}
	
	/**
	 *  Verify total Amount for INR is equal to Net price + Service Tax
	 * @return
	 */
	public OrderSummaryPage verifyTotalAmount(String currency){
        
		String price = oderSummaryLocator.getLocator("Price");
		String totalAmount = oderSummaryLocator.getLocator("TotalAmount");
		String netPrice = oderSummaryLocator.getLocator("NetPrice");
		String discount =oderSummaryLocator.getLocator("Discount");
		String serviceTax = oderSummaryLocator.getLocator("ServiceTax");
		String discountRate = oderSummaryLocator.getLocator("Discount");

		int netPriceValue = 0;
		int totalAmountValue=0;
		int serviceTaxValue=0;
		int priceAfterDiscont=0;
		int discountValue=0;
		int totalAmountVal_3=0;
		WaitForElementPresent(price, getTimeOut());
		String priceAmount= getText(price);
		String priceVal= priceAmount.replace(",","");
		String priceVal_2= priceVal.trim();
		int pirceValue = Integer.parseInt(priceVal_2);
		propertyReader.updatePropertyTestData("PriceValue", priceVal);

		if (isElementPresent(discountRate))
		{
			String discountAmount= getText(discount);
			String discountVal_1= discountAmount.replace(",", "");
			String discountVal_2= discountVal_1.replace("-", "");
			String discountVal_3=discountVal_2.trim();
			discountValue = Integer.parseInt(discountVal_3);

			priceAfterDiscont= pirceValue-discountValue;

			String netPriceAmount = getText(netPrice);
			String netPriceaVal= netPriceAmount.replace(",","");
			String netPriceaVal_1= netPriceaVal.trim();
			netPriceValue = Integer.parseInt(netPriceaVal_1);
			Assert.assertEquals(priceAfterDiscont, netPriceValue);
		}
		if (currency.equalsIgnoreCase("INR"))
		{
			String serviceTaxAmount= getText(serviceTax);
			String serviceTaxVal_1= serviceTaxAmount.replace(",","");
			String serviceTaxVal_2=serviceTaxVal_1.trim();
			System.out.println("Service tax value is :"+serviceTaxVal_2);
			if (serviceTaxVal_2=="" || serviceTaxVal_2.trim().equals("")) 
			{

			}
		else{
				System.out.println("I am in else");
				serviceTaxValue= Integer.parseInt(serviceTaxVal_2);
			}


		 //propertyReader.updatePropertyTestData("ServiceTax",serviceTaxVal_2);
			if (isElementPresent(discountRate)){
				totalAmountValue= netPriceValue+serviceTaxValue;
			}
		}
		String netPriceAmount = getText(netPrice);
		String netPriceaVal= netPriceAmount.replace(",","");
		String netPriceaVal_1= netPriceaVal.trim();
		netPriceValue = Integer.parseInt(netPriceaVal_1);
		Assert.assertEquals(priceAfterDiscont, netPriceValue);
		WaitForElementPresent(totalAmount, getTimeOut());
		String amount= getText(totalAmount);
		String totalAmountVal_1= amount.replace(",","");
		String totalAmountVal_2 =totalAmountVal_1.trim();
		totalAmountVal_3= Integer.parseInt(totalAmountVal_2);
		System.out.println("totalAmountVal_3  :::"+totalAmountVal_3);
		System.out.println("Total Amount :::"+totalAmountValue);
		if (currency.equalsIgnoreCase("INR")){
			Assert.assertEquals(totalAmountValue, totalAmountVal_3);
		}
		else  
		{
			if (isElementPresent(discountRate))
			{
				Assert.assertEquals(priceAfterDiscont, totalAmountVal_3);
			}
			else
			{
				Assert.assertEquals(pirceValue, totalAmountVal_3);
			}
		}

		System.out.println("Discount Value :::"+discountValue);
		propertyReader.updatePropertyTestData("TotalAmount",Integer.toString(totalAmountVal_3));
		propertyReader.updatePropertyTestData("DiscountValue",Integer.toString(discountValue));

		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 *  Verifying INR Currency has Service Tax Column
	 * @return
	 * @throws Exception
	 */

	public OrderSummaryPage verifyINRCurrency() throws Exception{
		String tBuserEmailId = propertyReader.readRunTimeData("Email_Id");
		Database dbObject = new Database();
		String userId = dbObject.getRecord(tableName, columnNameToBeFetched, columnNameToBeMatched, tBuserEmailId);
		String userTotalPrice = dbObject.getRecord(tableName3, columnNameToBeFetched4, "userid", userId);
		int totalPriceInINT = Integer.parseInt(userTotalPrice);
		String userServiceTax = dbObject.getRecord(tableName3, columnNameToBeFetched5, "userid", userId);
		int serviceTaxInINT = Integer.parseInt(userServiceTax);
		String userDiscountPrice = dbObject.getRecord(tableName3, "discountinr", "userid", userId);
		int discountPriceInINT = Integer.parseInt(userDiscountPrice);
		String userFinalPriceToPay = dbObject.getRecord(tableName3, columnNameToBeFetched7, "userid", userId);
		int finalPriceToPayInINT = ( (totalPriceInINT-discountPriceInINT) + (serviceTaxInINT) );
		String  finalPriceToPayInString = String.valueOf(finalPriceToPayInINT);
		if(finalPriceToPayInString.equals(userFinalPriceToPay)){
			System.out.println("Verified Currency From INR");
		}
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 *  Verifying USD Currency without Service Tax Column
	 */
	public OrderSummaryPage verifyUSDCurrency() throws Exception{
		String tBuserEmailId = propertyReader.readRunTimeData("Email_Id");
		String tUSDCurrency = propertyReader.readRunTimeData("Total_USD");
		Database dbObject = new Database();
		String userId = dbObject.getRecord(tableName, columnNameToBeFetched, columnNameToBeMatched, tBuserEmailId);
		String userServiceTaxInUSD = dbObject.getRecord(tableName3, columnNameToBeFetched8, "userid", userId);
		if(tUSDCurrency.equals(userServiceTaxInUSD)){
			System.out.println("Verified Currency From USD");
		}
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}
	/**
	 *  Verify User name
	 * @return
	 */
	public OrderSummaryPage verifySignUpSuccessfully() {
		String pageName =oderSummaryLocator.getLocator("ProfileDropdown.UserName");
		WaitForElementPresent(pageName,20);
		WebElement userNameHeader = driver.findElement(By.xpath(pageName));
		String tBuserName = propertyReader.readRunTimeData("UserName");
		String pageheaderText = userNameHeader.getText();
		Assert.assertTrue(pageheaderText.equalsIgnoreCase(tBuserName));
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 *  Verify Data in user Table
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage dataVerificationInUserTable(String emailId,String userName,String phone) throws Exception {
		OrderSummaryPage orderSummary;
		orderSummary = (OrderSummaryPage)databaseVerification.dataVerificationInUserTable(OrderSummaryPage.class,emailId,userName,phone);
		return orderSummary;
	}

	/**
	 *  Verify Data in user Table
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage dataVerificationInUserTable(String custID,String EmailID, String Username,String PhoneNum) throws Exception {
		OrderSummaryPage orderSummary;
		orderSummary = (OrderSummaryPage)databaseVerification.dataVerificationInUserTable(OrderSummaryPage.class,custID,EmailID,Username,PhoneNum);
		return orderSummary;
	}

	/**
	 * Database verification for referred user in User Table
	 * @param custID
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage dataVerificationForReferredUserInUserTable(String custID,String emailId) throws Exception {
		OrderSummaryPage orderSummary;
		orderSummary = (OrderSummaryPage)databaseVerification.dataVerificationForReferredUserInUserTable(OrderSummaryPage.class,custID,emailId);
		return orderSummary;
	}

	/**
	 *  Verify Data in User_Leads Table
	 * @param courseID
	 * @param webSiteAction
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage dataVerificationInUser_LeadsTable(String courseId, String webSiteAction, String country,String campaignSource, String campaignName, String campaignMedium,String email) throws Exception {
		OrderSummaryPage orderSummary;
		orderSummary = (OrderSummaryPage)databaseVerification.dataVerificationInUserLeadTable(OrderSummaryPage.class, courseId, webSiteAction, country, campaignSource, campaignName,campaignMedium,email);
		return orderSummary;
	}

	/**
	 *  Verify Data in User Courses Table
	 * @param courseId
	 * @param isPaidValue
	 * @param courseGroup
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage dataVerificationInUser_CoursedTable(String courseId, String isPaidValue, String courseGroup,String UserID) throws Exception {
		OrderSummaryPage orderSummary;
		orderSummary = (OrderSummaryPage)databaseVerification.dataVerificationInUser_Course_Table(OrderSummaryPage.class, courseId, isPaidValue, courseGroup,UserID);
		return orderSummary;
	}

	/**
	 *  Verify Data in user Events Table
	 * @param courseId
	 * @param event_context
	 * @param utm_source
	 * @param utm_campaign
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage dataVerificationInUser_EventTable(String courseId, String event_context,String utm_campaign,String event_Type,String UserID) throws Exception {
		OrderSummaryPage orderSummary;
		orderSummary = (OrderSummaryPage)databaseVerification.dataVerificationInUser_Events_Table(OrderSummaryPage.class, courseId, event_context, utm_campaign, event_Type,UserID);
		return orderSummary;
	}

	/**
	 *  Verify Data in Ambassadors Table
	 * @param level_id
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage dataVerificationInUser_AmbassadorsTable(String UserID) throws Exception {
		OrderSummaryPage orderSummary;
		orderSummary = (OrderSummaryPage)databaseVerification.dataVerificationInAmbassadors(OrderSummaryPage.class,UserID);
		return orderSummary;
	}

	/**
	 *  Database verification in Pre_Order table When currency is INR
	 * @param course_Id
	 * @param courseTitle
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage dataVerificationInUser_PreOrderTableForINR(String course_Id, String course_price,String course_servicetax,String UserID) throws Exception {
		OrderSummaryPage orderSummary;
		orderSummary = (OrderSummaryPage)databaseVerification.dataVerificationInPreOrderTableForINR(OrderSummaryPage.class, course_Id,course_price,course_servicetax,UserID);
		return orderSummary;
	}

	/**
	 *  Database verification in Pre_Order table When currency is USD
	 * @param course_Id
	 * @param courseTitle
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage dataVerificationInUser_PreOrderTableForUSD(String course_Id, String course_price,String UserID) throws Exception {
		OrderSummaryPage orderSummary;
		orderSummary = (OrderSummaryPage)databaseVerification.dataVerificationInPreOrderTableForUSD(OrderSummaryPage.class, course_Id,course_price,UserID);
		return orderSummary;
	}
    
	public String getUserID(String Email) throws Exception {
		Database d = new Database();
		String id = d.getRecord("users","id","email",Email);
		System.out.println("User_id of "+Email+" : "+id);
		return id;
	}
	
	/**
	 *  Click on Profile Dropdown
	 * @param userName
	 * @return
	 */
	public OrderSummaryPage clickOnProfileDropdown(String userName){
		String dropdown ="//span[contains(text(),'"+userName+"')]";
		WaitForElementPresent(dropdown, getTimeOut());
		clickOn(dropdown);
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 *  Logout application
	 * @return
	 */
	public DashboardPage logout(){
		String logout=oderSummaryLocator.getLocator("ProfileDropdown.LTLogout");;
		WaitForElementPresent(logout, getTimeOut());
		mouseOver(logout);
		clickOn(logout);
		return PageFactory.initElements(driver, DashboardPage.class);
	}

	/**
	 *  Verify TimeZone
	 * @return
	 */
	public OrderSummaryPage veriyTimeZone(){
		String timeZone=oderSummaryLocator.getLocator("TimeZone");;
		WaitForElementPresent(timeZone, getTimeOut());
		Assert.assertTrue(isElementPresent(timeZone));
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 *  Verify Payment modes
	 * @return
	 */
	public OrderSummaryPage verifyPaymentModes(){
		String debitAndCreditCard=oderSummaryLocator.getLocator("PaymentModeDebitAndCreditCard");;
		WaitForElementPresent(debitAndCreditCard, getTimeOut());
		Assert.assertTrue(isElementPresent(debitAndCreditCard));

		String paypal=oderSummaryLocator.getLocator("Paypal.PaymentModePaypal");;
		WaitForElementPresent(paypal, getTimeOut());
		Assert.assertTrue(isElementPresent(paypal));

		String netBanking =oderSummaryLocator.getLocator("PaymentModeNetBanking");;
		WaitForElementPresent(netBanking, getTimeOut());
		Assert.assertTrue(isElementPresent(netBanking));
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 *  Change Country
	 * @param countryName
	 * @return
	 */
	public OrderSummaryPage changeCountry(String countryName, String currencySign) throws InterruptedException{
		_waitForJStoLoad();
		String ddCountry = oderSummaryLocator.getLocator("Country.DDCountry");
		WaitForElementPresent(ddCountry, getTimeOut());
		clickOn(ddCountry);
Thread.sleep(2000);
	    //_waitForJStoLoad();
		String tbCountry= oderSummaryLocator.getLocator("Country.TBCountry");
		WaitForElementClickable(tbCountry, getTimeOut());
		WaitForElementPresent(tbCountry, getTimeOut());
		Thread.sleep(2000);
		sendKeysByKeyboard(tbCountry, countryName);
		_waitForJStoLoad();

		String country = "//div[@class='dropdown-menu open']/ul/div/div/li/a/span[contains(text(),'"+countryName+"')]";
		WaitForElementClickable(country,getTimeOut());
		WaitForElementPresent(country, getTimeOut());
		clickOn(country);
Thread.sleep(2000);
		_waitForJStoLoad();
		String currency ="//span[text()='"+currencySign+"']";
		WaitForElementPresent(currency,getTimeOut());
		WaitForElementVisible(currency,getTimeOut()); 
		Assert.assertTrue(isElementPresent(currency));
		return PageFactory.initElements(driver, OrderSummaryPage.class);

	}

	/**
	 *  Change Currency 
	 * @param currencyName
	 * @return
	 * @throws InterruptedException
	 */
	public OrderSummaryPage changeCurrency(String currencyName) throws InterruptedException{
		String ddCurrency = oderSummaryLocator.getLocator("Country.DDCurrency");
		WaitForElementPresent(ddCurrency, getTimeOut());
		clickOn(ddCurrency);

		String currency ="//li[@data-original-index='"+currencyName+"']";
		WaitForElementPresent(currency, 20);
		Assert.assertTrue(isElementPresent(currency));
		clickOn(currency);
		Thread.sleep(5000);
		return PageFactory.initElements(driver, OrderSummaryPage.class);

	}

	/**
	 * Click on Paypal Link
	 * @return
	 * @throws InterruptedException 
	 */
	public PayPalPage clickOnPaypalLink() throws InterruptedException{
		Thread.sleep(5000);
		String paypal=oderSummaryLocator.getLocator("Paypal.PaymentModePaypal");;
		WaitForElementVisible(paypal, 30);
		clickOn(paypal);

		String btnPaySecurely=oderSummaryLocator.getLocator("Paypal.BTNPaypalPaymentSecurely");;
		WaitForElementPresent(btnPaySecurely, getTimeOut());
		clickOn(btnPaySecurely);

		String imgLoading = oderSummaryLocator.getLocator("ImgLoading");
		WaitForElementNotVisible(imgLoading, getTimeOut());
		return PageFactory.initElements(driver, PayPalPage.class);
	}

	/**
	 * Click on Paypal Link
	 * @return
	 * @throws InterruptedException 
	 */
	public PayPalPage clickOnPaypalLink_New() throws InterruptedException{
		Thread.sleep(5000);
		String paypal=oderSummaryLocator.getLocator("Paypal.PaymentModePaypal");;
		WaitForElementVisible(paypal, 30);
		clickOn(paypal);

		String btnPaySecurely=oderSummaryLocator.getLocator("Paypal.BTNPaypalPaymentSecurely");;
		WaitForElementPresent(btnPaySecurely, getTimeOut());
		Thread.sleep(5000);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("submit_paypal_form();");

		String imgLoading = oderSummaryLocator.getLocator("ImgLoading");
		WaitForElementNotVisible(imgLoading, getTimeOut());
		return PageFactory.initElements(driver, PayPalPage.class);
	}

	/**
	 * Click on Pay Securely Link
	 * @return
	 */
	public RazorPayPage clickOnRazorPaySecurelyButton(){
		String btnPaySecurely=oderSummaryLocator.getLocator("BTNRazorPaySecurely");;
		WaitForElementPresent(btnPaySecurely, getTimeOut());
		clickOn(btnPaySecurely);
		return PageFactory.initElements(driver, RazorPayPage.class);
	}

	/**
	 *  Verify Time zone changes according to country 
	 * @param timeZone
	 * @return
	 * @throws InterruptedException
	 */
	public OrderSummaryPage verifyChangedTimeZoneWithCountry(String timeZone) {
		String countryTimeZone = oderSummaryLocator.getLocator("Country.CountryTimeZone");
		WaitForElementPresent(countryTimeZone, getTimeOut());
		String timeZoneText = getText(countryTimeZone);
		System.out.println("Time Zone Text"+timeZoneText);
		System.out.println("Time Zone"+timeZone);
		Assert.assertTrue(timeZoneText.contains(timeZone));
		return PageFactory.initElements(driver, OrderSummaryPage.class);

	}

	/**
	 *  Verify URL link Order Summary page 
	 * @return
	 */
	public OrderSummaryPage verifyUrlLink(){
		String urlLink = oderSummaryLocator.getLocator("UrlTechTeam");
		WaitForElementPresent(urlLink, getTimeOut());
		Assert.assertTrue(isElementPresent(urlLink));
		return PageFactory.initElements(driver, OrderSummaryPage.class);

	}

	/**
	 *  Verify Edureka Logo
	 * @return
	 */
	public OrderSummaryPage verifyEdurekaLogoOnOrdersummaryPage(){
	//String edurekaLogo = oderSummaryLocator.getLocator("EdurekaLogo");
		WebElement logo = driver.findElement(By.cssSelector(".navbar-brand>img"));
		String logoname = logo.getAttribute("value");
        waitForWebElementPresent(logo);
		Assert.assertTrue(isElementPresent("css= .navbar-brand>img"));
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}
	/**
	 * Verify Data in user Completed_Queue_Jobs Table
	 * 
	 * @param courseId
	 * @param event_context
	 * @param utm_source
	 * @param utm_campaign
	 * @return
	 * @throws Exception
	 */
	public  OrderSummaryPage dataVerificationInCompleted_Queue_Jobs_Table( String userEmail,String course_id) throws Exception {
		OrderSummaryPage orderSummaryPage;
		orderSummaryPage = ( OrderSummaryPage)databaseVerification.dataVerificationInCompleted_Queue_Jobs_Table(OrderSummaryPage.class,userEmail,course_id);
		return orderSummaryPage;
	}

	/**
	 *  Verify Elements with INR selection
	 * @param timeZone
	 * @return
	 * @throws InterruptedException
	 */
	public OrderSummaryPage verifytheElementsWithINRSelection() {

		List<WebElement> tabList = driver.findElements(By.xpath("//div[@id='payment-inr-button']/ul/li"));
		Assert.assertEquals(tabList.size(), 1);

		//Verify for Credit Debit Card
		String creditDebitTab = oderSummaryLocator.getLocator("CreditDebitTab");
		WaitForElementPresent(creditDebitTab, getTimeOut());
		Assert.assertTrue(isElementPresent(creditDebitTab));

		//Verify for VISA,Master and other card type images
		String visaImage = oderSummaryLocator.getLocator("VisaImage");
		String masterImage = oderSummaryLocator.getLocator("MasterImage");
		String americanExpressImage = oderSummaryLocator.getLocator("AmericanExpressImage");
		String discoverImage = oderSummaryLocator.getLocator("DiscoverImage");
		Assert.assertTrue(isElementPresent(visaImage));
		Assert.assertTrue(isElementPresent(masterImage));
		Assert.assertTrue(isElementPresent(americanExpressImage));
		Assert.assertTrue(isElementPresent(discoverImage));

		//Verify Transaction 256 bit encrypted
		String transaction256Encrypted=oderSummaryLocator.getLocator("Transaction256Encrypted_INR");
		Assert.assertTrue(isElementPresent(transaction256Encrypted));

		//Verify start learning text
		String learningText=oderSummaryLocator.getLocator("StartLearningText");
		String learningText1=oderSummaryLocator.getLocator("StartLearningText1");
		String learningText2=oderSummaryLocator.getLocator("StartLearningText2");
		String learningText3=oderSummaryLocator.getLocator("StartLearningText3");
		String learningText4=oderSummaryLocator.getLocator("StartLearningText4");
		String learningText5=oderSummaryLocator.getLocator("StartLearningText5");

		Assert.assertTrue(getText(learningText).contains("Start Learning Right Away !"));
		Assert.assertTrue(getText(learningText1).contains("Learning management system"));
		Assert.assertTrue(getText(learningText2).contains("Videos of previous class recordings"));
		Assert.assertTrue(getText(learningText3).contains("Assignments and Projects"));
		Assert.assertTrue(getText(learningText4).contains("Lifetime Access"));
		Assert.assertTrue(getText(learningText5).contains("24X7 Expert Technical Support"));

		return PageFactory.initElements(driver, OrderSummaryPage.class);

	}

	/**
	 *  Verify Elements with INR selection
	 * @param timeZone
	 * @return
	 * @throws InterruptedException
	 */
	public OrderSummaryPage verifytheElementsWithUSSDSelection() {
		List<WebElement> tabList = driver.findElements(By.xpath("//*[@id='payment-paypal-button']/ul/li"));
		Assert.assertEquals(tabList.size(), 2);

		//Verify for Credit Debit Card
		String creditDebitTab = oderSummaryLocator.getLocator("CreditDebitTab1");
		WaitForElementPresent(creditDebitTab, getTimeOut());
		String creditDebitTabText = getText(creditDebitTab);
		Assert.assertTrue(creditDebitTabText.contains("Credit card / Debit card"));

		String payPalTab = oderSummaryLocator.getLocator("PaypalTab");
		WaitForElementPresent(payPalTab, getTimeOut());
		String payPalTabText = getText(payPalTab);
		Assert.assertTrue(payPalTabText.contains("Paypal"));

		//Verify for VISA,Master and other card type images
		String visaImage = oderSummaryLocator.getLocator("VisaImage_USSD");
		String masterImage = oderSummaryLocator.getLocator("MasterImage_USSD");
		String americanExpressImage = oderSummaryLocator.getLocator("AmericanExpressImage_USSD");
		String discoverImage = oderSummaryLocator.getLocator("DiscoverImage_USSD");
		Assert.assertTrue(isElementPresent(visaImage));
		Assert.assertTrue(isElementPresent(masterImage));
		Assert.assertTrue(isElementPresent(americanExpressImage));
		Assert.assertTrue(isElementPresent(discoverImage));

		//Verify Transaction 256 bit encrypted
		String transaction256Encrypted=oderSummaryLocator.getLocator("Transaction256Encrypted_USSD");
		Assert.assertTrue(isElementPresent(transaction256Encrypted));

		//Verify start learning text
		String learningText=oderSummaryLocator.getLocator("StartLearningText");
		String learningText1=oderSummaryLocator.getLocator("StartLearningText1");
		String learningText2=oderSummaryLocator.getLocator("StartLearningText2");
		String learningText3=oderSummaryLocator.getLocator("StartLearningText3");
		String learningText4=oderSummaryLocator.getLocator("StartLearningText4");
		String learningText5=oderSummaryLocator.getLocator("StartLearningText5");

		Assert.assertTrue(getText(learningText).contains("Start Learning Right Away !"));
		Assert.assertTrue(getText(learningText1).contains("Learning management system"));
		Assert.assertTrue(getText(learningText2).contains("Videos of previous class recordings"));
		Assert.assertTrue(getText(learningText3).contains("Assignments and Projects"));
		Assert.assertTrue(getText(learningText4).contains("Lifetime Access"));
		Assert.assertTrue(getText(learningText5).contains("24X7 Expert Technical Support"));

		return PageFactory.initElements(driver, OrderSummaryPage.class);

	}
	/**
	 *  Verify Course Name
	 * @param timeZone
	 * @return
	 * @throws InterruptedException
	 */
	public OrderSummaryPage verifyCourseName() throws InterruptedException {
		String courseName=oderSummaryLocator.getLocator("CourseName");
		Assert.assertTrue(isElementPresent(courseName));
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}
	/**
	 *  Verify Need Assistance
	 * @param timeZone
	 * @return
	 * @throws InterruptedException
	 */
	public OrderSummaryPage verifyNeedAssitanceAndMobileNos() throws InterruptedException {
		Log("verify Need Assisstance");
		String needAssistance=oderSummaryLocator.getLocator("NeedAssitance");
		Assert.assertTrue(isElementPresent(needAssistance));
		Log("Toll Free Number");
		String tollFree=oderSummaryLocator.getLocator("TollFreeNo");
		Assert.assertTrue(isElementPresent(tollFree));
		Log("Mobile Number");
		String mobileNo=oderSummaryLocator.getLocator("MobileNo");
		Assert.assertTrue(isElementPresent(mobileNo));
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 *  Database verification in Pre_Order table
	 * @param course_Id
	 * @param courseTitle
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage dataVerificationInUser_PreOrderTable(String course_Id, String courseTitle,String gateway,String currency,String testCaseName,String excelSheetName,String Discount,String TotalAmount,String price,String serviceTax,String emailId ) throws Exception {
		OrderSummaryPage orderSummary;
		orderSummary = (OrderSummaryPage)databaseVerification.dataVerificationInPreOrderTable(OrderSummaryPage.class, course_Id, courseTitle,gateway,currency,testCaseName,excelSheetName,Discount,TotalAmount,price,serviceTax,emailId);
		return orderSummary;
	}

	/**
	 *  Verify Price after Referral and Offer Discounts
	 * @return
	 */
	public OrderSummaryPage verifyPriceAfterOfferAndReferralDiscount(){
		String orgPrice =oderSummaryLocator.getLocator("Price");
		String orgPrice_Value = getText(orgPrice);
		String orgPrice_Value_1 = orgPrice_Value.replace(",", "");
		String orgPrice_Value_2 = orgPrice_Value_1.trim();
		int orgPrice_Value_3= Integer.parseInt(orgPrice_Value_2);

		String discountPrice=oderSummaryLocator.getLocator("Discount");
		String discountPrice_1 = getText(discountPrice);
		String discountPrice_2 = discountPrice_1.replace(",", "");
		String discountPrice_3 = discountPrice_2.replace("-","");
		String discountPrice_4= discountPrice_3.trim();
		int discountPrice_5 = Integer.parseInt(discountPrice_4);

		int expected_Price= orgPrice_Value_3-discountPrice_5;

		String netPrice=oderSummaryLocator.getLocator("NetPrice");
		String netPrice_1 = getText(netPrice);
		String netPrice_2= netPrice_1.replace(",","");
		String netPrice_3= netPrice_2.replace("-","");
		String netPrice_4= netPrice_3.trim();
		int netPrice_5 = Integer.parseInt(netPrice_4);
		Assert.assertEquals(expected_Price, netPrice_5);
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}
	/**
	 *  Verify Price after Referral and Offer Discounts
	 * @return
	 */
	public OrderSummaryPage verifyDiscountForExistingCustomer(String currency){
		int serviceTaxValue;
		int priceWithServiceTax = 0;
		String orgPrice =oderSummaryLocator.getLocator("Price");
		String serviceTax = oderSummaryLocator.getLocator("ServiceTax");
		String totalAmount = oderSummaryLocator.getLocator("TotalAmount");
		String orgPrice_Value = getText(orgPrice);
		String orgPrice_Value_1 = orgPrice_Value.replace(",", "");
		String orgPrice_Value_2 = orgPrice_Value_1.trim();
		int orgPrice_Value_3= Integer.parseInt(orgPrice_Value_2);

		String discountPrice=oderSummaryLocator.getLocator("Discount");
		String discountPrice_1 = getText(discountPrice);
		String discountPrice_2 = discountPrice_1.replace(",", "");
		String discountPrice_3 = discountPrice_2.replace("-","");
		String discountPrice_4= discountPrice_3.trim();
		int discountPrice_5 = Integer.parseInt(discountPrice_4);

		int priceAfterDiscount= orgPrice_Value_3-discountPrice_5;

		String netPrice=oderSummaryLocator.getLocator("NetPrice");
		String netPrice_1 = getText(netPrice);
		String netPrice_2= netPrice_1.replace(",","");
		String netPrice_3= netPrice_2.replace("-","");
		String netPrice_4= netPrice_3.trim();
		int netPrice_5 = Integer.parseInt(netPrice_4);

		Assert.assertEquals(priceAfterDiscount, netPrice_5);



		String referralPoints= "//span[@id='referral-points']";
		String referralPoints_1 = getText(referralPoints);
		String referralPoints_2= referralPoints_1.replace(",","");
		String referralPoints_3= referralPoints_2.replace("-","");
		String referralPoints_4= referralPoints_3.trim();
		int referralPoints_5 = Integer.parseInt(referralPoints_4);

		int priceAfterReferralPoints = netPrice_5-referralPoints_5;

		if (currency.equals("INR")){

			String serviceTaxAmount= getText(serviceTax);
			String serviceTaxVal_1= serviceTaxAmount.replace(",","");
			String serviceTaxVal_2=serviceTaxVal_1.trim();
			serviceTaxValue= Integer.parseInt(serviceTaxVal_2);

			priceWithServiceTax = priceAfterReferralPoints+serviceTaxValue;
		}

		WaitForElementPresent(totalAmount, getTimeOut());
		String amount= getText(totalAmount);
		String totalAmountVal_1= amount.replace(",","");
		String totalAmountVal_2 =totalAmountVal_1.trim();
		int totalAmountVal_3= Integer.parseInt(totalAmountVal_2);

		if (currency.equals("INR")){

			Assert.assertEquals(priceWithServiceTax, totalAmountVal_3);
		}

		else {

			Assert.assertEquals(priceAfterReferralPoints, totalAmountVal_3);
		}

		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}
	/**
	 *  Verify total Amount for INR is equal to Net price + Service Tax
	 * @return
	 */
	public OrderSummaryPage verifyTotalAmountForCreditPointsCourse(String currency){
		String price = oderSummaryLocator.getLocator("Price");
		String totalAmount = oderSummaryLocator.getLocator("TotalAmount");
		String netPrice = oderSummaryLocator.getLocator("NetPrice");
		String discount =oderSummaryLocator.getLocator("Discount");
		String serviceTax = oderSummaryLocator.getLocator("ServiceTax");
		String discountRate = oderSummaryLocator.getLocator("Discount");

		int netPriceValue = 0;
		int totalAmountValue=0;
		int serviceTaxValue=0;
		int priceAfterDiscont=0;
		int discountValue=0;
		int totalAmountVal_3=0;
		WaitForElementPresent(price, getTimeOut());
		String priceAmount= getText(price);
		String priceVal= priceAmount.replace(",","");
		String priceVal_2= priceVal.trim();
		int pirceValue = Integer.parseInt(priceVal_2);
		propertyReader.updatePropertyTestData("PriceValue", priceVal);

		if (isElementPresent(discountRate)){
			String discountAmount= getText(discount);
			String discountVal_1= discountAmount.replace(",", "");
			String discountVal_2= discountVal_1.replace("-", "");
			String discountVal_3=discountVal_2.trim();
			discountValue = Integer.parseInt(discountVal_3);

			priceAfterDiscont= pirceValue-discountValue;

			String netPriceAmount = getText(netPrice);
			String netPriceaVal= netPriceAmount.replace(",","");
			String netPriceaVal_1= netPriceaVal.trim();
			netPriceValue = Integer.parseInt(netPriceaVal_1);
			Assert.assertEquals(priceAfterDiscont, netPriceValue);
		}
		if (currency.equalsIgnoreCase("INR")){
			String serviceTaxAmount= getText(serviceTax);
			String serviceTaxVal_1= serviceTaxAmount.replace(",","");
			String serviceTaxVal_2=serviceTaxVal_1.trim();
			System.out.println("Service tax value is :"+serviceTaxVal_2);
			if (serviceTaxVal_2=="" || serviceTaxVal_2.trim().equals("")) {

			}
			else{
				System.out.println("I am in else");
				serviceTaxValue= Integer.parseInt(serviceTaxVal_2);
			}


			propertyReader.updatePropertyTestData("ServiceTax",serviceTaxVal_2);
			if (isElementPresent(discountRate)){
				totalAmountValue= netPriceValue+serviceTaxValue;
			}
		}
		String netPriceAmount = getText(netPrice);
		String netPriceaVal= netPriceAmount.replace(",","");
		String netPriceaVal_1= netPriceaVal.trim();
		netPriceValue = Integer.parseInt(netPriceaVal_1);
		Assert.assertEquals(priceAfterDiscont, netPriceValue);
		WaitForElementPresent(totalAmount, getTimeOut());
		String amount= getText(totalAmount);
		String totalAmountVal_1= amount.replace(",","");
		String totalAmountVal_2 =totalAmountVal_1.trim();
		totalAmountVal_3= Integer.parseInt(totalAmountVal_2);
		System.out.println("totalAmountVal_3  :::"+totalAmountVal_3);
		System.out.println("Total Amount :::"+totalAmountValue);

		System.out.println("Discount Value :::"+discountValue);
		propertyReader.updatePropertyTestData("TotalAmount",Integer.toString(totalAmountVal_3));
		propertyReader.updatePropertyTestData("DiscountValue",Integer.toString(discountValue));

		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 * Enter Coupon code and Apply
	 * @return
	 */
	public OrderSummaryPage enterCouponCode(String couponCode){
		String tbCouponCode =oderSummaryLocator.getLocator("TBCouponCode");
		WaitForElementPresent(tbCouponCode, getTimeOut());
		sendKeys(tbCouponCode, couponCode);

		String btnApplyCode =oderSummaryLocator.getLocator("BTNApplyCode");
		WaitForElementPresent(btnApplyCode, getTimeOut());
		clickOn(btnApplyCode);
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 *  Verify Coupon code has been applied 
	 * @param couponCode
	 * @return
	 * @throws InterruptedException
	 */
	public OrderSummaryPage verifyCouponCodeIsApplied(String couponCode) throws InterruptedException{
		Thread.sleep(5000);
		String codeAppliedMessage =oderSummaryLocator.getLocator("AppliedCouponMessage");
		String codeAppliedMessage_Text = getText(codeAppliedMessage);
		System.out.println("codeAppliedMessage ::  " +codeAppliedMessage_Text);
		Assert.assertTrue(codeAppliedMessage_Text.contains("Coupon '"+couponCode+"' applied REMOVE"));
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 *  Verify Price after Referral and Offer Discounts
	 * @return
	 */
	public OrderSummaryPage verifyDiscountForCouponCode(String discountPercentage, String currency){
		int serviceTaxValue;
		int priceWithServiceTax = 0;
		String orgPrice =oderSummaryLocator.getLocator("Price");
		String serviceTax = oderSummaryLocator.getLocator("ServiceTax");
		String totalAmount = oderSummaryLocator.getLocator("TotalAmount");
		String orgPrice_Value = getText(orgPrice);
		String orgPrice_Value_1 = orgPrice_Value.replace(",", "");
		String orgPrice_Value_2 = orgPrice_Value_1.trim();
		int orgPrice_Value_3= Integer.parseInt(orgPrice_Value_2);

		int couponDiscount = Integer.parseInt(discountPercentage);
		double discountAmount =round((orgPrice_Value_3*(couponDiscount/100.0f)),2);
		int discountAmount_1= (int)(orgPrice_Value_3-discountAmount);

		if (currency.equals("INR")){

			String serviceTaxAmount= getText(serviceTax);
			String serviceTaxVal_1= serviceTaxAmount.replace(",","");
			String serviceTaxVal_2=serviceTaxVal_1.trim();
			serviceTaxValue= Integer.parseInt(serviceTaxVal_2);

			priceWithServiceTax = discountAmount_1+serviceTaxValue;
		}

		WaitForElementPresent(totalAmount, getTimeOut());
		String amount= getText(totalAmount);
		String totalAmountVal_1= amount.replace(",","");
		String totalAmountVal_2 =totalAmountVal_1.trim();
		int totalAmountVal_3= Integer.parseInt(totalAmountVal_2);

		if (currency.equals("INR")){

			Assert.assertEquals(priceWithServiceTax, totalAmountVal_3);
		}

		else {

			Assert.assertEquals(discountAmount_1, totalAmountVal_3);
		}

		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}
	/**
	 *  Verify total Amount for INR is equal to Net price + Service Tax
	 *  @param currency
	 * @return
	 */
	public OrderSummaryPage verifyTotalAmountWithoutNetPrice(String currency){
		String price = oderSummaryLocator.getLocator("Price");
		String totalAmount = oderSummaryLocator.getLocator("TotalAmount");
		String discount =oderSummaryLocator.getLocator("Discount");
		String serviceTax = oderSummaryLocator.getLocator("ServiceTax");
		String discountRate = oderSummaryLocator.getLocator("Discount");

		int discountValue=0;
		int totalAmountVal_3=0;
		WaitForElementPresent(price, getTimeOut());
		String priceAmount= getText(price);
		String priceVal= priceAmount.replace(",","");
		String priceVal_2= priceVal.trim();
		propertyReader.updatePropertyTestData("PriceValue", priceVal_2);

		if (isElementPresent(discountRate)){
			String discountAmount= getText(discount);
			String discountVal_1= discountAmount.replace(",", "");
			String discountVal_2= discountVal_1.replace("-", "");
			String discountVal_3=discountVal_2.trim();
			discountValue = Integer.parseInt(discountVal_3);

		}
		if (currency.equalsIgnoreCase("INR")){
			String serviceTaxAmount= getText(serviceTax);
			String serviceTaxVal_1= serviceTaxAmount.replace(",","");
			String serviceTaxVal_2=serviceTaxVal_1.trim();
			System.out.println("Service tax value is :"+serviceTaxVal_2);
			if (serviceTaxVal_2=="" || serviceTaxVal_2.trim().equals("")) {

			}
			else{
			}
			propertyReader.updatePropertyTestData("ServiceTax",serviceTaxVal_2);

		}
		WaitForElementPresent(totalAmount, getTimeOut());
		String amount= getText(totalAmount);
		String totalAmountVal_1= amount.replace(",","");
		String totalAmountVal_2 =totalAmountVal_1.trim();
		totalAmountVal_3= Integer.parseInt(totalAmountVal_2);
		propertyReader.updatePropertyTestData("TotalAmount",Integer.toString(totalAmountVal_3));
		propertyReader.updatePropertyTestData("DiscountValue",Integer.toString(discountValue));

		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}
	/**
	 *  This method is used to refresh page and check the selected country
	 * @param countryName
	 * @return
	 * @throws InterruptedException
	 */
	public OrderSummaryPage refreshPageAndCheckCountry(String countryName) throws InterruptedException {
		String ddCountry = oderSummaryLocator.getLocator("Country.DDCountry");
		WaitForElementPresent(ddCountry, getTimeOut());
		clickOn(ddCountry);
		String country = "//div[@class='dropdown-menu open']/ul/div/div/li/a/span[contains(text(),'"+countryName+"')]";
		Assert.assertTrue(isElementPresent(country));
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 *  This method is used to verify currency after country change
	 * @param currency
	 * @return
	 * @throws InterruptedException
	 */
	public OrderSummaryPage verifyCurrencyAfterCountryChange(String currency) throws InterruptedException {
		String currencySpan = oderSummaryLocator.getLocator("CurrencySpan");
		WaitForElementPresent(currencySpan, getTimeOut());
		Assert.assertTrue(getText(currencySpan).trim().contains(currency.trim()));
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}

	/**
	 *  Get User A and User B Available Points
	 * @param email
	 * @param referral_Email
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage getAvailablePointsOfUserAndUserB(String email, String referral_Email) throws Exception {
		OrderSummaryPage orderSummary;
		orderSummary = (OrderSummaryPage)databaseVerification.getAvailablePointsOfUserAndUserB(OrderSummaryPage.class, email, referral_Email);
		return orderSummary;
	}
	/**
	 *  Get User A and User B Available Points
	 * @param email
	 * @param referral_Email
	 * @return
	 * @throws Exception
	 */
	public OrderSummaryPage selectB1S2Course(String b1S2CourseName) throws Exception {
		_waitForJStoLoad();
		List<WebElement> courseList=driver.findElements(By.xpath("//div[@id='frecoursesection']/div/div/ul/li"));
		int size=courseList.size();
		for(int i=1;i<=size;i++){
			String courseName=getText("//div[@id='frecoursesection']/div/div/ul/li["+i+"]/label");
			if(courseName.contains(b1S2CourseName)){
				clickOn("//div[@id='frecoursesection']/div/div/ul/li["+i+"]/input");
				break;
			}
		}
		return PageFactory.initElements(driver, OrderSummaryPage.class);
	}
	
	  /**
	   *  Database verification in Pre_Order table
	   * @param course_Id
	   * @param courseTitle
	   * @return
	   * @throws Exception
	   */
	  public OrderSummaryPage dataVerificationInUser_PreOrderTableForLeadProcess(String course_Id, String courseTitle,String emailId) throws Exception {
		  OrderSummaryPage orderSummaryPage;
		  orderSummaryPage = (OrderSummaryPage)databaseVerification.dataVerificationInPreOrderTableForLeadProcess(OrderSummaryPage.class, course_Id, courseTitle,emailId);
	      return orderSummaryPage;
	  }
public void getStudentCount(String title) throws Exception{
	 databaseVerification.getStudentCount(title);
}

public void verifyCoursePrice(String course_id,String currency_id,String amount) throws Exception {
    Database d = new Database();
    String coursePrice = d.getRecord("course_prices","value",new String[]{"course_id","currency_id"},new String[]{course_id,currency_id});
    Assert.assertTrue(amount.contains(coursePrice));

}

public OrderSummaryPage verifyB1S2Title() {
	
	String B1S2_Title = driver.findElement(By.cssSelector(".titleorderdis")).getText();
	System.out.println("UI Message is : "+ B1S2_Title);
	Assert.assertEquals(B1S2_Title,"Buy 1 Study 2 Offer !");
	
	return PageFactory.initElements(driver,OrderSummaryPage.class);
}

@FindBy(css =".offersecnew.checkbox>ul>li:nth-child(1)" ) WebElement chooseLater;
public OrderSummaryPage clickOnChooseLater() {
	waitForWebElementPresent(chooseLater);
	chooseLater.click();
	return PageFactory.initElements(driver,OrderSummaryPage.class);
}


}


