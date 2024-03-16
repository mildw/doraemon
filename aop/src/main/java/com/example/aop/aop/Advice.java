package com.example.aop.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
public class Advice {

    @Around("execution(* com.example.aop..application..*Service.*(..))")
    public Object testAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        Object o = null;
        try {
            log.info("메소드 실행 전");
            o = joinPoint.proceed();
            log.info("메소드 실행 후");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return o;
    }

}