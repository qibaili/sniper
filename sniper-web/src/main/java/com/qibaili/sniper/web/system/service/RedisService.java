package com.qibaili.sniper.web.system.service;

import com.qibaili.sniper.web.system.vo.RedisVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author qibaili
 * @date 2019/11/19
 */
public interface RedisService {

    Page<RedisVO> findByKey(String key, Pageable pageable);

    List<RedisVO> findByKey(String key);

    String getCodeValue(String key);

    void saveCode(String key, Object value);

    void delete(String key);

    void deleteAll();
}
