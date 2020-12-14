package com.chen.simpleframework.util;

import com.chen.simpleframework.exception.ErrorCodeEnum;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;

/**
 * @author: cz
 * Date： 2020/12/12 17:51
 * 校验工具类
 */
public class ValidatorUtils {

    /**
     * 全局校验器
     */
    private static Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    /**
     * 参数校验
     * @param object 校验对象
     * @param group 可变的校验分组
     * @param <T>
     */
    public static <T> void validate(T object, Class... group) {
        Set<ConstraintViolation<T>> validate = validator.validate(object, group);
        //如果校验结果不为空
        if (!CollectionUtils.isEmpty(validate)) {
            StringBuilder exceptionMessage = new StringBuilder();
            validate.forEach(constraintViolation -> {
                exceptionMessage.append(constraintViolation.getMessage());
            });

            throw new RuntimeException(exceptionMessage.toString());
        }

    }
}
