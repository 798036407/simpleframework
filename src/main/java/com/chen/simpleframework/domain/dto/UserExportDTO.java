package com.chen.simpleframework.domain.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.chen.simpleframework.util.LocalDateTimeStringConverter;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: cz
 * Date： 2020/12/13 17:12
 * excel导出实体类 @ExcelProperty定义导出字段名
 */
@Data
public class UserExportDTO implements Serializable {

    private static final long serialVersionUID = -2411563462005212568L;

    /**
     * String类型字段
     */
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * Integer类型字段
     */
    @ExcelProperty(value = "年龄")
    private Integer age;

    /**
     * Long类型字段
     */
    @ExcelProperty(value = "版本号")
    private Long version;

    /**
     * LocalDateTime类型
     */
    @ExcelProperty(value = "创建时间",converter = LocalDateTimeStringConverter.class)
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒")
    private LocalDateTime created;


}
