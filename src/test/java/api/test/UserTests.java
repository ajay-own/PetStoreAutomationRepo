package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userPayload;
	
	public Logger logger;
	@BeforeClass
	public void setupData() {
		
		faker = new Faker();
		userPayload= new User();
		
		userPayload.setId(faker.idNumber().hashCode());	
		userPayload.setUsername(faker.name().username());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger= LogManager.getLogger(this.getClass());
		
	}
	@Test(priority=1,groups= {"CREATE USER"})
	public void testpostUser() {
		System.out.println("POST USER");
		logger.info("*********creating user info****************");
		Response response = UserEndpoints.createUser(userPayload);
		response.then().log().all();
		logger.info("*********user info created****************");
		Assert.assertEquals(response.getStatusCode(),200);
	}
	@Test(priority =2,groups= {"GET USER"})
	public void testGetUserName() {
		System.out.println("GET USER");
		logger.info("*********reading user info****************");
		Response response =UserEndpoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		logger.info("*********user info displayed****************");
		Assert.assertEquals(response.getStatusCode(), 200); //testNg validation
	}
	
	@Test(priority=3,groups= {"UPDATE"})
	public void testUpdateuser() {
		
		System.out.println("UPDATE USER");
		logger.info("*********updating user info****************");
		//update data using payload
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		
		Response response= UserEndpoints.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().body().statusCode(200);  //rest assured validation
		
		logger.info("*********updated user info****************");
		//Checking data after update
		Response responseAfterUpdate= UserEndpoints.readUser(this.userPayload.getUsername());
		System.out.println("Data After Update\n "+(UserEndpoints.readUser
				(this.userPayload.getUsername())).then().log().all());
		
	}
	@Test(priority=4,groups= {"DELETE"})
	public void testDeleteUserName() {
		logger.info("*********deleting user info****************");
		System.out.println("DELETE USER");
		Response response= UserEndpoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.statusCode(),200);
		logger.info("*********user info deleted****************");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
