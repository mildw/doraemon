package com.example.springcloudfunction.aop;

import com.example.core.multitenancy.TenantContext;
import com.example.core.multitenancy.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TenantAdvice {

    //Advice를 통해 테넌트 변경 가능하다
//    @Before("execution(* com.example.springcloudfunction.function.*.*(..))")
    public void beforeFunctionExecution(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        try {
            TenantContextHolder.setTenantContext(new TenantContext(1L, "databaseName"));

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException();
        }
    }
}