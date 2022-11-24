package main.project.server.aop;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LogAspect {

//    private final PlatformTransactionManager transactionManager;

//    @Pointcut("execution(* main.project.server.*.*Controller.*(..))")
    @Pointcut("execution( * *..*Controller.*(..) )")
    public void controllerLog(){};


    @Around("controllerLog()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {

//        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionAttribute());

        try {
            log.info("[호출 핸들러 메소드 : " + joinPoint.getSignature().getName() + "]");
            log.info("[---------- 로직 시작 ----------]");
            Object proceed = joinPoint.proceed();
            log.info("[---------- 로직 종료 ----------]");
            return proceed;

        } catch (RuntimeException e) {
//            transactionManager.rollback(transaction);
            throw new RuntimeException(e.getMessage());
        }


    }




}
