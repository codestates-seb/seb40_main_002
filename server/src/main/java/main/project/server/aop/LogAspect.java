package main.project.server.aop;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LogAspect {
    private final Gson gson;

    @Pointcut("execution( * *..*Controller.*(..) )")
    public void controllerLog() {
    }


    @Around("controllerLog()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attribute = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
        HttpServletRequest request = attribute.getRequest();

        //put 순서를 유지하기 위해 LinkedHashMap 구현체를 사용
        Map<String, Object> requestBody = new LinkedHashMap<>();

        if (request.getContentType() == null) {
            //System.out.println("======= 리퀘스트 바디 없음 =======");
        }
        else
        {
            //System.out.println("======= 리퀘스트 바디 있음 =======");

            MethodSignature signature = (MethodSignature) joinPoint.getSignature(); //메소드의 선언 부분에 대한 정보
            Method method = signature.getMethod(); //메소드 자체의 대한 정보를 갖는 클래스
            Parameter[] parameters = method.getParameters(); //메소드가 갖는 파라미터들의 정보
            Object[] parameterValues = joinPoint.getArgs(); //실제 파라미터들의 값
            String contentType = request.getContentType().toLowerCase(); //리퀘스트 바디의 컨텐트 타입별 분기를 위해 얻은 ContentType

            if (contentType.contains("application/json")) //Content-Type이 json인 경우
            {
                putDataInMap(parameters[0].getName(),gson.toJson(parameterValues[0]), requestBody);
            }
            else if (contentType.contains("multipart/form-data")) //Content-Type이 Multipart/form-data인 경우
            {
                //Multipart/form-data는 여러 Content-Type을 내부 요소로 가지고 있기 때문에 반복문으로 각 요소를 접근
                for (int i = 0; i < parameters.length; i++)
                {
                    if (parameterValues[i] instanceof MultipartFile[])
                    {
                        //멀티파트 배열일 경우
                        MultipartFile[] multipartFilesArr = (MultipartFile[]) parameterValues[i];
                        String[] multipartFileNames = new String[multipartFilesArr.length];

                        for (int j = 0; j < multipartFilesArr.length; j++) {
                            multipartFileNames[j] = multipartFilesArr[j].getOriginalFilename();
                        }

                        requestBody.put(parameters[i].getName(), Arrays.toString(multipartFileNames));

                    }
                    else if (parameterValues[i] instanceof MultipartFile)
                    {
                        //멀티파트파일일 경우
                        MultipartFile multipartFiles = (MultipartFile)parameterValues[i];
                        putDataInMap(parameters[i].getName(), multipartFiles.getName(), requestBody);
                    }
                    else
                    {
                        //dto 객체일 경우
                        putDataInMap(parameters[i].getName(),gson.toJson(parameterValues[i]), requestBody);
                    }
                }
            }
            else
            {
                //System.out.println("==================================");
                //System.out.println("이외의 타입");
            }
        }

        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        String handlerMethodName = joinPoint.getSignature().getName();

        log.info(String.format(
                "\n ------- JoinPoint 시작 ------- " +
                "\n ==== [호출된 uri] : [%s] ==== " +
                "\n ==== [쿼리스트링] : [%s]  ==== " +
                "\n ==== [작동된 핸들러 메소드 명] : [%s] " +
                "\n ==== [리퀘스트 바디] : [%s] ", requestURI, queryString, handlerMethodName, requestBody.toString())

        );

        Object proceed = joinPoint.proceed();

        log.info("\n ------- JoinPoint 정상 종료! ------- ");
        return proceed;
    }

    private void putDataInMap(String key, String value, Map<String, Object> map) {
        map.put(key,value);
    }


}
