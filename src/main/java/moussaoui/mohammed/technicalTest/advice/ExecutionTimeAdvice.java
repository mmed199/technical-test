package moussaoui.mohammed.technicalTest.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * Advice arround a ProceedingJoinPoint to calculate the execution time
 * 
 * @author moussaoui
 */
@Aspect
@Component
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAdvice {

	Logger logger = LoggerFactory.getLogger(ExecutionTimeAdvice.class);

	@Around("@annotation(moussaoui.mohammed.technicalTest.advice.TrackExecutionTime)")
	public Object executionTime(ProceedingJoinPoint point) throws Throwable {
		
		// Save the start Time, proceed the point execution, then save the end time
		long startTime = System.currentTimeMillis();
		Object object = point.proceed();
		long endtime = System.currentTimeMillis();

		long executionTime = endtime - startTime;

		logger.info("[Class: " + point.getSignature().getDeclaringTypeName() + ", Method: "
				+ point.getSignature().getName() + ", Execution Time : " + executionTime + "ms");
		
		return object;
	}
}
