package com.chen.simpleframework.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author: cz
 * Date： 2020/12/10 23:00
 * 数据查询DTO实体
 */
@Data
public class UserQueryDTO implements Serializable {


    private static final long serialVersionUID = 6635460990773067721L;

    /**
     * 用户姓名
     */
    @NotEmpty(message = "用户姓名不能为空！")
    String username;
}
