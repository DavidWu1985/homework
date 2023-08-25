package com.wuwei.homework.error;

import com.wuwei.homework.api.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    // 处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgument(MethodArgumentNotValidException e){
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        Map<String, Object> state = new HashMap<>();
        return R.fail(objectError.getDefaultMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public R handleBusinessException(BusinessException e){
        return R.fail(400, e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleError(Throwable e) {
        e.printStackTrace();
        log.error("服务器异常", e);
        return R.fail(e.getMessage());
    }

    // AuthenticationException
    @ExceptionHandler(AuthenticationException.class)
    public R handleUnAuth(AuthenticationException e) {
        return R.fail(900, e.getMessage());
    }



}
