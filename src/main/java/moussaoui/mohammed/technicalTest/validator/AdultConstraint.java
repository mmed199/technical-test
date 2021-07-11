package moussaoui.mohammed.technicalTest.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import javax.validation.Constraint;
import javax.validation.Payload;

import moussaoui.mohammed.technicalTest.validator.impl.AdultValidator;

@Documented
@Constraint(validatedBy = AdultValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AdultConstraint {
    String message() default "User should be adult";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}