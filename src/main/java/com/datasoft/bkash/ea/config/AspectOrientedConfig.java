package com.datasoft.bkash.ea.config;


import com.datasoft.bkash.ea.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.UUID;

@Component
@Aspect
@Slf4j
public class AspectOrientedConfig {




    private static final ThreadLocal<Map<String, LocalDateTime>> requestStartTimeHolder = ThreadLocal.withInitial(HashMap::new);
    private static final String REQUEST_ID_ATTRIBUTE = "request-uuid";


    @Before("execution(* com.datasoft.bkash.ea.controller.*.*(..))")
    public void logRequestInfo(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String requestId = UUID.randomUUID().toString();

        request.setAttribute(REQUEST_ID_ATTRIBUTE, requestId);
        requestStartTimeHolder.get().put(requestId, LocalDateTime.now());

        log.info("REQUEST: URI : {} from address : {} with parameters: {}", request.getRequestURI(), request.getRemoteAddr(), joinPoint.getArgs());

    }

    @Before("execution(* com.datasoft.bkash.ea.controller.*.*.*(..))")
    public void logQueryModuleRequestInfo(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String requestId = UUID.randomUUID().toString();

        request.setAttribute(REQUEST_ID_ATTRIBUTE, requestId);
        requestStartTimeHolder.get().put(requestId, LocalDateTime.now());

        log.info("REQUEST: URI : {} from address : {} with parameters: {}", request.getRequestURI(), request.getRemoteAddr(), joinPoint.getArgs());

    }

    @AfterReturning(pointcut = "execution(* com.datasoft.bkash.ea.controller.*.*(..))", returning = "result")
    public void logRequestInfo(JoinPoint joinPoint, final Object result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        String requestId = (String) request.getAttribute(REQUEST_ID_ATTRIBUTE);
        LocalDateTime startTime = requestStartTimeHolder.get().get(requestId);
        LocalDateTime endTime = LocalDateTime.now();

        requestStartTimeHolder.get().remove(requestId);

        String browserName = request.getHeader("User-Agent");
        ApiResponse apiResponse = null;
        ResponseEntity responseEntity = null;
        Object obj = null;
        try {
            apiResponse = (ApiResponse) result;
        } catch (Exception ex) {
            try {
                responseEntity = (ResponseEntity) result;
            } catch (Exception e) {
                obj = result;
            }
        }

        Integer status = 200;
        if (Objects.nonNull(apiResponse))
            status = apiResponse.getStatusCode();
        else if (Objects.nonNull(responseEntity)) {
            status = responseEntity.getStatusCodeValue();
        } else
            status = response.getStatus();

        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        log.info(
                LocalDateTime.now()
                        + " | " + "INFO"
                        + " | " + "101"
                        + " | " + request.getMethod()
                        + " | " + request.getRequestURI()
                        + " | " + request.getServletPath()
                        + " | " + status
                        + " | " + remoteAddr
                        + " | " + browserName
                        + " | " + Duration.between(startTime, endTime).toMillis()
        );


    }

    @AfterReturning(pointcut = "execution(* com.datasoft.bkash.ea.controller.*.*.*(..))", returning = "result")
    public void logQueryModuleRequestInfo(JoinPoint joinPoint, final Object result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        String requestId = (String) request.getAttribute(REQUEST_ID_ATTRIBUTE);
        LocalDateTime startTime = requestStartTimeHolder.get().get(requestId);
        LocalDateTime endTime = LocalDateTime.now();

        requestStartTimeHolder.get().remove(requestId);

        String browserName = request.getHeader("User-Agent");
        ApiResponse apiResponse = null;
        ResponseEntity responseEntity = null;
        Object obj = null;
        try {
            apiResponse = (ApiResponse) result;
        } catch (Exception ex) {
            try {
                responseEntity = (ResponseEntity) result;
            } catch (Exception e) {
                obj = result;
            }
        }

        Integer status = 200;
        if (Objects.nonNull(apiResponse))
            status = apiResponse.getStatusCode();
        else if (Objects.nonNull(responseEntity)) {
            status = responseEntity.getStatusCodeValue();
        } else
            status = response.getStatus();

        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        log.info(
                LocalDateTime.now()
                        + " | " + "INFO"
                        + " | " + "101"
                        + " | " + request.getMethod()
                        + " | " + request.getRequestURI()
                        + " | " + request.getServletPath()
                        + " | " + status
                        + " | " + remoteAddr
                        + " | " + browserName
                        + " | " + Duration.between(startTime, endTime).toMillis()
        );


    }

    @AfterThrowing(pointcut = "execution(* com.datasoft.bkash.ea.controller.*.*(..))", throwing = "ex")
    public void logError(Exception ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String browserName = request.getHeader("User-Agent");

        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        ex.printStackTrace();
        log.error(
                LocalDateTime.now()
                        + " | " + "ERROR"
                        + " | " + "101"
                        + " | " + request.getMethod()
                        + " | " + request.getRequestURI()
                        + " | " + request.getServletPath()
                        + " | " + ex.getMessage()
                        + " | " + remoteAddr
                        + " | " + browserName
        );

    }

    @AfterThrowing(pointcut = "execution(* com.datasoft.bkash.ea.controller.*.*.*(..))", throwing = "ex")
    public void logQueryModuleError(Exception ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String browserName = request.getHeader("User-Agent");

        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        ex.printStackTrace();
        log.error(
                LocalDateTime.now()
                        + " | " + "ERROR"
                        + " | " + "101"
                        + " | " + request.getMethod()
                        + " | " + request.getRequestURI()
                        + " | " + request.getServletPath()
                        + " | " + ex.getMessage()
                        + " | " + remoteAddr
                        + " | " + browserName
        );

    }
}
