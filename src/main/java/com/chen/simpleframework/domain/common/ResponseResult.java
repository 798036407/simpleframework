package com.chen.simpleframework.domain.common;

import com.chen.simpleframework.exception.ErrorCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.w3c.dom.CDATASection;

import javax.lang.model.element.NestingKind;
import java.io.Serializable;

/**
 * @author: cz
 * Date： 2020/12/10 22:23
 * 通用返回结果模型
 */
@Data
@ApiModel(
        //ApiModel用来描述实体信息
        value = "统一返回实体",
        description = "封装统一返回结果实体信息"
)
public class ResponseResult<T> implements Serializable {


    private static final long serialVersionUID = -1697998194759500601L;

    /**
     * 是否成功
     */
    @ApiModelProperty(
            name = "success",
            value = "是否成功",
            required = true,
            dataType = "Boolean"
    )
    private Boolean success;

    /**
     * 编码
     */
    @ApiModelProperty(
            name = "code",
            value = "编码",
            required = false,
            dataType = "String"
    )
    private String code;

    /**
     * 描述信息
     */
    @ApiModelProperty(
            name = "message",
            value = "描述信息"
    )
    private String message;

    /**
     *
     */
    @ApiModelProperty(
            name = "result",
            value = "泛型结果"
    )
    private T result;

    /**
     * 成功
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T result) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setSuccess(true);
        responseResult.setResult(result);
        return responseResult;
    }

    /**
     * 失败
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> error(String code, String message) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setSuccess(false);
        responseResult.setCode(code);
        responseResult.setMessage(message);
        return responseResult;
    }

    /**
     * 失败
     * @param errorCodeEnum
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> error(ErrorCodeEnum errorCodeEnum) {
        return error(errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }
}
