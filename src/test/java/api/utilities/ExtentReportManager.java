package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener{

	public ExtentSparkReporter sparkreporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onTestStart(ITestContext testContext) {
		// TODO Auto-generated method stub				
		
	}
	public void onStart(ITestContext context) {
		String timeStamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName= "Test-Report-"+timeStamp+".html";
		
		sparkreporter= new ExtentSparkReporter(".\\reports\\"+repName);
		sparkreporter.config().setDocumentTitle("RestAssuredAutomationProject");
		sparkreporter.config().setReportName("PetStore users API");
		sparkreporter.config().setTheme(Theme.DARK);
		
		extent= new ExtentReports();
		extent.attachReporter(sparkreporter);
		extent.setSystemInfo("Application","Pet Store users API");
		extent.setSystemInfo("Operating System",System.getProperty("os.name"));
		extent.setSystemInfo("User Name",System.getProperty("user.name"));
		extent.setSystemInfo("Environment","QA");
		extent.setSystemInfo("user","Ajay");
		
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		test= extent.createTest(result.getName());
		test.createNode("node : "+result.getName());
		
		for(String cat : result.getMethod().getGroups()) {
			test.assignCategory("category : "+cat );
			
		}
		test.log(Status.PASS, "Test Passed");
		//test.log(Status.PASS,result.getThrowable().getMessage());
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		test= extent.createTest(result.getName());
		test.createNode("node : "+result.getName());
		for(String cat : result.getMethod().getGroups()) {
			test.assignCategory("category : "+cat );
			
		}
		test.log(Status.FAIL, "Test Failed");
		test.log(Status.FAIL,result.getThrowable().getMessage());
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		test= extent.createTest(result.getName());
		test.createNode("node : "+result.getName());
		for(String cat : result.getMethod().getGroups()) {
			test.assignCategory("category : "+cat );
			
		}
		test.log(Status.SKIP, "Test Skipped");
		test.log(Status.SKIP,result.getThrowable().getMessage());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extent.flush();
	}

	
	
}
