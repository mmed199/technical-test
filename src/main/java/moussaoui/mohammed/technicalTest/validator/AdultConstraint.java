package moussaoui.mohammed.technicalTest.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import moussaoui.mohammed.technicalTest.validator.impl.AdultValidator;

/**
 * Provides a constraint to check Adults, using the birth date
 * Applies on {@link java.util.Date Date}
 * 
 * @author moussaoui
 */
@Documented
@Constraint(validatedBy = AdultValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdultConstraint {
    String message() default "User should be adult";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
