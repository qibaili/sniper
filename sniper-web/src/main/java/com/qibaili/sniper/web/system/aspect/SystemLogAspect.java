package com.qibaili.sniper.web.system.aspect;

import com.alibaba.fastjson.JSON;
import com.qibaili.sniper.web.system.entity.SystemLog;
import com.qibaili.sniper.web.system.service.SystemLogService;
import com.qibaili.sniper.web.system.util.SecurityUtils;
import com.qibaili.sniper.web.system.annotation.SystemLogs;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author qibaili
 * @date 2019/12/6
 */
@Component
@Aspect
@Slf4j
public class SystemLogAspect {

    private final SystemLogService logService;

    private long currentTime = 0L;

    public SystemLogAspect(SystemLogService logService) {
        this.logService = logService;
    }

    /**
     * 配置切入点
     * 该方法无方法体,主要为了让同类中其他方法使用此切入点
     */
    @Pointcut("@annotation(com.qibaili.sniper.web.system.annotation.SystemLogs)")
    public void logPointcut() {

    }

    /**
     * 配置环绕通知,使用在方法 logPointcut() 上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime = System.currentTimeMillis();
        result = joinPoint.proceed();
        SystemLog log = new SystemLog();
        log.setDescription(getMethodSysLogsAnnotationValue(joinPoint));
        log.setLogType("INFO");
        log.setTime(System.currentTimeMillis() - currentTime);
        logService.save(joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        SystemLog log = new SystemLog();
        log.setTime(System.currentTimeMillis() - currentTime);
        log.setLogType("ERROR");
        log.setDescription(getMethodSysLogsAnnotationValue(joinPoint));
        logService.save((ProceedingJoinPoint) joinPoint, log);
    }

    private String getMethodSysLogsAnnotationValue(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(SystemLogs.class)) {
            SystemLogs logs = method.getAnnotation(SystemLogs.class);
            return logs.value();
        }
        return "未知";
    }

    public String getUsername() {
        try {
            return SecurityUtils.getUserDetails().getUsername();
        } catch (Exception e) {
            return "";
        }
    }

    private String getParams(Object[] params) {
        // if (params.length > 0) {
        //     for (int i = 0; i < params.length; i++) {
        //         if (params[i] instanceof LoginDTO) {
        //             LoginDTO loginDTO = (LoginDTO) params[i];
        //             loginDTO.setPassword(filterString);
        //             params[i] = loginDTO;
        //         }
        //         if (params[i] instanceof UserAddDTO) {
        //             UserAddDTO userAddDTO = (UserAddDTO) params[i];
        //             userAddDTO.setPassword(filterString);
        //             params[i] = userAddDTO;
        //         }
        //     }
        // }
        return JSON.toJSONString(params);
    }
}
