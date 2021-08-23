package moussaoui.mohammed.technicalTest.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import moussaoui.mohammed.technicalTest.model.User;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @Column(name = "USERNAME", unique = true, length = 50)
    private String username;

    @Column(name = "BIRTHDATE")
    private Date birthdate;

    @Column(name = "RESIDANCE_COUNTRY")
    private String residanceCountry;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "GENDER")
    private String gender;

    public UserEntity() {
    	super();
    }
    
    public UserEntity(User user) {
    	username = user.getUsername();
    	birthdate = user.getBirthdate();
    	residanceCountry = user.getResidanceCountry();
    	phoneNumber = user.getPhoneNumber();
    	gender = user.getGender();
    }
    
    
    public User toModel() {
    	User user = new User();
    	user.setUsername(username);
    	user.setBirthdate(birthdate);
    	user.setResidanceCountry(residanceCountry);
    	user.setPhoneNumber(phoneNumber);
    	user.setGender(gender);
    	
    	return user;
    }

}
