package com.qibaili.sniper.web.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qibaili.sniper.web.system.dto.LogFindDTO;
import com.qibaili.sniper.web.system.dto.LoginDTO;
import com.qibaili.sniper.web.system.entity.SystemLog;
import com.qibaili.sniper.web.system.repository.SystemLogRepository;
import com.qibaili.sniper.web.system.service.SystemLogService;
import com.qibaili.sniper.web.system.util.IpUtils;
import com.qibaili.sniper.web.system.util.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author qibaili
 * @date 2018-08-14
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Autowired
    private SystemLogRepository logRepository;

    @Override
    public Page<SystemLog> findAll(LogFindDTO dto, Pageable pageable) {
        Specification<SystemLog> specification = createSpecification(dto);
        return logRepository.findAll(specification, pageable);
    }

    @Override
    public void save(ProceedingJoinPoint joinPoint, SystemLog log) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Object[] args = joinPoint.getArgs();
        List<Object> arguments = new ArrayList<>();

        for (Object arg : args) {
            if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof MultipartFile) {
                // ServletRequest 不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                // ServletResponse 不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                continue;
            }
            if (arg instanceof LoginDTO) {
                LoginDTO dto = (LoginDTO) arg;
                System.out.println(dto);
                log.setUsername(dto.getUsername());
            } else {
                log.setUsername(SecurityUtils.getUserDetails().getUsername());
            }
            arguments.add(arg);
        }
        String paramter;
        try {
            paramter = JSONObject.toJSONString(arguments);
        } catch (Exception e) {
            paramter = arguments.toString();
        }
        // 方法路径
        log.setMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "()");
        log.setRequestIp(IpUtils.getIp(request));
        log.setBrowser(IpUtils.getBrowser(request));
        log.setAddress(IpUtils.getCityInfo(log.getRequestIp()));
        log.setParams(paramter);
        log.setCreateTime(new Date());
        logRepository.save(log);
    }

    private Specification<SystemLog> createSpecification(LogFindDTO dto) {
        return (Specification<SystemLog>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (!StringUtils.isEmpty(dto.getUsername())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("username"), dto.getUsername()));
            }
            if (!StringUtils.isEmpty(dto.getAction())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("action"), dto.getAction()));
            }
            return predicate;
        };
    }
}
