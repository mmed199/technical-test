package moussaoui.mohammed.technicalTest.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moussaoui.mohammed.technicalTest.model.User;
import moussaoui.mohammed.technicalTest.validator.AdultConstraint;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @Column(name = "USERNAME", unique = true, length = 50)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "BIRTHDATE")
    @NotNull(message = "Birthdate is mandatory")
    @AdultConstraint
    private Date birthdate;

    @Column(name = "RESIDANCE_COUNTRY")
    @NotBlank(message = "Residance Country is mandatory")
    @Pattern(regexp = "France", message = "Only users from France can create an account")
    private String residanceCountry;

    @Column(name = "PHONE_NUMBER")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "The phone number must br a 10-digits number (ex = 0700112233)")
    private String phoneNumber;

    @Column(name = "GENDER")
    @Pattern(regexp = "^(?:Male|Female|Other)$", message = "the gender must be 'Male', 'Female' or 'Other'")
    private String gender;

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
