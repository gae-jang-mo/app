package com.gaejangmo.apiserver.commons.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class LoggingAspect {
    private final Map<Class<?>, Logger> loggers = new ConcurrentHashMap<>();

    @Around("@within(com.gaejangmo.apiserver.commons.logging.EnableLog) || @annotation(com.gaejangmo.apiserver.commons.logging.EnableLog)")
    public Object methodLogging(final ProceedingJoinPoint pjp) throws Throwable {
        Logger log = getLog(pjp.getSignature().getDeclaringType());

        log.debug("request by {}, args: {} ", pjp.getSignature().getDeclaringType(), pjp.getArgs());
        Object requestResult = pjp.proceed();
        log.debug("response {}", requestResult);

        return requestResult;
    }

    @Around("@annotation(org.springframework.web.bind.annotation.ExceptionHandler) && args (exception, ..)")
    public Object restControllerAdviceLogging(final ProceedingJoinPoint pjp, Exception exception) throws Throwable {
        Logger log = getLog(pjp.getSignature().getDeclaringType());

        Object result = pjp.proceed();
        log.error("errorMessage: {}", exception.getMessage());

        return result;
    }

    private Logger getLog(final Class<?> clazz) {
        if (loggers.containsKey(clazz)) {
            return loggers.get(clazz);
        }
        Logger log = LoggerFactory.getLogger(clazz);
        loggers.put(clazz, log);
        return log;
    }
}
