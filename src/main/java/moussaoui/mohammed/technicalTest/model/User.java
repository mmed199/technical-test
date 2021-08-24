package moussaoui.mohammed.technicalTest.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import moussaoui.mohammed.technicalTest.validator.AdultConstraint;

/**
 * Specifies the user details with the validations to make in the 
 * @author moussaoui
 *
 */
public class User {
    
	/**
	 * the username can not be empty
	 */
    @NotBlank(message = "Username is mandatory")
    private String username;

	/**
	 * the birthdate can not be empty, and the age has to be > 18
	 */
    @NotNull(message = "Birthdate is mandatory")
    @AdultConstraint
    private Date birthdate;

    /**
     * the residanceCountry can not be empty, and it has to equal 'France'
     */
    @NotBlank(message = "Residance Country is mandatory")
    @Pattern(regexp = "France", message = "Only users from France can create an account")
    private String residanceCountry;

    /**
     * the phoneNumber can be empty, and must be correct
     * 
     */
    @Pattern(regexp = "(^$|[0-9]{10})", message = "The phone number must be a 10-digits number (ex = 0700112233)")
    private String phoneNumber;

    /**
     * the gender can be empty, and must be Male, Female orOther
     */
    @Pattern(regexp = "^(?:Male|Female|Other)$", message = "The gender must be 'Male', 'Female' or 'Other'")
    private String gender;

    
    public User() {
    	super();
    }
	
    public User(@NotBlank(message = "Username is mandatory") String username,
			@NotNull(message = "Birthdate is mandatory") Date birthdate,
			@NotBlank(message = "Residance Country is mandatory") @Pattern(regexp = "France", message = "Only users from France can create an account") String residanceCountry) {
		this();
		this.username = username;
		this.birthdate = birthdate;
		this.residanceCountry = residanceCountry;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getResidanceCountry() {
		return residanceCountry;
	}

	public void setResidanceCountry(String residanceCountry) {
		this.residanceCountry = residanceCountry;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;

        if (this.username != null && this.username.equals(other.getUsername())) {
            return true;
        }

        return false;
    }

	@Override
	public String toString() {
		return "User [username=" + username + ", birthdate=" + birthdate + ", residanceCountry=" + residanceCountry
				+ ", phoneNumber=" + phoneNumber + ", gender=" + gender + "]";
	}
    
    
    
    
}
