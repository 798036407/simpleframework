package com.chen.simpleframework.domain.common;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: cz
 * Date： 2020/12/11 20:37
 */
@Data
public class PageQuery<T> implements Serializable {


    private static final long serialVersionUID = -8411749009969723858L;

    /**
     * 当前页
     */
    @NotNull(message = "页号不能为空！")
    @Min(value = 1, message = "页号必须为正数！")
    private Integer pageNo = 1;

    /**
     * 每页条数
     */
    @NotNull(message = "每页条数不能为空！")
    @Max(value = 100, message = "每页条数不能超过100条！")
    private Integer pageSize = 20;

    /**
     * 动态查询条件
     * @Valid 级联约束，query里有验证需要一起验证
     */
    @Valid
    @NotNull(message = "动态查询条件不能为空！")
    private T query;
}
