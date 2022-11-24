package main.project.server.aop;


import com.google.gson.Gson;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LogAspect {

//    private final PlatformTransactionManager transactionManager;

    private final Gson gson;

    @Pointcut("execution( * *..*Controller.*(..) )")
    public void controllerLog(){};


    @Around("controllerLog()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {

//        final String methodName = joinPoint.getSignature().getName();
//        ServletRequestAttributes attribute = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
//        HttpServletRequest request = attribute.getRequest();
//
//        if (request.getContentType() == null) {
//            System.out.println("==================================");
//            System.out.println("비어있음");
//
//        }
//        else {
//            String contentType = request.getContentType().toLowerCase();
//
//            if (contentType.contains("application/json")) {
//                System.out.println("==================================");
//                String json = gson.toJson(joinPoint.getArgs());
//                System.out.println(json);
//
//            } else if (contentType.contains("multipart/form-data")) {
//                System.out.println("==================================");
//                System.out.println("멀티파트");
//
//                Object[] args = joinPoint.getArgs();
//
//                for (Object arg : args) {
//                    if (arg instanceof MultipartFile[]) {
//
//                        System.out.println("멀티파트 배열!!");
//                    } else if (arg instanceof MultipartFile) {
//                        System.out.println("멀티파트!!");
//                    } else {
//                        System.out.println("제이슨?!!");
//                    }
//                }
//            }else{
//                System.out.println("==================================");
//                System.out.println("이외의 타입");
//            }
//        }



        Object proceed = joinPoint.proceed();
        return proceed;

////        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionAttribute());
//
//        try {
//            log.info("[호출 핸들러 메소드 : " + joinPoint.getSignature().getName() + "]");
//            log.info("[---------- 로직 시작 ----------]");
//            Object proceed = joinPoint.proceed();
//            log.info("[---------- 로직 종료 ----------]");
//            return proceed;
//
//        } catch (RuntimeException e) {
////            transactionManager.rollback(transaction);
////            throw new RuntimeException(e.getMessage());
//
//        }
    }




}
