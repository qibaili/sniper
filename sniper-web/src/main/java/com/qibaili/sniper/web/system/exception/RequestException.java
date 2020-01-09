package com.qibaili.sniper.web.system.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author qibaili
 * @date 2019/10/31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RequestException extends RuntimeException {

    private static final long serialVersionUID = 2757799549008769942L;

    private Integer status = BAD_REQUEST.value();

    public RequestException(String message) {
        super(message);
    }

    public RequestException(HttpStatus status, String messsage) {
        super(messsage);
        this.status = status.value();
    }
}
