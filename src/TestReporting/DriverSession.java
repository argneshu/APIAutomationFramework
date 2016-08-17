package TestReporting;

import org.openqa.selenium.WebDriver;

//import testCaseReporting.TestCaseReporting;
//import utilities.GlobalVar;

public class DriverSession {
	private static WebDriver driver;
	private static TestCaseReporting testaCaseReporting;
	//private static AppiumDriver aDriver;
	
	public static void setLastExecutionDriver(WebDriver wd) {
		driver = wd;
		GlobalVar.driver = wd;

	}
	/*public static void setLastExecutionAppiumDriver(AppiumDriver ADriver)
	{
		aDriver=ADriver;
		GlobalVar.aDriver=ADriver;
	}*/
	
	public static WebDriver getLastExecutionDriver() {
		return driver;
	}
	
	/*public static AppiumDriver getLastExecutionAppiumDriver()
	{
		return aDriver;
	}*/

	public static void setLastExecutionReportingInstance(
			TestCaseReporting testReporting) {
		testaCaseReporting = testReporting;
	}

	public static TestCaseReporting getLastExecutionReportingInstance() {
		return testaCaseReporting;
	}

}
