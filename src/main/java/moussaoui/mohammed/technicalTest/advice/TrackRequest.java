package moussaoui.mohammed.technicalTest.advice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the annotation used for {@link moussaoui.mohammed.technicalTest.advice.RequestAdvice RequestAdvice}
 * Applies on Methods
 * 
 * @author moussaoui
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackRequest {

}
