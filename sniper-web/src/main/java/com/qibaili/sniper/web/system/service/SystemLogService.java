package com.qibaili.sniper.web.system.service;

import com.qibaili.sniper.web.system.dto.LogFindDTO;
import com.qibaili.sniper.web.system.entity.SystemLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author qibaili
 * @date 2018-08-14
 */
public interface SystemLogService {

    Page<SystemLog> findAll(LogFindDTO dto, Pageable pageable);

    void save(ProceedingJoinPoint joinPoint, SystemLog log);
}
