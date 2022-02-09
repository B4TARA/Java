package by.baturel.spring.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class Logger {
    @Pointcut("@annotation(Loggable)")
    public void webServiceMethod() { }

    @Around("webServiceMethod()")
    public Object logWebServiceCall(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();
        log.info("Method: " + methodName + ", args: " + Arrays.toString(methodArgs));
        Object result = thisJoinPoint.proceed();
        log.info("Method " + methodName + " returns " + result);

        return result;
    }
}
