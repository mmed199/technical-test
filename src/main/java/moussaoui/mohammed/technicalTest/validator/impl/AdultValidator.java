package moussaoui.mohammed.technicalTest.validator.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import moussaoui.mohammed.technicalTest.validator.AdultConstraint;

/**
 * The main implementation of the validator {@link moussaoui.mohammed.technicalTest.validator.AdultConstraint AdultConstraint}
 * 
 * @author moussaoui
 *
 */
public class AdultValidator implements ConstraintValidator<AdultConstraint, Date> {

    @Override
    public void initialize(AdultConstraint contactNumber) {
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();
        LocalDate localDate = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(localDate, today);
        return period.getYears() >= 18;
    }

}
