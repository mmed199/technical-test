package moussaoui.mohammed.technicalTest.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import moussaoui.mohammed.technicalTest.controller.UserController;
import moussaoui.mohammed.technicalTest.model.User;
import moussaoui.mohammed.technicalTest.service.impl.UserServiceImpl;

/**
 * Unit tests for the class
 * {@link moussaoui.mohammed.technicalTest.controller.UserController
 * UserController }. In the test cases we mock the Service layer to isolate the
 * controller.
 * 
 * @author Moussaoui Mohammed
 *
 */
@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

	private static final String ENDPOINT = "/api/user";

	private static final String USERNAME = "Moussaoui_test";

	private static final String BIRTHDATE = "31/12/1998";

	private static final String RESIDANCE = "France";

	private static final String PHONE_NUMBER = "0758855885";

	private static final String GENDER = "Male";

	private static User validUser;

	@MockBean
	private UserServiceImpl userService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Before starting the tests, instantiate the proprety validUser
	 */
	@BeforeAll
	public static void initUser() {
		try {
			validUser = new User(USERNAME, new SimpleDateFormat("dd/MM/yyyy").parse(BIRTHDATE), RESIDANCE);
			validUser.setPhoneNumber(PHONE_NUMBER);
			validUser.setGender(GENDER);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	// *** GET USER TEST CASES ***

	/**
	 * Unit test for the GET user endpoint, the api should return the user given by
	 * the mocked service
	 * 
	 * @throws Exception
	 */
	@Test
	public void givenUser_whenGetUser_thenReturnUser() throws Exception {
		// When the getUserByUsername is called using USERNAME,
		// return user
		Mockito.when(userService.getUserByUsername(USERNAME)).thenReturn(validUser);

		// When calling the end point /api/user,
		// it should return user
		mvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/" + USERNAME).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.username", is(USERNAME)))
				.andExpect(jsonPath("$.residanceCountry", is(RESIDANCE)));
	}

	/**
	 * Unit test for the GET user endpoint when the user is not mocked, the api
	 * should a 404 error
	 * 
	 * @throws Exception
	 */
	@Test
	public void givenNoUser_whenGetUser_thenNotFound() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/" + USERNAME).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	// *** POST USER TEST CASES ***

	/**
	 * Unit test for the POST user endpoint when the user is correct, the api should
	 * return OK and the user
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendingValidUser_whenUserAdd_thenReturnOkUserJson() throws Exception {

		Mockito.when(userService.addUser(any(User.class))).thenReturn(validUser);

		mvc.perform(MockMvcRequestBuilders.post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(validUser))).andExpect(status().isOk())
				.andExpect(jsonPath("$.username", is(USERNAME)))
				.andExpect(jsonPath("$.residanceCountry", is(RESIDANCE)))
				.andExpect(jsonPath("$.phoneNumber", is(PHONE_NUMBER)));
	}

	/**
	 * Unit test for the POST user endpoint when the user is not correct, the api
	 * should return NOT FOUND 400 and the errors descriptions
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendingUserWithErrors_whenUserAdd_thenReturnErrorJson() throws Exception {
		User userWithErors = new User("Moussaoui_test", new Date(), "Italy");
		userWithErors.setGender("M");
		userWithErors.setPhoneNumber("+337575757");

		mvc.perform(MockMvcRequestBuilders.post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userWithErors))).andExpect(status().is(400))
				.andExpect(jsonPath("$.birthdate", is("User should be adult")))
				.andExpect(jsonPath("$.residanceCountry", is("Only users from France can create an account")))
				.andExpect(jsonPath("$.gender", is("The gender must be 'Male', 'Female' or 'Other'")));
	}

	/**
	 * Unit test for POST user endpoint, when the user already exists the api should
	 * return CONFLCIT 409
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendingValidUser_givenUserAlreadyExists_whenUserAdd_thenReturnErrorJson() throws Exception {
		Mockito.when(userService.userExists(any(User.class))).thenReturn(true);

		mvc.perform(MockMvcRequestBuilders.post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(validUser))).andExpect(status().is(409));
	}

}
