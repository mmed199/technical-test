package moussaoui.mohammed.technicalTest.unit;

import static org.hamcrest.Matchers.is;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import moussaoui.mohammed.technicalTest.entity.UserEntity;
import moussaoui.mohammed.technicalTest.model.User;
import moussaoui.mohammed.technicalTest.repository.UserRepository;
import moussaoui.mohammed.technicalTest.service.UserService;
import moussaoui.mohammed.technicalTest.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceUnitTest {

	private static final String USERNAME = "Moussaoui_test";

	private static final String BIRTHDATE = "31/12/1998";

	private static final String RESIDANCE = "France";

	private static final String PHONE_NUMBER = "0758855885";

	private static final String GENDER = "Male";

	private static User validUser;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	/**
	 * Before starting the tests, instantiate the proprety validUser
	 * 
	 * @throws ParseException
	 */
	@BeforeAll
	public void initUser() throws ParseException {
		userService = new UserServiceImpl();

		validUser = new User(USERNAME, new SimpleDateFormat("dd/MM/yyyy").parse(BIRTHDATE), RESIDANCE);
		validUser.setPhoneNumber(PHONE_NUMBER);
		validUser.setGender(GENDER);
	}

	/**
	 * Unit Test for the userExists method of UserService When userRepository return
	 * the correct User, userExists should return true
	 * 
	 * @throws Exception
	 */
	@Test
	public void givenUser_whenValidUsername_thenUserExists() throws Exception {

		Mockito.when(userRepository.findById(USERNAME)).thenReturn(Optional.of(new UserEntity(validUser)));

		boolean exists = userService.userExists(validUser);
		MatcherAssert.assertThat(exists, is(true));
	}

	/**
	 * Unit Test for the userExists method of UserService When no user given,
	 * userExists should return false
	 * 
	 * @throws Exception
	 */

	@Test
	public void whenNoUser_thenUserDoesNotExist() throws Exception {
		boolean exists = userService.userExists(validUser);
		MatcherAssert.assertThat(exists, is(false));
	}

	/**
	 * Unit Test for the addUser method of UserService When no user given,
	 * userExists should return false
	 * 
	 * @throws Exception
	 */

	@Test
	public void whenAddUser_thenReturnUser() throws Exception {
		Mockito.when(userRepository.save(new UserEntity(validUser))).thenReturn(new UserEntity(validUser));

		User userSaved = userService.addUser(validUser);

		MatcherAssert.assertThat(userSaved.getUsername(), is(USERNAME));
		MatcherAssert.assertThat(userSaved.getResidanceCountry(), is(RESIDANCE));
		MatcherAssert.assertThat(userSaved.getBirthdate(), is(new SimpleDateFormat("dd/MM/yyyy").parse(BIRTHDATE)));
	}

	/**
	 * Unit Test for the addUser method of getUserByUsername When the correct user
	 * is given, getUserByUsername should return the user
	 * 
	 * @throws Exception
	 */

	@Test
	public void givenUser_whenGetUserByUsername_thenReturnUser() throws Exception {
		Mockito.when(userRepository.findById(USERNAME)).thenReturn(Optional.of(new UserEntity(validUser)));

		User userReturned = userService.getUserByUsername(USERNAME);

		MatcherAssert.assertThat(userReturned.getUsername(), is(CoreMatchers.notNullValue()));
		MatcherAssert.assertThat(userReturned.getUsername(), is(USERNAME));
		MatcherAssert.assertThat(userReturned.getResidanceCountry(), is(RESIDANCE));
		MatcherAssert.assertThat(userReturned.getBirthdate(), is(new SimpleDateFormat("dd/MM/yyyy").parse(BIRTHDATE)));
	}

	/**
	 * Unit Test for the addUser method of getUserByUsername When the correct user
	 * is given, getUserByUsername should return the user
	 * 
	 * @throws Exception
	 */

	@Test
	public void givenNoUser_whenGetUserByUsername_thenReturnNull() throws Exception {
		User userReturned = userService.getUserByUsername(USERNAME);
		MatcherAssert.assertThat(userReturned, is(CoreMatchers.nullValue()));
	}

}

/*
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
	UserRepository sut;

	@Test
	public void it_should_save_user() {
		User user = new User();
		user.setName("test user");
		user = entityManager.persistAndFlush(user);
		assertThat(sut.findById(user.getId()).get()).isEqualTo(user);
	}
}*/
