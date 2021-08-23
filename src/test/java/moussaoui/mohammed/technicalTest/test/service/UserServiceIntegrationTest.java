/*package moussaoui.mohammed.technicalTest.test.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.Date;
import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import moussaoui.mohammed.technicalTest.entity.UserEntity;
import moussaoui.mohammed.technicalTest.repository.UserRepository;
import moussaoui.mohammed.technicalTest.service.UserService;

@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest {

    @TestConfiguration
    static class UserServiceIntegrationTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        UserEntity user = new UserEntity("Moussaoui_test", new Date(), "France", "0758705774", "Male");
        Mockito.when(userRepository.findById("Moussaoui_test"))
            .thenReturn(Optional.of(user));
    }

    @Test
    public void whenValidUsername_thenUserShouldBeFound() {
        String username = "Moussaoui_test";
        UserEntity found = userService.getUserByUsername(username);

        assertThat(found.getUsername(), equalTo("Moussaoui_test"));
        assertThat(found.getPhoneNumber(), equalTo("0758705774"));
        assertThat(found.getGender(), equalTo("Male"));
        assertThat(found.getResidanceCountry(), equalTo("France"));
    }

    @Test
    public void whenNoValidUsername_thenUserShouldNotBeFound() {
        String username = "Moussaoui";
        UserEntity found = userService.getUserByUsername(username);

        assertThat(found, is(nullValue()));
    }

    @Test
    public void whenValidUsername_thenUserExists() {
        UserEntity user = new UserEntity("Moussaoui_test", new Date(), "France");
        boolean exists = userService.userExists(user);

        assertThat(exists, is(true));
    }

    @Test
    public void whenNoValidUsername_thenUserDoesNotExists() {
        UserEntity user = new UserEntity("Moussaoui", new Date(), "France");
        boolean exists = userService.userExists(user);

        assertThat(exists, is(false));
    }
}*/
