package moussaoui.mohammed.technicalTest.unit;

import static org.hamcrest.Matchers.is;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import moussaoui.mohammed.technicalTest.entity.UserEntity;
import moussaoui.mohammed.technicalTest.model.User;
import moussaoui.mohammed.technicalTest.repository.UserRepository;

/**
 * Unit tests for the repository layer
 * {@link moussaoui.mohammed.technicalTest.repository.UserRepository UserRepository} 
 * In the test cases, we use the class TestEntityManager that allows us to
 * use EntityManager in tests.
 * 
 * @author moussaoui
 *
 */
@DataJpaTest
public class UserRepositoryUnitTest {

	private static final String USERNAME = "Moussaoui_test";

	private static final String BIRTHDATE = "31/12/1998";

	private static final String RESIDANCE = "France";

	private static final String PHONE_NUMBER = "0758855885";

	private static final String GENDER = "Male";

	private static User validUser;

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	UserRepository userRepository;

	/**
	 * Before starting the tests, instantiate the proprety validUser
	 * 
	 * @throws ParseException
	 */
	@BeforeAll
	public static void initUser() throws ParseException {
		validUser = new User(USERNAME, new SimpleDateFormat("dd/MM/yyyy").parse(BIRTHDATE), RESIDANCE);
		validUser.setPhoneNumber(PHONE_NUMBER);
		validUser.setGender(GENDER);
	}

	/**
	 * Unit Test for the findById method when user is saved, should return the
	 * correct user
	 * 
	 * @throws Exception
	 */
	@Test
	public void whenUserSaved_findByIdReturnUser() throws Exception {
		UserEntity userSaved = entityManager.persistAndFlush(new UserEntity(validUser));
		MatcherAssert.assertThat(userRepository.findById(USERNAME), is(Optional.of(userSaved)));
	}

	/**
	 * Unit Test for the findById method when user is not saved should return empty
	 * optional
	 * 
	 * @throws Exception
	 */
	@Test
	public void whenNoUserSaved_findByIdReturnEmptyOptional() throws Exception {
		MatcherAssert.assertThat(userRepository.findById(USERNAME), is(Optional.empty()));
	}

}
