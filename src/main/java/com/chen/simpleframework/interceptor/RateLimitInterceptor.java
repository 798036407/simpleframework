package com.chen.simpleframework.interceptor;

import com.chen.simpleframework.exception.BusinessException;
import com.chen.simpleframework.exception.ErrorCodeEnum;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: cz
 * Date： 2020/12/12 22:49
 */
@Slf4j
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    /**
     * 限流器实例(QPS每秒查询率限制为10（每秒请求十次）)
     */
    private static final RateLimiter rateLimiter = RateLimiter.create(10);


    /**
     * 请求先走这个方法判断是否限流
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (!rateLimiter.tryAcquire()) {
            log.error("系统已被限流...");
            throw new BusinessException(ErrorCodeEnum.RATE_LIMIT_ERROR);
        }
        return true ;
    }
}
