package com.chen.simpleframework.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: cz
 * Date： 2020/12/10 22:13
 * 用户VO实体
 */
@Data
public class UserVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 8624447115829874912L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phone;
}
