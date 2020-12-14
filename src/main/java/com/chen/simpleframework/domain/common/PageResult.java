package com.chen.simpleframework.domain.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: cz
 * Date： 2020/12/10 23:02
 * 通用分页查询返回实体
 */
@Data
public class PageResult<T> implements Serializable {


    private static final long serialVersionUID = -8419045704234420268L;

    /**
     * 当前页号
     */
    private Integer pageNo;

    /**
     * 每页行数
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pageNum;

    /**
     * 动态的内容
     */
    private T data;
}
