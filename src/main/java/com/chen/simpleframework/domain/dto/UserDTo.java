package com.chen.simpleframework.domain.dto;

import com.chen.simpleframework.util.InsertValidationGroup;
import com.chen.simpleframework.util.UpdateValidationGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: cz
 * Date： 2020/12/10 21:54
 * 用户DTO实体
 */
@Data
public class UserDTo implements Serializable {


    /**
     * 序列号
     */
    private static final long serialVersionUID = 1941377266911069163L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空！"
            , groups = {InsertValidationGroup.class})
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空！"
            , groups = {InsertValidationGroup.class})
    @Length(min = 6, max = 20
            , message = "密码长度不能少于6位，不能多于20位！")
    private String password;

    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空！"
            , groups = {InsertValidationGroup.class})
    @Email(message = "必需为有效邮箱！")
    private String email;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空！"
            , groups = {InsertValidationGroup.class})
    @Max(value = 60, message = "最大不能大于60岁！")
    @Min(value = 18, message = "年龄最小不能小于18岁！")
    private Integer age;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空！"
            , groups = {InsertValidationGroup.class})
    private String phone;

    /**
     * 版本号
     */
    @NotNull(message = "版本号不能为空！"
            , groups = {UpdateValidationGroup.class})
    private Long version;

    /**
     * 创建时间
     */
    private LocalDateTime created;
}
