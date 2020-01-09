package com.qibaili.sniper.web.system.exception;

import com.qibaili.sniper.web.system.bean.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * @author qibaili
 * @date 2019/10/31
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕捉所有不可知异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e) {
        // 打印堆栈信息
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseResult.e(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), false);
    }

    /**
     * 处理接口无权访问异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseResult.e(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), false);
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = RequestException.class)
    public ResponseResult handleRequestException(RequestException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseResult.e(FORBIDDEN.value(), e.getMessage(), false);
    }

    /**
     * 处理接口数据验证异常
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseResult handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder message = new StringBuilder(bindingResult.getFieldErrors().size() * 16);
        message.append("Invalid request:");
        for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
            if (i > 0) {
                message.append(",");
            }
            FieldError error = bindingResult.getFieldErrors().get(i);
            message.append(error.getField()).append(error.getDefaultMessage());
        }
        return ResponseResult.e(BAD_REQUEST.value(), message.toString(), false);
    }
}
