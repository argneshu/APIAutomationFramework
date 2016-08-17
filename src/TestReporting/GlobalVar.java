package TestReporting;

import io.appium.java_client.AppiumDriver;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class GlobalVar {

	public static Connection conn = null;
	public static String screenshotOption= "ALL";
	public static String CURRENT_EXECUTION_MODE;
	public static String CURRENT_PROJECT_PATH;
	public static String CURRENT_SUITE_NAME;
	public static String CURRENT_TEST_CASE_NAME;
	public static String CUR_KEWORD_EXECUTE_SEQ_NO;
	public static String CURRENT_KEYWORD_NAME;
	public static int TEST_CASE_ITERATION;
	public static String BrowserName;
	public static String url;
	public static String TestexcelPath;
	public static WebDriver driver;
	public static AppiumDriver aDriver;
	public static String vendorUrl;
	public static boolean etpStepsReport;
	public static HashMap<String, String> TEST_DATA;
	public static long pageSyncTime = 15000;
	public static int initialDivCount;
	public static int laterDivCont;
	public static HashMap<String, String> TransactionData;
	public static WebDriverBackedSelenium selenium;
	public static String staticKey;
	public static String ttlValue;
	public static HashMap<String, String> UserInfoData;
	public static Map<String, String> prodDetails;
	public static String tokenValue;
	public static String productCartInitialValue;
	public static String orderId;
	public static String productAddToCartValue;
	public static String AddToCart_ProdQty;
	public static String PROJECT_ROOT_PATH="/home/shopclues/";
	public static String radioButton;
	public static WebElement textField;
	public static String tokenType="|";
	public static String COUPON_CODE;
	public static String CURRENT_PRICE;
	public static String AVAIL_CLUES_BUCKS;
	public static String dynamicEmail;
	public static String companyName;
	public static String MerchantEmail;
	public static String flag="true";
	public static float subTotal;
	public static String OrderPrice;
	public static String OrderStatus;
	public static String OrderName;
	public static String AddFeaturename;
	public static String FeatureGroupName;
	public static String FEATURE_TYPE = "";
	public static String serverName = "";
	public static boolean ExitTestCase=false;
	public static boolean setSafari=false;
	public static String serverIP="localhost";
	public static boolean mergeCase = false;

	
}
