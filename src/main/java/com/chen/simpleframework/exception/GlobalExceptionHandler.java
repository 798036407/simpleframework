package com.chen.simpleframework.exception;

import com.chen.simpleframework.domain.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: cz
 * Date： 2020/12/12 21:26
 * 全局异常捕获处理器
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截业务类异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler
    public ResponseResult businessExceptionHandle(BusinessException e) {
        log.error("捕捉到业务类异常: ", e);
        return ResponseResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 拦截运行时异常
     * @param e
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseResult runtimeExceptionHandle(RuntimeException e) {
        log.error("捕捉到运行时异常: ", e);
        return ResponseResult.error(ErrorCodeEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
    }

    /**
     * 捕捉系统级异常
     * @param th
     * @return
     */
    @ResponseBody
    @ExceptionHandler
    public ResponseResult throwableHandle(Throwable th) {
        log.error("捕捉到Throwable异常: ", th);
        return ResponseResult.error(ErrorCodeEnum.SYSTEM_ERROR.getCode(), th.getMessage());
    }

}
