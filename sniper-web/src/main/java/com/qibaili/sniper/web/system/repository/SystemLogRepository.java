package com.qibaili.sniper.web.system.repository;

import com.qibaili.sniper.web.system.entity.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author qibaili
 * @date 2018-08-14
 */
public interface SystemLogRepository extends JpaRepository<SystemLog, String>, JpaSpecificationExecutor<SystemLog> {

}
