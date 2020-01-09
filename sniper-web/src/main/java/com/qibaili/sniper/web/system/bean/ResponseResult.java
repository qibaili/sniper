package com.qibaili.sniper.web.system.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author qibaili
 * @date 2018/6/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseResult {

    private Integer status;

    private Object data;

    private String message;

    private Boolean success;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public synchronized static ResponseResult e(Integer status, String message, boolean success) {
        return e(status, message, success, null);
    }

    public synchronized static ResponseResult e(Integer status, String message, boolean success, Object data) {
        ResponseResult res = new ResponseResult();
        res.setStatus(status);
        res.setMessage(message);
        res.setData(data);
        res.setSuccess(success);
        return res;
    }
}
