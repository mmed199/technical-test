package moussaoui.mohammed.technicalTest.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import moussaoui.mohammed.technicalTest.validator.AdultConstraint;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
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

    public UserEntity() {
        super();
    }

    public UserEntity(String username, Date birthdate, String residanceCountry) {
        super();
        this.username = username;
        this.birthdate = birthdate;
        this.residanceCountry = residanceCountry;
    }

    public UserEntity(String username, Date birthdate, String residanceCountry, String phoneNumber, String gender) {
        super();
        this.username = username;
        this.birthdate = birthdate;
        this.residanceCountry = residanceCountry;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
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
        UserEntity other = (UserEntity) obj;

        if (this.username != null && this.username.equals(other.getUsername())) {
            return true;
        }

        return false;
    }

}
