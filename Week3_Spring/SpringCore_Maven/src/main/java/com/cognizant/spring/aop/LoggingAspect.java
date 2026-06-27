package com.cognizant.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Spring AOP — Aspect-Oriented Programming
 * Cross-cutting concerns: Logging, Performance Monitoring, Security
 *
 * Concepts:
 *  - @Aspect         : Marks this class as an aspect
 *  - @Before         : Runs BEFORE the method
 *  - @After          : Runs AFTER method (always)
 *  - @AfterReturning : Runs AFTER method returns successfully
 *  - @AfterThrowing  : Runs if method throws exception
 *  - @Around         : Wraps the method (most powerful)
 *  - Pointcut        : Expression defining which methods to intercept
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut — matches all methods in service package
    @Pointcut("execution(* com.cognizant.spring..*Service.*(..))")
    public void serviceMethods() {}

    // @Before — runs before any service method
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("→ Entering: {}.{}() | Args: {}",
                 joinPoint.getTarget().getClass().getSimpleName(),
                 joinPoint.getSignature().getName(),
                 Arrays.toString(joinPoint.getArgs()));
    }

    // @AfterReturning — runs after successful return, captures return value
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("← Exiting: {}.{}() | Returned: {}",
                 joinPoint.getTarget().getClass().getSimpleName(),
                 joinPoint.getSignature().getName(),
                 result);
    }

    // @AfterThrowing — runs when method throws exception
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("✗ Exception in {}.{}() | Cause: {}",
                  joinPoint.getTarget().getClass().getSimpleName(),
                  joinPoint.getSignature().getName(),
                  error.getMessage());
    }

    // @Around — performance monitoring (wraps method entirely)
    @Around("execution(* com.cognizant.spring..*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result  = joinPoint.proceed();   // Execute the actual method
        long duration  = System.currentTimeMillis() - startTime;

        if (duration > 100) {
            log.warn("SLOW METHOD: {}.{}() took {}ms",
                     joinPoint.getTarget().getClass().getSimpleName(),
                     joinPoint.getSignature().getName(),
                     duration);
        } else {
            log.debug("{}() executed in {}ms", joinPoint.getSignature().getName(), duration);
        }
        return result;
    }
}
