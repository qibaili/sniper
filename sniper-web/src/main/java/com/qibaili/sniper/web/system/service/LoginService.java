package com.qibaili.sniper.web.system.service;

import com.qibaili.sniper.web.system.bean.ResponseResult;
import com.qibaili.sniper.web.system.dto.LoginDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qibaili
 * @date 2018-06-08
 */
public interface LoginService {

    ResponseResult login(LoginDTO dto, HttpServletRequest request);

}
