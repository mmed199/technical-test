package moussaoui.mohammed.technicalTest.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static com.jayway.jsonpath.JsonPath.*;
import moussaoui.mohammed.technicalTest.entity.UserEntity;
import moussaoui.mohammed.technicalTest.service.UserService;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService userService;
    
	@Autowired
    private ObjectMapper objectMapper;
    
	@Test
	public void givenUser_whenUserByUsername_thenReturnUserJson() throws Exception {

		UserEntity user = new UserEntity("Moussaoui_test", new Date(), "France");

		given(userService.getUserByUsername("Moussaoui_test")).willReturn(user);

		mvc.perform(get("/api/user/Moussaoui_test").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(jsonPath("$.username", is(user.getUsername())));
	}

	@Test
    public void givenUser_whenUserAdd_thenReturnUserJson()
      throws Exception {
		LocalDate date = LocalDate.parse("1998-05-05");
		Date birthdate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        UserEntity user = new UserEntity("Moussaoui_test", birthdate, "France");

        given(userService.addUser(any(UserEntity.class))).willReturn(user);
        
        mvc.perform(post("/api/user/")
        	.contentType(MediaType.APPLICATION_JSON)
        	.content(objectMapper.writeValueAsString(user)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username", is(user.getUsername())));
    }
	
	@Test
    public void givenUserError_whenUserAdd_thenReturnErrorJson()
      throws Exception {
        UserEntity user = new UserEntity("Moussaoui_test", new Date(), "Italy", "07001314", "M");

        given(userService.addUser(any(UserEntity.class))).willReturn(user);
        
        mvc.perform(post("/api/user/")
        	.contentType(MediaType.APPLICATION_JSON)
        	.content(objectMapper.writeValueAsString(user)))
			.andExpect(status().is(400))
			.andExpect(jsonPath("$.birthdate", is("User should be adult")))
        	.andExpect(jsonPath("$.residanceCountry", is("Only users from France can create an account")))
        	.andExpect(jsonPath("$.gender", is("Gender should equals Male, Female or Other")));
    }
}
