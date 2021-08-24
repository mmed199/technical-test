package moussaoui.mohammed.technicalTest.advice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Aspect used to log requests sent to an api endpoint
 * 
 * @author moussaoui
 *
 */
@Aspect
@Component
public class RequestAdvice {

	Logger logger = LoggerFactory.getLogger(RequestAdvice.class);

	@Autowired(required = false)
	private HttpServletRequest request;	
	
    @Pointcut("@annotation(moussaoui.mohammed.technicalTest.advice.TrackRequest)")
    public void action() {
    	
    }
    
    @Before("action()")
    public void logAction(JoinPoint joinPoint) {
    	
    	// if the method is GET, log the url params,
    	// if other log the body
    	if(!"GET".equals(request.getMethod())) {
        	logger.info("[ Uri = " + request.getRequestURI() + ", Body = " + getRequestBody(joinPoint) + "]");
    	} else {
        	logger.info("[ Uri = " + request.getRequestURI() + ", Params = " + paramsToString(request.getParameterMap()) + "]");

    	}
    }

    private String getRequestBody(JoinPoint joinPoint) {
    	
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][] annotationMatrix = method.getParameterAnnotations();
        
        // search the requestBody on the Parameter Annotations
        int index = -1;
        for (Annotation[] annotations : annotationMatrix) {
          index++;
          for (Annotation annotation : annotations) {
            if (!(annotation instanceof RequestBody))
              continue;
            Object requestBody = joinPoint.getArgs()[index];
            return requestBody.toString();
          }
        }
        
        return "";
    }
    
    private String paramsToString(Map<String, String[]> params) {
    	
    	String toReturn = "[";
    	int i = params.size();
    	for (Map.Entry<String, String[]> entry : params.entrySet()) {
    		i--;
    		toReturn += entry.getKey() + " = " + Arrays.toString(entry.getValue());
    		if(i > 0)  toReturn += ",";
    	}
    	
    	toReturn += "]";
    	
    	return toReturn;
    }
}
