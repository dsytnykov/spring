package com.dsytnykov.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("execution(public * com.dsytnykov.aop.controller.*.*())")
    public void allPublicServiceMethods() {}

    @Pointcut("execution(* getById(..)) && args(id))")
    public void controllerGetById(int id) {}


    @Around("execution(* findAll(..))")
    public Object allFindMethods(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            logger.info("Method {} took {} ms", pjp.getSignature().getName(), System.currentTimeMillis() - start);
        }
    }

    @Before("allPublicServiceMethods() && @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void allPublicServiceMethodsBefore(JoinPoint jp) {
        logger.info("Method {} called before", jp.getSignature().getName());
    }

    @After("allPublicServiceMethods() && @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void allPublicServiceMethodsAfter(JoinPoint jp) {
        logger.info("Method {} called after", jp.getSignature().getName());
    }

    @AfterReturning(value = "controllerGetById(id)", argNames = "jp,id")
    public void controllerGetByIdAfter(JoinPoint jp, int id) {
        logger.info("Method {}.getById({}) called successfully", jp.getTarget().getClass().getSimpleName(), id);
    }

    @AfterThrowing(value = "controllerGetById(id)", argNames = "jp,id")
    public void controllerGetByIdAfterThrowing(JoinPoint jp, int id) {
        logger.info("Method {}.getById({}) failed", jp.getTarget().getClass().getSimpleName(), id);
    }

}
