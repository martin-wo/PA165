package cz.muni.fi.pa165;

import javax.inject.Named;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Named
@Aspect
public class LoggingAspect {

    @Around("execution(public * *(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

    	long start = System.currentTimeMillis();
        System.err.println("Calling method: "
                + joinPoint.getSignature());

        Object result = joinPoint.proceed();

        System.err.println("Method finished: "
                + joinPoint.getSignature());
        long elapsedTime = System.currentTimeMillis() - start;
        System.err.println("Method execution time: " + elapsedTime + " milliseconds.");
        return result;
    }

}
