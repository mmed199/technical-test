package moussaoui.mohammed.technicalTest.integration;

import static org.hamcrest.Matchers.is;

import java.net.URI;
import java.text.SimpleDateFormat;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import moussaoui.mohammed.technicalTest.model.User;


/**
 * Integration test for the class {@link moussaoui.mohammed.technicalTest.controller.UserController
 * UserController }.
 * The application is provided as a real web environment, and loaded on a random port. 
 * 
 * @author moussaoui
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	/**
	 * Test case for the a POST request on the /user endpoint,
	 * the app should create a new user
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendingValidUser_apiReturnOk() throws Exception {
		URI uri = new URI(this.getUrl());

		User userToSave = new User("Moussaui_test", new SimpleDateFormat("dd/MM/yyyy").parse("02/12/1998"), "France");
		userToSave.setPhoneNumber("0700000000");
		userToSave.setGender("Male");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<User> request = new HttpEntity<>(userToSave, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		MatcherAssert.assertThat(result.getStatusCodeValue(), is(200));
	}
	
	/**
	 * Test case for the a POST request on the /user endpoint, with an invalid user
	 * the app should return BADREQUEST error, with errors detail
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendingInvalidUser_apiReturnBADREQUEST() throws Exception {
		URI uri = new URI(this.getUrl());

		User userToSave = new User("Moussaui_test", new SimpleDateFormat("dd/MM/yyyy").parse("02/12/2020"), "Italy");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<User> request = new HttpEntity<>(userToSave, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		MatcherAssert.assertThat(result.getStatusCodeValue(), is(400));
		MatcherAssert.assertThat(result.getBody().contains("Only users from France can create an account"), is(true));
		MatcherAssert.assertThat(result.getBody().contains("User should be adult"), is(true));
	}
	
	/**
	 * Test case for the a POST request on the /user endpoint, with an existing user
	 * the app should return CONFLICT error
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendingTwiceValidUser_apiReturnCONFLICT() throws Exception {
		URI uri = new URI(this.getUrl());

		User userToSave = new User("Moussaui_test_1", new SimpleDateFormat("dd/MM/yyyy").parse("02/12/1998"), "France");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<User> request = new HttpEntity<>(userToSave, headers);

		// First time
		this.restTemplate.postForEntity(uri, request, String.class);
		
		// Second time
		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
		
		MatcherAssert.assertThat(result.getStatusCodeValue(), is(409));
	}
	
	
	/**
	 * Test case for the a GET request on the /user/{username} endpoint
	 * with an existing user, the api should return all the informations of the user
	 * 
	 * @throws Exception
	 */
	@Test
	public void whenGetExistingUser_apiReturnUser() throws Exception {
		
		// Saving the user
		URI uri = new URI(this.getUrl());
		User userToSave = new User("Moussaui_test_2", new SimpleDateFormat("dd/MM/yyyy").parse("02/12/1998"), "France");
		userToSave.setPhoneNumber("0700000000");
		userToSave.setGender("Male");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");
		
		HttpEntity<User> requestPost = new HttpEntity<>(userToSave, headers);
		this.restTemplate.postForEntity(uri, requestPost, String.class);
		
		// getting the user
		HttpEntity<String> requestGet = new HttpEntity<>(null, headers);

		ResponseEntity<String> result = restTemplate.exchange(this.getUrl() + "/Moussaui_test_2", HttpMethod.GET, requestGet, String.class);
		MatcherAssert.assertThat(result.getBody().contains("Moussaui_test_2"), is(true));
		MatcherAssert.assertThat(result.getBody().contains("France"), is(true));
		MatcherAssert.assertThat(result.getBody().contains("0700000000"), is(true));
		MatcherAssert.assertThat(result.getBody().contains("Male"), is(true));
		MatcherAssert.assertThat(result.getStatusCodeValue(), is(200));

	}
	
	/**
	 * Test case for the a GET request on the /user/{username} endpoint
	 * with a user that not exist in DB, the api should return NOT FOUND error
	 * 
	 * @throws Exception
	 */
	@Test
	public void whenGetNotExistingUser_apiReturnNOTFOUND() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");
			
		// getting the user
		HttpEntity<String> requestGet = new HttpEntity<>(null, headers);

		ResponseEntity<String> result = restTemplate.exchange(this.getUrl() + "/Moussaui_test_4", HttpMethod.GET, requestGet, String.class);
		MatcherAssert.assertThat(result.getStatusCodeValue(), is(404));

	}

	private String getUrl() {
		return "http://localhost:" + port + "/api/user";
	}
}
