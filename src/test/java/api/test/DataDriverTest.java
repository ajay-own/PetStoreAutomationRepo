package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDriverTest {

	public Logger logger;
	
	
	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class,groups= {"CREATE USER"})
	public void testpostUser(String userID,String userName,String lname,String fname,String useremail, String pwd,String ph) {
		System.out.println("POST USER");
		logger= LogManager.getLogger(this.getClass());
		logger.info("*********creating user info****************");
		User userPayload = new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setEmail(useremail);
		userPayload.setFirstName(fname);
		userPayload.setLastname(lname);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		
		Response response = UserEndpoints.createUser(userPayload);
		response.then().log().all();
		logger.info("*********Created user info****************");
		Assert.assertEquals(response.getStatusCode(),200);
	}
	
	@Test(priority=2,dataProvider="UserNames",dataProviderClass=DataProviders.class,groups= {"DELETE"})
	public void testDeleteUserName(String userName) {
		logger= LogManager.getLogger(this.getClass());
		logger.info("*********deleting user info****************");
		System.out.println("DELETE USER");
		Response response= UserEndpoints.deleteUser(userName);
		Assert.assertEquals(response.statusCode(),200);
		logger.info("*********user info DELETED****************");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
